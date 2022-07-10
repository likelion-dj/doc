package com.ll.dj.doc.email.service;

import com.ll.dj.doc.base.AppConfig;
import com.ll.dj.doc.base.dto.RsData0;
import com.ll.dj.doc.base.dto.RsData1;
import com.ll.dj.doc.email.dto.SendEmailLogDto;
import com.ll.dj.doc.email.entity.SendEmailLog;
import com.ll.dj.doc.email.repository.SendEmailLogRepository;
import com.ll.dj.doc.emailSender.service.EmailSenderService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final SendEmailLogRepository emailLogRepository;
    private final ModelMapper mapper;
    private final EmailSenderService emailSenderService;

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

        RsData0 trySendRs = trySend(email, title, body);

        setCompleted(sendEmailLog, trySendRs.getResultCode(), trySendRs.getMessage());

        return RsData1.of("S-1", "메일이 발송되었습니다.", sendEmailLog.getId());
    }

    private RsData0 trySend(String email, String title, String body) {
        if (AppConfig.isNotProd()) {
            return RsData0.of("S-0", "메일이 발송되었습니다.");
        }

        try {
            emailSenderService.send(email, "no-reply@no-reply.com", title, body);

            return RsData0.of("S-1", "메일이 발송되었습니다.");
        } catch (MailException e) {
            return RsData0.of("F-1", e.getMessage());
        }
    }

    @Transactional
    private void setCompleted(SendEmailLog sendEmailLog, String resultCode, String message) {
        if (resultCode.startsWith("S-")) {
            sendEmailLog.setSendEndDate(LocalDateTime.now());
        } else {
            sendEmailLog.setFailDate(LocalDateTime.now());
        }

        sendEmailLog.setResultCode(resultCode);
        sendEmailLog.setMessage(message);
    }
}
