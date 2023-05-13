package com.hanghae.be_h010gram.domain.member.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ID")
    private Long Id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String password;

    @Column(columnDefinition = "LONGTEXT")
    @Lob
    private String profileImage;

    @Builder
    private Member(String email, String nickname, String password, String profileImage) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.profileImage = profileImage;
    }
}
