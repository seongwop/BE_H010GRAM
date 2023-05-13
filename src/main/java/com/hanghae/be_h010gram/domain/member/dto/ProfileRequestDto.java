package com.hanghae.be_h010gram.domain.member.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class ProfileRequestDto {
    private String nickname;
}
