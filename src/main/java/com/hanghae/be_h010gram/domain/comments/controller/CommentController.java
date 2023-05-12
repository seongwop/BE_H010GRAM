package com.hanghae.be_h010gram.domain.comments.controller;


import com.hanghae.be_h010gram.security.auth.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments/")
@RequiredArgsConstructor
public class CommentController  {

    private final CommentService commentService;

    // 댓글 작성하기
    @PostMapping("/{postId}")
    public CommentResponseDto commentWrite(@PathVariable Long boardId, @RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.write(boardId,commentRequestDto,userDetails.getUser());
    }

    // 댓글 수정하기
    @PutMapping("/{commentId}")
    public CommentResponseDto commentUpdate(@PathVariable Long commentId, @RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.update(commentId, commentRequestDto, userDetails.getUser());
    }

    // 댓글 삭제하기
    @DeleteMapping("/{commentId}")
    public ResponseEntity<> commentDelete(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.delete(id,userDetails.getUser());
    }


}

