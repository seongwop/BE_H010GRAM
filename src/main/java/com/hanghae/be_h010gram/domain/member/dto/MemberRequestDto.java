package com.hanghae.be_h010gram.domain.member.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;


public class MemberRequestDto{

    @Getter
    public static class Login {
        private String email;
        private String password;
    }

    @Getter
    public static class Register {
        @NotBlank @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = "이메일 형식에 맞지 않습니다.")
        private String email;
        @NotBlank @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*?&()_])[A-Za-z\\d$@$!%*?&()_]{8,15}$", message = "비밀번호는 최소 8자 이상 15자 이하이며 알파벳 대소문자와 숫자로 구성되어야 합니다.")
        private String password;
        @NotBlank
        private String checkPassword;
        private String nickname;
    }
}

