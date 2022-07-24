package com.ll.dj.doc.member.form;

import com.ll.dj.doc.member.dto.MemberDto;
import com.ll.dj.doc.util.Ut;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class JoinForm {
    @NotNull
    @NotEmpty(message = "아이디를 입력해주세요")
    @Size(min = 4, message = "아이디는 4글자 이상이어야 합니다.")
    private String username;
    @NotNull
    @NotEmpty(message = "패스워드를 입력해주세요")
    @Size(min = 4, message = "비밀번호는 4글자 이상이어야 합니다.")
    private String password;

    @NotEmpty(message = "패스워드를 입력해주세요")
    @Size(min = 4, message = "비밀번호는 4글자 이상이어야 합니다.")
    private String password2;
    @NotNull
    @NotEmpty(message = "이메일을 입력해주세요")
    @Email
    private String email;
    @NotNull
    @NotEmpty(message = "이름을 입력해주세요")
    private String name;

    public MemberDto toDto() {
        return Ut.modelMapper.map(this, MemberDto.class);
    }
}
