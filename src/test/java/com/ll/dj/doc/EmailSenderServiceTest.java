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
        //emailSenderService.send("jangka512@gmail.com", "no-reply@no-reply.com", "[테스트발송] - 제목", "[테스트발송] - 내용");
    }
}
