package com.hanghae.be_h010gram.domain.posts.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.hanghae.be_h010gram.domain.posts.dto.PostsRequestDto;
import com.hanghae.be_h010gram.util.Timestamped;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Posts extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "posts_id")
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "posts", cascade = CascadeType.ALL)
    @OrderBy("id asc")
    @JsonBackReference
    private List<Comments> comments;

    public void update(PostsRequestDto postRequestDto) {
        this.content = postRequestDto.getContent();
    }
}
