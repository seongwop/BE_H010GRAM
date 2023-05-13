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
@RequestMapping("/post/{postId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;


    //댓글 등록
    @PostMapping("")
    public ResponseDto<String> createComment(@PathVariable Long postId,
                                         @Valid @RequestBody CommentRequestDto commentRequestDto,
                                         @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.createComment(postId, commentRequestDto, userDetails.getMember());
    }

    //댓글 수정
    @PutMapping("/{commentId}")
    public ResponseDto<CommentResponseDto> updateComment(@PathVariable Long id,
                                                         @Valid @RequestBody CommentRequestDto commentRequestDto,
                                                         @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.updateComment(id, commentRequestDto, userDetails.getMember());
    }

    //댓글 삭제
    @DeleteMapping("/{commentId}")
    public ResponseDto<?> deleteComment(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.deleteComment(id, userDetails.getMember());
    }
}
