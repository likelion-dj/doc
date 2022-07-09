package com.ll.dj.doc.member.service;

import com.ll.dj.doc.base.dto.RsData1;
import com.ll.dj.doc.base.dto.RsData2;
import com.ll.dj.doc.emailVerification.service.EmailVerificationService;
import com.ll.dj.doc.member.dto.MemberDto;
import com.ll.dj.doc.member.entity.Member;
import com.ll.dj.doc.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final ModelMapper modelMapper;

    private final EmailVerificationService emailVerificationService;

    public Member of(MemberDto memberDto) {
        if (memberDto == null) return null;
        return modelMapper.map(memberDto, Member.class);
    }

    public MemberDto of(Member member) {
        if (member == null) {
            return null;
        }
        return modelMapper.map(member, MemberDto.class);
    }

    public MemberDto create(MemberDto memberDto) {
        Member member = memberRepository.save(of(memberDto));
        memberDto.setId(member.getId());

        return memberDto;
    }

    public MemberDto findById(long id) {
        Optional<Member> optMember = memberRepository.findById(id);

        return of(optMember.orElse(null));
    }

    public MemberDto findByNameAndEmail(String name, String email) {
        Optional<Member> optMember = memberRepository.findByNameAndEmail(name, email);

        return of(optMember.orElse(null));
    }

    public RsData2<MemberDto, Long> join(MemberDto memberDto) {
        memberDto = create(memberDto);

        RsData1<Long> sendEmailRsData = sendEmailVerificationUrlToMemberEmail(memberDto);

        return RsData2.of("S-1", "회원가입이 완료되었습니다.", memberDto, sendEmailRsData.getData1());
    }

    private RsData1<Long> sendEmailVerificationUrlToMemberEmail(MemberDto memberDto) {
        return emailVerificationService.send(memberDto);
    }
}
