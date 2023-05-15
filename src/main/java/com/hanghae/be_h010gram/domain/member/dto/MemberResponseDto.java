package com.hanghae.be_h010gram.domain.member.dto;

import com.hanghae.be_h010gram.domain.member.entity.Member;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberResponseDto {
    private String email;
    private String nickname;
    private String profileImage;

    public MemberResponseDto(Member member) {
        this.email = member.getEmail();
        this.nickname = member.getNickname();
        this.profileImage = member.getMemberImage();
    }
}
