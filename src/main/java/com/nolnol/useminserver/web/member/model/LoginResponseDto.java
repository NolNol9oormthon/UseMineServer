package com.nolnol.useminserver.web.member.model;

import com.nolnol.useminserver.domain.member.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDto {

    private Long id;
    private String nickname;

    public LoginResponseDto(Member member) {
        this(member.getId(), member.getNickname());
    }
}
