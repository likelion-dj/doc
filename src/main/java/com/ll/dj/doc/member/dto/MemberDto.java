package com.ll.dj.doc.member.dto;

import com.ll.dj.doc.member.entity.Member;
import com.ll.dj.doc.util.Ut;
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

    public Member toEntity() {
        return Ut.modelMapper.map(this, Member.class);
    }

    public void fillDate() {
        this.setCreatedDate(LocalDateTime.now());
        this.setModifiedDate(LocalDateTime.now());
    }
}
