package com.ll.dj.doc.email.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
public class SendEmailLog {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Setter(AccessLevel.NONE)
    private long id;

    private String resultCode;
    private String message;
    private String email;
    private String title;
    private String body;
    private LocalDateTime sendEndDate;
    private LocalDateTime failDate;
}
