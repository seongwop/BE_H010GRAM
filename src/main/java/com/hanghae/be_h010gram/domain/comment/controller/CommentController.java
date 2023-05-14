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
@RequestMapping("comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    //댓글 등록
    @PostMapping("/{postId}")
    public ResponseDto<CommentResponseDto> saveComment(@PathVariable Long postId,
                                                       @Valid @RequestBody CommentRequestDto commentRequestDto,
                                                       @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.saveComment(postId, commentRequestDto, userDetails.getMember());
    }

    //댓글 리스트 전체조회
    @GetMapping("/{commentslist}")
    public List<ResponseDto<CommentResponseDto>> getAllComments() {return commentService.getAllComments(); }

    //댓글 수정
    @PutMapping("/{commentId}")
    public ResponseDto<CommentResponseDto> modifyComment(@PathVariable Long commentId,
                                           @Valid @RequestBody CommentRequestDto commentRequestDto,
                                           @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.modifyComment(commentId, commentRequestDto, userDetails.getMember());
    }

    //댓글 삭제
    @DeleteMapping("/{commentId}")
    public ResponseDto<?> deleteComment(@PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.deleteComment(commentId, userDetails.getMember());
    }

    //댓글좋아요
    @PostMapping("/likes/{commentId}")
    public ResponseDto<?> likeComment(@PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return commentService.likeComment(commentId, userDetails.getMember());
    }
}
