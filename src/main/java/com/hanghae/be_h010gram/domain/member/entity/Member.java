package com.hanghae.be_h010gram.domain.member.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member {
    @Id
    private String memberId;

    @Column
    private String email;

    @Column
    private String nickname;

    @Column
    private String password;

    @Column(columnDefinition = "LONGTEXT")
    @Lob
    private String img;
}
