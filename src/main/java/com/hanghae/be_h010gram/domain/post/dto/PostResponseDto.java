package com.hanghae.be_h010gram.domain.post.dto;

import com.hanghae.be_h010gram.domain.comment.dto.CommentResponseDto;
import com.hanghae.be_h010gram.domain.post.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class PostResponseDto {
//    private Long id;
    private String content;
    private String nickname;
    private LocalDateTime createdAt;
//    private LocalDateTime modifiedAt;
    private List<CommentResponseDto> comment;
    private int like;

    public PostResponseDto(Post post) {
//        this.id = post.getId();
        this.content = post.getContent();
        this.nickname = post.getMember().getNickname();
        this.createdAt = post.getCreatedAt();
//        this.modifiedAt = post.getModifiedAt();
        this.comment = CommentResponseDto.ofList(post.getComments());
        this.like = post.getPostLike();
    }

}
