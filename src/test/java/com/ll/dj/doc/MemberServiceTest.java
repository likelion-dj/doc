package com.ll.dj.doc;

import com.ll.dj.doc.base.dto.RsData2;
import com.ll.dj.doc.member.dto.MemberDto;
import com.ll.dj.doc.member.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class MemberServiceTest {
    @Autowired
    private MemberService memberService;

    public static MemberDto saveMember(MemberService memberService, String username, String password, String name, String email) {
        MemberDto memberDto = new MemberDto(username, password, name, email);
        return memberService.create(memberDto);
    }

    public static RsData2<MemberDto, Long> joinMember(MemberService memberService, String username, String password, String name, String email) {
        MemberDto memberDto = new MemberDto(username, password, name, email);
        return memberService.join(memberDto);
    }

    private MemberDto saveMember(String username, String password, String name, String email) {
        return saveMember(memberService, username, password, name, email);
    }

    private RsData2<MemberDto, Long> joinMember(String username, String password, String name, String email) {
        return joinMember(memberService, username, password, name, email);
    }

    @Test
    public void 회원_생성() {
        MemberDto memberDto = saveMember("user1", "1234", "유저1", "user1@test.com");

        assertThat(memberDto.getId()).isGreaterThan(0);
    }

    @Test
    public void username이_동일한_회원은_존재할_수_없다() {
        saveMember("user1", "1234", "유저1", "user1@test.com");

        assertThrows(DataIntegrityViolationException.class, () -> {
            saveMember("user1", "1234", "유저2", "user2@test.com");
        });
    }

    @Test
    public void email이_동일한_회원은_존재할_수_없다() {
        saveMember("user1", "1234", "유저1", "user1@test.com");

        assertThrows(DataIntegrityViolationException.class, () -> {
            saveMember("user2", "1234", "유저2", "user1@test.com");
        });
    }

    @Test
    public void name과_email로_username을_찾을_수_있다() {
        MemberDto memberDto = saveMember("user1", "1234", "유저1", "user1@test.com");
        saveMember("user2", "1234", "유저2", "user2@test.com");

        MemberDto foundMemberDto = memberService.findByNameAndEmail("유저1", "user1@test.com");

        memberDto.setPassword(foundMemberDto.getPassword());
        assertThat(foundMemberDto).isEqualTo(memberDto);
    }

    @Test
    public void 생성된_회원은_이메일인증이_필요하다() {
        MemberDto memberDto = saveMember("user1", "1234", "유저1", "user1@test.com");

        assertThat(memberDto.isEmailVerified()).isFalse();
    }

    @Test
    public void 가입이_완료되면_인증URL이_발송_되어야_한다() {
        RsData2<MemberDto, Long> joinMemberRsData = joinMember("user1", "1234", "유저1", "user1@test.com");
        long sendEmailLogId = joinMemberRsData.getData2();

        assertThat(sendEmailLogId).isGreaterThan(0);
    }

    @Test
    public void 회원_조회() {
        saveMember("user1", "1234", "유저1", "user1@test.com");
        MemberDto memberDto = saveMember("user2", "1234", "유저2", "user2@test.com");

        MemberDto foundMemberDto = memberService.findById(memberDto.getId());

        // 비밀번호는 변경된다.
        memberDto.setPassword(foundMemberDto.getPassword());

        assertThat(foundMemberDto).isEqualTo(memberDto);
    }
}
