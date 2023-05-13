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

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class CommentService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    //댓글 등록
    @Transactional
    public ResponseDto<String> createComment(Long id, CommentRequestDto commentRequestDto, Member member) {
       //게시글 조회
        Post post = isExistPost(id);
        //댓글 생성
        Comment comment = new Comment(commentRequestDto);

        //댓글 엔티티에 작성한 멤버 / 게시글 설정
        comment.setMember(member);
        comment.setPost(post);

        commentRepository.save(comment);
        return ResponseDto.setSuccess("success");
    }

    //댓글 수정
    @Transactional
    public ResponseDto<CommentResponseDto> updateComment(Long id, CommentRequestDto commentRequestDto, Member member) {
        Comment comment = isExistComment(id);

        //작성자가 맞는지 확인
        isMemberEqual(member, comment);

        comment.update(commentRequestDto);
        return ResponseDto.setSuccess("댓글 수정 성공", new CommentResponseDto(comment));
    }

    /**
     * 댓글 삭제
     */
    @Transactional
    public ResponseDto<?> deleteComment(Long id, Member member) {
        Comment comment = isExistComment(id);
        isMemberEqual(member, comment);
        comment.getPost().getComments().remove(comment);
        return ResponseDto.setSuccess("댓글 삭제 성공",null);
    }

    public void isMemberEqual(Member member, Comment comment) {
        if (!member.getId().equals(comment.getMember().getId())) {
            throw new CustomException(ExceptionEnum.INVALID_USER);
        }
    }

    public Comment isExistComment(Long id) {
        return commentRepository.findById(id).orElseThrow(
                () -> new CustomException(ExceptionEnum.COMMENT_NOT_FOUND)
        );
    }

    public Post isExistPost(Long id) {
        return postRepository.findById(id).orElseThrow(
                () -> new CustomException(ExceptionEnum.POST_NOT_FOUND)
        );
    }
}
