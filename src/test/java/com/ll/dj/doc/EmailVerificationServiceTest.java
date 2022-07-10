package com.ll.dj.doc;

import com.ll.dj.doc.base.dto.RsData0;
import com.ll.dj.doc.base.dto.RsData2;
import com.ll.dj.doc.emailVerification.service.EmailVerificationService;
import com.ll.dj.doc.member.dto.MemberDto;
import com.ll.dj.doc.member.service.MemberService;
import com.ll.dj.doc.util.Ut;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class EmailVerificationServiceTest {
    @Autowired
    private EmailVerificationService emailVerificationService;
    @Autowired
    private MemberService memberService;

    @Test
    public void 이메일_인증코드_생성() {
        String code = emailVerificationService.genEmailVerificationCode(1);

        assertThat(code).hasSizeGreaterThan(20);
    }

    @Test
    public void 이메일_인증코드_유효성검사() {
        String code = emailVerificationService.genEmailVerificationCode(1);
        RsData0 rsData = emailVerificationService.verifyVerificationCode(1, code);

        assertThat(rsData.isSuccess()).isTrue();
    }

    @Test
    public void 이메일_인증코드_URL_생성() {
        String url = emailVerificationService.genEmailVerificationUrl(1);

        assertThat(Ut.url.isUrl(url)).isTrue();
    }

    @Test
    public void 회원가입이_되면_유효한_이메일인증코드가_발급된다() {
        RsData2<MemberDto, Long> joinMemberRsData = MemberServiceTest.joinMember(memberService, "user1", "1234", "유저1", "user1@test.com");
        MemberDto memberDto = joinMemberRsData.getData1();

        long memberId = memberDto.getId();
        String verificationCode = emailVerificationService.findEmailVerificationCode(memberId);

        boolean isSuccess = emailVerificationService.verifyVerificationCode(memberId, verificationCode).isSuccess();

        assertThat(isSuccess).isTrue();
    }

    @Test
    public void 회원가입_후_이메일인증코드로_인증을_완료하면_해당_회원은_이메일인증된_회원이_된다() {
        RsData2<MemberDto, Long> joinMemberRsData = MemberServiceTest.joinMember(memberService, "user1", "1234", "유저1", "user1@test.com");
        MemberDto memberDto = joinMemberRsData.getData1();

        long memberId = memberDto.getId();
        String verificationCode = emailVerificationService.findEmailVerificationCode(memberId);

        boolean isSuccess = memberService.verifyEmail(1, verificationCode).isSuccess();
        assertThat(isSuccess).isTrue();

        // 해당 회원의 emailVerified 필드가 정말로 true가 인지 체크
        MemberDto foundMemberDto = memberService.findById(memberId);

        boolean emailVerified = foundMemberDto.isEmailVerified();
        assertThat(emailVerified).isTrue();
    }
}
