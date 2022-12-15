package com.nolnol.useminserver.domain.member;

import com.nolnol.useminserver.web.member.model.LoginRequestDto;
import com.nolnol.useminserver.web.member.model.LoginResponseDto;

public interface MemberService {
    LoginResponseDto login(LoginRequestDto loginRequestDto);
    Member findById(String ownerId);
}
