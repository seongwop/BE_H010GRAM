package com.hanghae.be_h010gram.domain.posts.dto;

import com.hanghae.be_h010gram.domain.posts.entity.Posts;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class PostsResponseDto {
    private Long id;
    private String content;
    private Members members;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private List<Comments> comments;
//    private int likes;

    public PostsResponseDto(Posts posts) {
        this.id = posts.getId();
        this.content = posts.getContent();
        this.members = posts.getMembers();
        this.createdAt = posts.getCreatedAt();
        this.modifiedAt = posts.getModifiedAt();
        this.comments = getComments();
        this.Likes = posts.getPostsLikes();
    }

}
