package com.ll.dj.doc.email;

import com.ll.dj.doc.base.dto.RsData1;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    public RsData1<Long> sendEmail(String email, String title, String body) {
        return RsData1.of("S-1", "메일이 발송되었습니다.", 1L);
    }
}
