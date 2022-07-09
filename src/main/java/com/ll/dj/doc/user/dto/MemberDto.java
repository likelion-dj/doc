package com.ll.dj.doc.user.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class MemberDto {
    private long id;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private String username;
    private String password;
    private String email;
    private String name;
    private boolean emailVerified;

    public MemberDto(String username, String password, String name, String email) {
        this.createdDate = LocalDateTime.now();
        this.modifiedDate = LocalDateTime.now();
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
    }
}
