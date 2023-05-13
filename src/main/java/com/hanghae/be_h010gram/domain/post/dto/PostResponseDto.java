package com.hanghae.be_h010gram.domain.post.dto;

import com.hanghae.be_h010gram.domain.comments.entity.Comment;
import com.hanghae.be_h010gram.domain.member.entity.Member;
import com.hanghae.be_h010gram.domain.post.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class PostResponseDto {
    private Long id;
    private String content;
    private Member member;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private List<Comment> comment;
//    private int like;

    public PostResponseDto(Post post) {
        this.id = post.getId();
        this.content = post.getContent();
        this.member = post.getMember();
        this.createdAt = post.getCreatedAt();
        this.modifiedAt = post.getModifiedAt();
        this.comment = getComment();
//        this.like = post.getPostLike();
    }

}
