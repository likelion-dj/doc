package com.ll.dj.doc.article.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@ToString
public class Article {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long id;
    @Setter
    private LocalDateTime createdDate;
    @Setter
    private LocalDateTime modifiedDate;
    @Setter
    private String title;
    @Setter
    private String body;
}
