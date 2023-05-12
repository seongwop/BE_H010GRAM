package com.hanghae.be_h010gram.domain.posts.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.hanghae.be_h010gram.domain.posts.dto.PostsRequestDto;
import com.hanghae.be_h010gram.util.Timestamped;
import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

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

    @OneToMany(mappedBy = "posts", cascade = CascadeType.REMOVE)
    @OrderBy("id asc")
    @JsonBackReference
    private List<Comments> comments;

//    @ColumnDefault("0")
//    private int postsLikes;
//
//    @JsonBackReference
//    @OneToMany(mappedBy = "posts", cascade = CascadeType.REMOVE)
//    private List<PostsLikes> postsLikesList;

    public void update(PostsRequestDto postRequestDto) {
        this.content = postRequestDto.getContent();
    }

//    public void updateLikes(boolean likeOrDislike) {
//        this.postsLikes = likeOrDislike ? this.postsLikes + 1 : this.postsLikes - 1;
//    }

}
