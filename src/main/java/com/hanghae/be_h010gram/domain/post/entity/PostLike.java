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
    private Member members;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "post_id", nullable = false)
    private Post posts;

    public PostLike(Post post, Member members) {
        this.posts = post;
        this.members = members;
    }

}
