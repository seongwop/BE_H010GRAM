package com.hanghae.be_h010gram.domain.post.dto;

import com.hanghae.be_h010gram.domain.post.entity.Post;
import lombok.Getter;

@Getter
public class MainPostResponseDto {
    private Long id;
    private int liked;
    private int commentsCount;
    private String postImage;

    public MainPostResponseDto(Post post, int commentsCount) {
        this.id = post.getId();
        this.liked = post.getLiked();
        this.commentsCount = commentsCount;
        this.postImage = post.getPostImage();
    }
}
