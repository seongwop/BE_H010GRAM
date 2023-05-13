package com.hanghae.be_h010gram.domain.comments.service;

import com.hanghae.be_h010gram.domain.comments.dto.CommentRequestDto;
import com.hanghae.be_h010gram.domain.comments.dto.CommentResponseDto;
import com.hanghae.be_h010gram.domain.comments.entity.Comment;
import com.hanghae.be_h010gram.domain.comments.repository.CommentRepository;
import com.hanghae.be_h010gram.domain.member.entity.Member;
import com.hanghae.be_h010gram.domain.posts.Post;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class CommentService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final CommentResponseDto commentResponseDto;

    /**
     * 댓글 등록
     */
    @Transactional
    public ResponseEntity<CommentResponseDto> saveComment(Long id, CommentRequestDto commentRequestDto, Member member) {
        Post post = validateExistPost(id);
        Comment comment = new Comment(commentRequestDto, post, member);
        return ResponseEntity.status(HttpStatus.CREATED).body(commentResponseDto);
    }

    /**
     * 댓글 수정
     */
    @Transactional
    public ResponseEntity<CommentResponseDto> modifyComment(Long id, CommentRequestDto commentRequestDto, Member member) {
        Comment comment = validateExistComment(id);
        validateCommentAuthor(member, comment);
        comment.modify(commentRequestDto);
        return ResponseDto.setSuccess(comment.getId());
    }

    /**
     * 댓글 삭제
     */
    @Transactional
    public ResponseEntity<CommentResponseDto> deleteComment(Long id, Member member) {
        Comment comment = validateExistComment(id);
        validateCommentAuthor(member, comment);
        comment.getPost().getComments().remove(comment);
        return ResponseDto.setSuccess(null);
    }

    public void validateCommentAuthor(Member member, Comment comment) {
        if (!member.getId().equals(comment.getMember().getId())) {
            throw new IllegalArgumentException(ExceptionMessage.NO_AUTHORIZATION.getMessage());
        }
    }

    public Comment validateExistComment(Long id) {
        return commentRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException(ExceptionMessage.NO_EXIST_COMMENT.getMessage())
        );
    }
    public void validatePostAuthor(Member member, Post post) {
        if (!member.getNickname().equals(post.getNickname())) {
            throw new IllegalArgumentException(ExceptionMessage.NO_AUTHORIZATION.getMessage());
        }
    }

    public Post validateExistPost(Long id) {
        return postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException(ExceptionMessage.NO_EXIST_POST.getMessage())
        );
    }
}
