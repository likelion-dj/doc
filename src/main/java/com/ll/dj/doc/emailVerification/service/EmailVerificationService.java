package com.ll.dj.doc.emailVerification.service;

import com.ll.dj.doc.attr.service.AttrService;
import com.ll.dj.doc.base.dto.RsData0;
import com.ll.dj.doc.base.dto.RsData1;
import com.ll.dj.doc.email.service.EmailService;
import com.ll.dj.doc.member.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmailVerificationService {
    private final EmailService emailService;
    private final AttrService attrService;

    public RsData1<Long> send(MemberDto memberDto) {
        String email = memberDto.getEmail();
        String title = "[DOC 이메일인증] 안녕하세요 %s님. 링크를 클릭하여 회원가입을 완료해주세요.".formatted(memberDto.getName());
        String url = genEmailVerificationUrl(memberDto);

        RsData1<Long> sendEmailRs = emailService.sendEmail(email, title, url);

        return sendEmailRs;
    }

    public String genEmailVerificationUrl(MemberDto memberDto) {
        return genEmailVerificationUrl(memberDto.getId());
    }

    public String genEmailVerificationUrl(long memberId) {
        String code = genEmailVerificationCode(memberId);

        return "http://localhost:8081/emailValidation/verify?memberId=%d&code=%s".formatted(memberId, code);
    }

    public String genEmailVerificationCode(long memberId) {
        String code = UUID.randomUUID().toString();
        attrService.set("member__%d__extra__emailVerificationCode".formatted(memberId), code, LocalDateTime.now().plusSeconds(60 * 60 * 1));

        return code;
    }

    public RsData0 verifyVerificationCode(long memberId, String code) {
        String foundCode = attrService.get("member__%d__extra__emailVerificationCode".formatted(memberId), "");

        if (foundCode.equals(code) == false) {
            return RsData0.of("F-1", "만료되었거나 유효하지 않은 코드입니다.");
        }

        return RsData0.of("S-1", "인증된 코드 입니다.");
    }

    public String findEmailVerificationCode(long memberId) {
        return attrService.get("member__%d__extra__emailVerificationCode".formatted(memberId), "");
    }
}
