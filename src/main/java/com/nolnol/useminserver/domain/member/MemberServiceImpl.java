package com.nolnol.useminserver.domain.member;

import com.nolnol.useminserver.web.member.model.LoginRequestDto;
import com.nolnol.useminserver.web.member.model.LoginResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        Optional<Member> savedMember = memberRepository.findById(loginRequestDto.getId());
        if (savedMember.isPresent()) {
            return new LoginResponseDto(savedMember.get().getId(), savedMember.get().getNickname());
        }

        Member member = Member.builder()
                              .id(loginRequestDto.getId())
                              .profileUrl(loginRequestDto.getProfileUrl())
                              .nickname(RandomNicknameGenerator.generate())
                              .build();
        memberRepository.save(member);

        return new LoginResponseDto(member);
    }

    @Override
    public Member findById(String ownerId) {
        return memberRepository.findById(ownerId)
                               .orElseThrow(NoSuchElementException::new);
    }
}
