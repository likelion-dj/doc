package com.ll.dj.doc.member.entity;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Entity
@Setter
public class Member {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Setter(AccessLevel.NONE)
    private long id;
    @NotNull
    private LocalDateTime createdDate;
    @NotNull
    private LocalDateTime modifiedDate;
    @Column(unique = true)
    @NotNull
    private String username;
    @NotNull
    private String password;
    @Column(unique = true)
    @NotNull
    private String email;
    @NotNull
    private String name;
    @ColumnDefault("0")
    @NotNull
    private boolean emailVerified;
}
