package com.nolnol.useminserver.web.member;

import com.nolnol.useminserver.domain.member.MemberService;
import com.nolnol.useminserver.web.member.model.LoginRequestDto;
import com.nolnol.useminserver.web.member.model.LoginResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        LoginResponseDto response = memberService.login(loginRequestDto);

        return ResponseEntity.ok(response);
    }
}
