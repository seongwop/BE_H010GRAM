package com.hanghae.be_h010gram.domain.member.controller;

import com.hanghae.be_h010gram.domain.member.Service.MemberService;
import com.hanghae.be_h010gram.domain.member.dto.LoginRequestDto;
import com.hanghae.be_h010gram.domain.member.dto.MemberResponseDto;
import com.hanghae.be_h010gram.security.auth.UserDetailsImpl;
import com.hanghae.be_h010gram.util.ResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    @PostMapping("/login")
    public ResponseDto<String> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        return memberService.login(loginRequestDto, response);
    }

    @GetMapping("/member/{memberId}")
    public ResponseDto<MemberResponseDto> getProfile (@PathVariable Long memberId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return memberService.getProfil(memberId, userDetails.getMember());
    }
}
