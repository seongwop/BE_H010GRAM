package com.hanghae.be_h010gram.domain.like.controller;

import com.hanghae.be_h010gram.domain.like.service.LikeService;
import com.hanghae.be_h010gram.security.auth.UserDetailsImpl;
import com.hanghae.be_h010gram.util.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("likes")
public class LikeController {

    private final LikeService likeService;

    // 게시글 좋아요
    @PostMapping("{postId}")
    public ResponseDto<?> likePost(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return likeService.likePost(postId, userDetails.getMember());
    }

    // 게시글 좋아요 취소
    @DeleteMapping("{postId}")
    public ResponseDto<?> dislikePost(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return likeService.dislikePost(postId, userDetails.getMember());
    }

    //댓글 좋아요
    @PostMapping("{commentId}")
    public ResponseDto<?> likeComment(@PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return likeService.likeComment(commentId, userDetails.getMember());
    }

    //댓글 좋아요 취소
    @DeleteMapping("{commentId}")
    public ResponseDto<?> dislikeComment(@PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return likeService.dislikeComment(commentId, userDetails.getMember());
    }
}
