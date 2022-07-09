package com.ll.dj.doc.email.service;

import com.ll.dj.doc.base.AppConfig;
import com.ll.dj.doc.base.dto.RsData1;
import com.ll.dj.doc.email.dto.SendEmailLogDto;
import com.ll.dj.doc.email.entity.SendEmailLog;
import com.ll.dj.doc.email.repository.SendEmailLogRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final SendEmailLogRepository emailLogRepository;
    private final ModelMapper mapper;

    public SendEmailLog of(SendEmailLogDto sendEmailLogDto) {
        if (sendEmailLogDto == null) return null;
        return mapper.map(sendEmailLogDto, SendEmailLog.class);
    }

    public SendEmailLogDto of(SendEmailLog sendEmailLog) {
        if (sendEmailLog == null) return null;
        return mapper.map(sendEmailLog, SendEmailLogDto.class);
    }

    public RsData1 sendEmail(String email, String title, String body) {
        SendEmailLogDto sendEmailLogDto = new SendEmailLogDto(email, title, body);
        SendEmailLog sendEmailLog = emailLogRepository.save(of(sendEmailLogDto));

        RsData1<String> trySendRs = trySend(email, title, body);

        setCompleted(sendEmailLog, trySendRs.getResultCode(), trySendRs.getMsg());

        return RsData1.of("S-1", "메일이 발송되었습니다.", sendEmailLog.getId());
    }

    private RsData1<String> trySend(String email, String title, String body) {
        if (AppConfig.isNotProd()) {
            return RsData1.of("S-1", "메일이 발송되었습니다.", "CODE");
        }

        // 나중에 실전 소스코드로 대체하여 실제 메일 발송
        return RsData1.of("S-1", "메일이 발송되었습니다.", "CODE");
    }

    @Transactional
    private void setCompleted(SendEmailLog sendEmailLog, String resultCode, String msg) {
        if (resultCode.startsWith("S-")) {
            sendEmailLog.setSendEndDate(LocalDateTime.now());
        } else {
            sendEmailLog.setFailDate(LocalDateTime.now());
        }

        sendEmailLog.setResultCode(resultCode);
        sendEmailLog.setMessage(msg);
    }
}
