package com.ll.dj.doc.email.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SendEmailLogDto {
    private long id;
    private String resultCode;
    private String message;
    private String email;
    private String title;
    private String body;
    private LocalDateTime sendEndDate;
    private LocalDateTime failDate;

    public SendEmailLogDto(String email, String title, String body) {
        this.email = email;
        this.title = title;
        this.body = body;
    }
}
