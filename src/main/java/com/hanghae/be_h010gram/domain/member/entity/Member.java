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

    @Column
    private String email;

    @Column
    private String nickname;

    @Column
    private String password;

    @Column(columnDefinition = "LONGTEXT")
    @Lob
    private String profileImg;
}
