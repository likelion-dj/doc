package com.ll.dj.doc;

import com.ll.dj.doc.base.dto.RsData1;
import com.ll.dj.doc.email.service.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class EmailServiceTest {
    @Autowired
    EmailService emailService;

    @Test
    public void 이메일_발송() {
        RsData1<Long> sendEmailRs = emailService.sendEmail("user1@test.com", "제목1", "내용1");
        long sendEmailLogId = sendEmailRs.getData1();

        assertThat(sendEmailLogId).isGreaterThan(0);
    }

    @Test
    public void 이메일_2개_발송() {
        RsData1<Long> sendEmailRs1 = emailService.sendEmail("user1@test.com", "제목1", "내용1");
        RsData1<Long> sendEmailRs2 = emailService.sendEmail("user1@test.com", "제목2", "내용2");

        assertThat(sendEmailRs2.getData1()).isGreaterThan(sendEmailRs1.getData1());
    }
}
