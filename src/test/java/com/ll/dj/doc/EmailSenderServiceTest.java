package com.ll.dj.doc;

import com.ll.dj.doc.emailSender.service.EmailSenderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class EmailSenderServiceTest {
    @Autowired
    EmailSenderService emailSenderService;

    @Test
    public void 이메일_발송_실제() {
        // 주석의 이유 : 실제로 메일이 발송되기 때문에
        // 정말 필요할 때만 주석을 해제해서 테스트
        //emailSenderService.send("jangka512@gmail.com", "no-reply@no-reply.com", "[테스트발송] - 제목", "[테스트발송] - 내용");
    }
}
