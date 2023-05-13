package com.hanghae.be_h010gram.domain.post.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.hanghae.be_h010gram.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class PostLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(nullable = false)
    private Member member;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    public PostLike(Post post, Member members) {
        this.post = post;
        this.member = members;
    }

}
