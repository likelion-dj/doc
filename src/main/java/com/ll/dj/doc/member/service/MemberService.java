package com.ll.dj.doc.member.service;

import com.ll.dj.doc.base.dto.RsData0;
import com.ll.dj.doc.base.dto.RsData1;
import com.ll.dj.doc.base.dto.RsData2;
import com.ll.dj.doc.emailVerification.service.EmailVerificationService;
import com.ll.dj.doc.member.dto.MemberDto;
import com.ll.dj.doc.member.entity.Member;
import com.ll.dj.doc.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    private final EmailVerificationService emailVerificationService;

    public MemberDto create(MemberDto memberDto) {
        memberDto.fillDate();
        Member member = memberDto.toEntity();
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        Member savedMember = memberRepository.save(member);

        return savedMember.toDto();
    }

    public MemberDto findById(long id) {
        return memberRepository.findById(id)
                .map(Member::toDto)
                .orElse(null);
    }

    public MemberDto findByNameAndEmail(String name, String email) {
        return memberRepository.findByNameAndEmail(name, email)
                .map(Member::toDto)
                .orElse(null);
    }

    public RsData2<MemberDto, Long> join(MemberDto memberDto) {
        memberDto = create(memberDto);

        RsData1<Long> sendEmailRsData = sendEmailVerificationUrlTo(memberDto);

        return RsData2.of("S-1", "회원가입이 완료되었습니다.", memberDto, sendEmailRsData.getData1());
    }

    private RsData1<Long> sendEmailVerificationUrlTo(MemberDto memberDto) {
        return emailVerificationService.send(memberDto);
    }

    @Transactional
    public void modify(MemberDto memberDto) {
        memberRepository.findById(memberDto.getId())
                .ifPresent(member -> {
                    member.update(memberDto);
                    memberRepository.save(member);
                });
    }

    public RsData0 verifyEmail(int memberId, String verificationCode) {
        RsData0 verifyVerificationCodeRs = emailVerificationService.verifyVerificationCode(memberId, verificationCode);
        if (verifyVerificationCodeRs.isSuccess() == false) {
            return verifyVerificationCodeRs;
        }

        // 회원 불러오고
        // 관련 필드 업데이트
        MemberDto memberDto = findById(memberId);
        memberDto.setEmailVerified(true);
        modify(memberDto);

        return RsData0.of("S-1", "이메일인증이 완료되었습니다.");
    }
}
