package com.ll.dj.doc;

import com.ll.dj.doc.base.dto.RsData1;
import com.ll.dj.doc.base.dto.RsData2;
import com.ll.dj.doc.email.EmailService;
import com.ll.dj.doc.user.dto.MemberDto;
import com.ll.dj.doc.user.entity.Member;
import com.ll.dj.doc.user.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final ModelMapper mapper;
    private final EmailService emailService;

    public Member of(MemberDto memberDto) {
        return mapper.map(memberDto, Member.class);
    }

    public MemberDto of(Member member) {
        return mapper.map(member, MemberDto.class);
    }

    public MemberDto create(MemberDto memberDto) {
        Member member = memberRepository.save(of(memberDto));
        memberDto.setId(member.getId());

        return memberDto;
    }

    public MemberDto findById(long id) {
        Optional<Member> optMember = memberRepository.findById(id);

        return optMember.isPresent() ? of(optMember.get()) : null;
    }

    public MemberDto findByNameAndEmail(String name, String email) {
        Optional<Member> optMember = memberRepository.findByNameAndEmail(name, email);

        return optMember.isPresent() ? of(optMember.get()) : null;
    }

    public RsData2<MemberDto, Long> join(MemberDto memberDto) {
        memberDto = create(memberDto);

        RsData1<Long> sendEmailRsData = sendEmailVerificationUrlToMemberEmail(memberDto);

        return RsData2.of("S-1", "회원가입이 완료되었습니다.", memberDto, sendEmailRsData.getData1());
    }

    private RsData1<Long> sendEmailVerificationUrlToMemberEmail(MemberDto memberDto) {
        String email = memberDto.getEmail();
        String title = "[DOC 이메일인증] 안녕하세요 %s님. 링크를 클릭하여 회원가입을 완료해주세요.".formatted(memberDto.getName());
        String url = genEmailVerificationUrl(memberDto);

        RsData1<Long> sendEmailRs = emailService.sendEmail(email, title, url);

        return sendEmailRs;
    }

    private String genEmailVerificationUrl(MemberDto memberDto) {
        return "링크!";
    }
}
