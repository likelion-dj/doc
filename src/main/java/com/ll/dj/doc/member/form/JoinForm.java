package com.ll.dj.doc.member.form;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class JoinForm {
    @NotNull
    @NotEmpty
    @Size(min = 4)
    private String username;
    @NotNull
    @NotEmpty
    @Size(min = 4)
    private String password1;
    @NotNull
    @NotEmpty
    @Size(min = 4)
    private String password2;
    @NotNull
    @NotEmpty
    @Email
    private String email;
    @NotNull
    @NotEmpty
    private String name;
}
