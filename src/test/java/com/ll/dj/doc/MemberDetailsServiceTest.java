package com.ll.dj.doc;

import com.ll.dj.doc.base.MemberDetailsService;
import com.ll.dj.doc.member.dto.MemberDto;
import com.ll.dj.doc.member.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class MemberDetailsServiceTest {
    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberDetailsService memberDetailsService;

    @Test
    public void loadUserByUsername() {
        MemberDto memberDto = MemberServiceTest.saveMember(memberService, "user1", "passwd1", "유저1", "user1@test.com");
        String username = memberDto.getUsername();

        UserDetails userDetails = memberDetailsService.loadUserByUsername(username);

        assertThat(userDetails.getUsername()).isEqualTo(username);
    }
}
