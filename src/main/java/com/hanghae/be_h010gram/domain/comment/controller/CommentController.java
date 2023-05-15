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

import java.util.List;


@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    //댓글 등록
    @PostMapping("{postId}/comments")
    public ResponseDto<CommentResponseDto> saveComment(@PathVariable Long postId,
                                                       @Valid @RequestBody CommentRequestDto commentRequestDto,
                                                       @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.saveComment(postId, commentRequestDto, userDetails.getMember());
    }

    //댓글 전체 조회
    @GetMapping("{postId}/comments")
    public ResponseDto<List<CommentResponseDto>> getAllComments(@PathVariable Long postId) {
        return commentService.getAllComments(postId); }

    //댓글 수정
    @PutMapping("comments/{commentId}")
    public ResponseDto<CommentResponseDto> modifyComment(@PathVariable Long commentId,
                                           @Valid @RequestBody CommentRequestDto commentRequestDto,
                                           @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.modifyComment(commentId, commentRequestDto, userDetails.getMember());
    }

    //댓글 삭제
    @DeleteMapping("comments/{commentId}")
    public ResponseDto<?> deleteComment(@PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.deleteComment(commentId, userDetails.getMember());
    }
}
