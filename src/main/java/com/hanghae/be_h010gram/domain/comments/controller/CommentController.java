package com.hanghae.be_h010gram.domain.comments.controller;


import com.hanghae.be_h010gram.domain.comments.dto.CommentRequestDto;
import com.hanghae.be_h010gram.domain.comments.service.CommentService;
import com.hanghae.be_h010gram.security.auth.UserDetailsImpl;
import com.hanghae.be_h010gram.util.ResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;


    //댓글 등록
    @PostMapping("/{postId}")
    public ResponseDto<Long> saveComment(@PathVariable Long id,
                                         @Valid @RequestBody CommentRequestDto commentRequestDto,
                                         @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.saveComment(id, commentRequestDto, userDetails.getMember());
    }

    //댓글 수정
    @PutMapping("/{commentId}")
    public ResponseDto<Long> modifyComment(@PathVariable Long id,
                                           @Valid @RequestBody CommentRequestDto commentRequestDto,
                                           @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.modifyComment(id, commentRequestDto, userDetails.getMember());
    }

    //댓글 삭제
    @DeleteMapping("/{commentId}")
    public ResponseDto<?> deleteComment(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.deleteComment(id, userDetails.getMember());
    }
}
