package com.hanghae.be_h010gram.domain.comment.service;

import com.hanghae.be_h010gram.domain.comment.dto.CommentRequestDto;
import com.hanghae.be_h010gram.domain.comment.dto.CommentResponseDto;
import com.hanghae.be_h010gram.domain.comment.entity.Comment;
import com.hanghae.be_h010gram.domain.comment.repository.CommentRepository;
import com.hanghae.be_h010gram.domain.member.entity.Member;
import com.hanghae.be_h010gram.domain.post.entity.Post;
import com.hanghae.be_h010gram.domain.post.repository.PostRepository;
import com.hanghae.be_h010gram.exception.CustomException;
import com.hanghae.be_h010gram.exception.ExceptionEnum;
import com.hanghae.be_h010gram.util.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class CommentService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    /**
     * 댓글 등록
     */
    @Transactional
    public ResponseDto<CommentResponseDto> saveComment(Long id, CommentRequestDto commentRequestDto, Member member) {
        Post post = validateExistPost(id);

        Comment comment = commentRepository.save(new Comment(commentRequestDto, post, member));
        return ResponseDto.setSuccess("댓글 등록 성공", new CommentResponseDto(comment));
    }

    /**
     * 댓글 전체 조회
     */

    public ResponseDto<List<CommentResponseDto>> getAllComments() {

        return ResponseDto.setSuccess("댓글 전체 조회 성공",commentRepository
                .findAllByOrderByCreatedAtDesc()
                .stream().map(CommentResponseDto::new).collect(Collectors.toList()));
    }

    /**
     * 댓글 수정
     */
    @Transactional
    public ResponseDto<CommentResponseDto> modifyComment(Long id, CommentRequestDto commentRequestDto, Member member) {
        Comment comment = validateExistComment(id);
        validateCommentAuthor(member, comment);
        comment.modify(commentRequestDto);
        return ResponseDto.setSuccess("댓글 수정 성공", new CommentResponseDto(comment));
    }

    /**
     * 댓글 삭제
     */
    @Transactional
    public ResponseDto<?> deleteComment(Long id, Member member) {
        Comment comment = validateExistComment(id);
        validateCommentAuthor(member, comment);
        commentRepository.delete(comment);
        return ResponseDto.setSuccess("댓글 삭제 성공");
    }

    public void validateCommentAuthor(Member member, Comment comment) {
        if (!member.getId().equals(comment.getMember().getId())) {
            throw new CustomException(ExceptionEnum.COMMENT_NOT_FOUND);
        }
    }

    public Comment validateExistComment(Long id) {
        return commentRepository.findById(id).orElseThrow(() -> new CustomException(ExceptionEnum.COMMENT_NOT_FOUND));
    }

    public Post validateExistPost(Long id) {
        return postRepository.findById(id).orElseThrow(() -> new CustomException(ExceptionEnum.POST_NOT_FOUND));

    }
}
