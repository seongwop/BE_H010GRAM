package com.hanghae.be_h010gram.domain.comment.controller;


import com.hanghae.be_h010gram.domain.comment.dto.CommentRequestDto;
import com.hanghae.be_h010gram.domain.comment.dto.CommentResponseDto;
import com.hanghae.be_h010gram.domain.comment.service.CommentService;
import com.hanghae.be_h010gram.security.auth.UserDetailsImpl;
import com.hanghae.be_h010gram.util.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api")
@RequiredArgsConstructor
@Tag(name = "comments", description = "댓글 API")
public class CommentController {

    private final CommentService commentService;

    //댓글 등록
    @PostMapping("posts/{postId}/comments")
    @Operation(summary = "댓글 생성")
    public ResponseDto<CommentResponseDto> saveComment(@PathVariable Long postId,
                                                       @Valid @RequestBody CommentRequestDto commentRequestDto,
                                                       @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.saveComment(postId, commentRequestDto, userDetails.getMember());
    }

    //댓글 전체 조회
    @GetMapping("posts/{postId}/comments")
    @Operation(summary = "해당 게시글 댓글 전체 조회")
    public ResponseDto<List<CommentResponseDto>> getAllComments(@PathVariable Long postId) {
        return commentService.getAllComments(postId); }

    //댓글 수정
    @PutMapping("comments/{commentId}")
    @Operation(summary = "댓글 수정")
    public ResponseDto<CommentResponseDto> modifyComment(@PathVariable Long commentId,
                                           @Valid @RequestBody CommentRequestDto commentRequestDto,
                                           @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.modifyComment(commentId, commentRequestDto, userDetails.getMember());
    }

    //댓글 삭제
    @DeleteMapping("comments/{commentId}")
    @Operation(summary = "댓글 삭제")
    public ResponseDto<?> deleteComment(@PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.deleteComment(commentId, userDetails.getMember());
    }
}
