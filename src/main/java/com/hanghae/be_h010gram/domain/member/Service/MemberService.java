package com.hanghae.be_h010gram.domain.member.Service;

import com.hanghae.be_h010gram.domain.member.dto.LoginRequestDto;
import com.hanghae.be_h010gram.domain.member.dto.MemberRequestDto;
import com.hanghae.be_h010gram.domain.member.dto.MemberResponseDto;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional
    public ResponseDto<String> login(MemberRequestDto.Login loginRequestDto, HttpServletResponse response) {
        String email = loginRequestDto.getEmail();
        String password = loginRequestDto.getPassword();

        //멤버 조회
        Member member = memberRepository.findByEmail(email).orElseThrow(
                () -> new CustomException(ExceptionEnum.USER_NOT_FOUND)
        );

        //비밀번호 확인
        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new CustomException(ExceptionEnum.INVALID_USER_PASSWORD);
        }

        //토큰 생성 및 헤더 반환
        String token = jwtUtil.createToken(member.getNickname(), member.getEmail(), member.getId());
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, token);

        return ResponseDto.setSuccess("로그인 성공");
    }

    @Transactional
    public ResponseDto<?> register(MemberRequestDto.Register requestDto) {
        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());

        Member member = Member.builder().email(requestDto.getEmail())
                .password(encodedPassword)
                .nickname(requestDto.getNickname())
                .build();

        memberRepository.saveAndFlush(member);
        return ResponseDto.setSuccess("회원가입 성공", new MemberResponseDto(member));
    }

    @Transactional(readOnly = true)
    public ResponseDto<MemberResponseDto> getProfile(Long memberId, Member member) {
        //현재 로그인 멤버 조회
        Member loginMember = memberRepository.findById(memberId).orElseThrow(
                () -> new CustomException(ExceptionEnum.USER_NOT_FOUND)
        );

        //로그인된 멤버와 토큰 정보에 존재하는 멤버 비교
        if (!Objects.equals(member.getId(), loginMember.getId())) {
            //토큰의 정보가 유효하지 않아서 실패
            throw new CustomException(ExceptionEnum.INVALID_TOKEN);
        }

        //성공
        MemberResponseDto memberResponseDto = new MemberResponseDto(member);
        return ResponseDto.setSuccess("success", memberResponseDto);
    }
}
