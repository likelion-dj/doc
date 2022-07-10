package com.ll.dj.doc.base;

import com.ll.dj.doc.member.entity.Member;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class AccountContext extends User {
    @Getter
    private final Member member;

    public AccountContext(Member member, Collection<? extends GrantedAuthority> authorities) {
        super(member.getUsername(), member.getPassword(), authorities);

        this.member = member;
    }
}
