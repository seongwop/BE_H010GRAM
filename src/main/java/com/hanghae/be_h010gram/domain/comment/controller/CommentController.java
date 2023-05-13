package com.hanghae.be_h010gram.domain.comment.controller;


import com.hanghae.be_h010gram.domain.comment.dto.CommentRequestDto;
import com.hanghae.be_h010gram.domain.comment.dto.CommentResponseDto;
import com.hanghae.be_h010gram.domain.comment.service.CommentService;
import com.hanghae.be_h010gram.security.auth.UserDetailsImpl;
import com.hanghae.be_h010gram.util.ResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;


    //댓글 등록
    @PostMapping("/{postId}/comments")
    public ResponseDto<String> createComment(@PathVariable Long postId,
                                         @Valid @RequestBody CommentRequestDto commentRequestDto,
                                         @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.createComment(postId, commentRequestDto, userDetails.getMember());
    }

    //댓글 수정
    @PutMapping("/{postId}/comments/{commentId}")
    public ResponseDto<CommentResponseDto> updateComment(@PathVariable Long commentId,
                                                         @PathVariable Long postId,
                                                         @Valid @RequestBody CommentRequestDto commentRequestDto,
                                                         @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.updateComment(commentId, postId, commentRequestDto, userDetails.getMember());
    }

    //댓글 삭제
    @DeleteMapping("/{postId}/comments/{commentId}")
    public ResponseDto<String> deleteComment(@PathVariable Long postId,
                                             @PathVariable Long commentId,
                                             @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.deleteComment(postId, commentId, userDetails.getMember());
    }
}
