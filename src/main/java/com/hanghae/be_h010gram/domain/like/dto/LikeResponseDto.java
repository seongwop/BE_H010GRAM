package com.hanghae.be_h010gram.domain.like.dto;

import com.hanghae.be_h010gram.domain.comment.entity.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LikeResponseDto {
    private boolean likeStatus;
    private int likeCount;

    public LikeResponseDto(Comment comment, boolean likeStatus) {
        this.likeStatus = likeStatus;
        this.likeCount = comment.getCommentLikes().size();
    }
}
