package com.hanghae.be_h010gram.domain.member.Service;

import com.hanghae.be_h010gram.domain.member.dto.LoginRequestDto;
import com.hanghae.be_h010gram.domain.member.entity.Member;
import com.hanghae.be_h010gram.domain.member.repository.MemberRepository;
import com.hanghae.be_h010gram.exception.CustomException;
import com.hanghae.be_h010gram.exception.ExceptionEnum;
import com.hanghae.be_h010gram.security.jwt.JwtUtil;
import com.hanghae.be_h010gram.util.ResponseDto;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public ResponseDto<String> login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        String email = loginRequestDto.getEmail();
        String password = loginRequestDto.getPassword();

        Member member = memberRepository.findByEmail(email).orElseThrow(
                () -> new CustomException(ExceptionEnum.USER_NOT_FOUND)
        );

        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new CustomException(ExceptionEnum.INVALID_USER_PASSWORD);
        }

        String token = jwtUtil.createToken(member.getNickname(), member.getEmail(), member.getId());
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, token);

        return ResponseDto.setSuccess("로그인 성공");
    }
}
