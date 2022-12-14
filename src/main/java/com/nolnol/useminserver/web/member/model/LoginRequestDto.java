package com.nolnol.useminserver.web.member.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class LoginRequestDto {
    private String id;
    private String profileUrl;
}
