package com.hanghae.be_h010gram.domain.comment.dto;

import com.hanghae.be_h010gram.domain.comment.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class CommentResponseDto {
    //private Long id;
    private String nickname;
    private String content;
    private int liked;
    private LocalDateTime createdAt;

    public CommentResponseDto(Comment comment) {
        //this.id = comment.getId();
        this.nickname = comment.getMember().getNickname();
        this.content = comment.getContent();
        this.liked = comment.getLiked();
        this.createdAt = comment.getCreatedAt();
    }

    public static List<CommentResponseDto> ofList(List<Comment> comments) {
        return comments.stream().map(CommentResponseDto::new).collect(Collectors.toList());
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

