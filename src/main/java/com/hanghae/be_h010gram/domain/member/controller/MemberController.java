package com.hanghae.be_h010gram.domain.member.controller;

import com.hanghae.be_h010gram.domain.member.dto.MemberRequestDto;
import com.hanghae.be_h010gram.domain.member.dto.MemberResponseDto;
import com.hanghae.be_h010gram.domain.member.dto.ProfileRequestDto;
import com.hanghae.be_h010gram.domain.member.service.MemberService;
import com.hanghae.be_h010gram.security.auth.UserDetailsImpl;
import com.hanghae.be_h010gram.util.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
@Tag(name = "member", description = "회원 API")
public class MemberController {
    private final MemberService memberService;

    @PostMapping("login")
    @Operation(summary = "로그인")
    public ResponseDto<String> login(@RequestBody MemberRequestDto.Login loginRequestDto, HttpServletResponse response) {
        return memberService.login(loginRequestDto, response);
    }

    @PostMapping("register")
    @Operation(summary = "회원가입")
    public ResponseDto<String> register(@Valid @RequestBody MemberRequestDto.Register requestDto) {
        return memberService.register(requestDto);
    }

    @GetMapping("members/{memberId}")
    @Operation(summary = "프로필 조회")
    public ResponseDto<MemberResponseDto> getProfile(@PathVariable Long memberId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return memberService.getProfile(memberId, userDetails.getMember());
    }

    @PutMapping(value = "/members/{memberId}")
    @Operation(summary = "프로필 수정")
    public ResponseDto<String> updateProfile(@PathVariable Long memberId,
                                             @RequestPart(value = "profileRequestDto", required = false) ProfileRequestDto profileRequestDto,
                                             @RequestPart(value = "imageFile", required = false) MultipartFile image,
                                             @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
        return memberService.updateProfile(memberId, profileRequestDto, image, userDetails.getMember());
    }
}
