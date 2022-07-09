package com.ll.dj.doc.user.entity;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long id;

    @Setter
    private LocalDateTime createdDate;

    @Setter
    private LocalDateTime modifiedDate;

    @Setter
    @Column(unique = true)
    private String username;

    @Setter
    private String password;

    @Setter
    @Column(unique = true)
    private String email;

    @Setter
    private String name;

    @Setter
    @ColumnDefault("0")
    private boolean emailVerified;
}
