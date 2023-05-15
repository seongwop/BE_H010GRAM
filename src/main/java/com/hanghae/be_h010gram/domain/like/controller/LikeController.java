package com.hanghae.be_h010gram.domain.like.controller;

import com.hanghae.be_h010gram.domain.like.dto.LikeResponseDto;
import com.hanghae.be_h010gram.domain.like.service.LikeService;
import com.hanghae.be_h010gram.security.auth.UserDetailsImpl;
import com.hanghae.be_h010gram.util.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api")
public class LikeController {

    private final LikeService likeService;

    // 게시글 좋아요
    @PostMapping("posts/{postId}/likes")
    public ResponseDto<?> likePost(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return likeService.likePost(postId, userDetails.getMember());
    }

    // 게시글 좋아요 취소
    @DeleteMapping("posts/{postId}/likes")
    public ResponseDto<?> likeCancelPost(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return likeService.likeCancelPost(postId, userDetails.getMember());
    }

    //댓글좋아요
    @PostMapping("comments/{commentId}/likes")
    public ResponseDto<LikeResponseDto> likeComment(@PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return likeService.likeComment(commentId, userDetails.getMember());
    }

    //댓글좋아요 취소
    @DeleteMapping("comments/{commentId}/likes")
    public ResponseDto<LikeResponseDto> likeCancelComment(@PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return likeService.likeCancelComment(commentId, userDetails.getMember());
    }
}
