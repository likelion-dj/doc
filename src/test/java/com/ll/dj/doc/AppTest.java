package com.ll.dj.doc;

import com.ll.dj.doc.article.dto.ArticleDto;
import com.ll.dj.doc.article.service.ArticleService;
import com.ll.dj.doc.base.dto.RsData1;
import com.ll.dj.doc.base.dto.RsData2;
import com.ll.dj.doc.email.service.EmailService;
import com.ll.dj.doc.emailSender.service.EmailSenderService;
import com.ll.dj.doc.emailVerification.service.EmailVerificationService;
import com.ll.dj.doc.member.dto.MemberDto;
import com.ll.dj.doc.member.service.MemberService;
import com.ll.dj.doc.util.Ut;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AppTest {

}

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class MemberServiceTest {
    @Autowired
    MemberService memberService;

    @Autowired
    ModelMapper mapper;

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

        assertThat(foundMemberDto).isEqualTo(memberDto);
    }

    private MemberDto saveMember(String username, String password, String name, String email) {
        MemberDto memberDto = new MemberDto(username, password, name, email);
        return memberService.create(memberDto);
    }

    private RsData2<MemberDto, Long> joinMember(String username, String password, String name, String email) {
        MemberDto memberDto = new MemberDto(username, password, name, email);
        return memberService.join(memberDto);
    }
}

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class EmailVerificationServiceTest {
    @Autowired
    EmailVerificationService emailVerificationService;

    @Test
    public void 이메일_인증코드_생성() {
        String code = emailVerificationService.genEmailVerificationCode(1);

        assertThat(code).hasSizeGreaterThan(20);
    }

    @Test
    public void 이메일_인증코드_유효성검사() {
        String code = emailVerificationService.genEmailVerificationCode(1);
        RsData1<String> rsData1 = emailVerificationService.verifyVerificationCode(1, code);

        assertThat(rsData1.isSuccess()).isTrue();
    }

    @Test
    public void 이메일_인증코드_URL_생성() {
        String url = emailVerificationService.genEmailVerificationUrl(1);

        assertThat(Ut.url.isUrl(url)).isTrue();
    }
}

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class EmailSenderServiceTest {
    @Autowired
    EmailSenderService emailSenderService;

    @Test
    public void 이메일_발송_실제() {
        //emailSenderService.send("jangka512@gmail.com", "no-reply@no-reply.com", "[테스트발송] - 제목", "[테스트발송] - 내용");
    }
}

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class EmailServiceTest {
    @Autowired
    EmailService emailService;

    @Test
    public void 이메일_발송() {
        RsData1<Long> sendEmailRs = emailService.sendEmail("user1@test.com", "제목1", "내용1");
        long emailLogId = sendEmailRs.getData1();

        assertThat(emailLogId).isGreaterThan(0);
    }

    @Test
    public void 이메일_2개_발송() {
        RsData1<Long> sendEmailRs1 = emailService.sendEmail("user1@test.com", "제목1", "내용1");
        RsData1<Long> sendEmailRs2 = emailService.sendEmail("user1@test.com", "제목2", "내용2");

        assertThat(sendEmailRs2.getData1()).isGreaterThan(sendEmailRs1.getData1());
    }
}

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class ArticleServiceTest {
    @Autowired
    ArticleService articleService;

    private ArticleDto genArticleDto(String title, String body) {
        return ArticleDto.builder()
                .title(title)
                .body(body)
                .createdDate(LocalDateTime.now())
                .modifiedDate(LocalDateTime.now())
                .build();
    }

    private ArticleDto saveArticle(String title, String body) {
        return articleService.create(genArticleDto(title, body));
    }

    @Test
    public void 게시물_생성() {
        ArticleDto articleDto = saveArticle("제목1", "내용1");

        assertThat(articleDto.getId()).isGreaterThan(0);
    }

    @Test
    public void 게시물_조회() {
        saveArticle("제목1", "내용1");
        ArticleDto articleDto = saveArticle("제목2", "내용2");

        ArticleDto foundArticleDto = articleService.findById(articleDto.getId());

        assertThat(foundArticleDto).isEqualTo(articleDto);
    }

    @Test
    @Rollback(value = false)
    public void 게시물_수정() {
        ArticleDto articleDto = saveArticle("제목1", "내용1");

        articleDto.setBody("내용2");

        articleService.modify(articleDto);

        ArticleDto foundArticleDto = articleService.findById(articleDto.getId());

        assertThat(foundArticleDto).isEqualTo(articleDto);
    }

    @Test
    public void 게시물_삭제() {
        ArticleDto articleDto = saveArticle("제목1", "내용1");

        articleService.remove(articleDto.getId());

        ArticleDto foundArticleDto = articleService.findById(articleDto.getId());

        assertThat(foundArticleDto).isNull();
    }
}