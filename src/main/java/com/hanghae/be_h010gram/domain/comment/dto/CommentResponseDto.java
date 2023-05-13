package com.hanghae.be_h010gram.domain.comment.dto;

import com.hanghae.be_h010gram.domain.comment.entity.Comment;
import com.hanghae.be_h010gram.domain.post.dto.PostResponseDto;
import com.hanghae.be_h010gram.domain.post.entity.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {
    private Long id;
    private String nickname;
    private String content;
    private LocalDateTime createdAt;

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.nickname = comment.getContent();
        this.content = comment.getContent();
        this.createdAt = comment.getCreatedAt();
    }
}
//    private boolean likeStatus;
//    private int likeCount;

//    public CommentResponseDto(Comment comment) {
//        this.id = comment.getId();
//        this.nickname = comment.getMember().getNickname();
//        this.content = comment.getContent();
//        this.createdAt = comment.getCreatedAt();
//        this.likeStatus = false;
//        this.likeCount = post.getLikes().size();
//    }

//    public void setLikeStatus(boolean status) {
//        this.likeStatus = status;
//    }
//    @Getter
//    public static class AllPostResponseDto extends PostResponseDto {
//        private String gradient;
//
//        public AllPostResponseDto(Post post) {
//            super(post);
//            //this.gradient = post.getGradient();
//        }
//    }

