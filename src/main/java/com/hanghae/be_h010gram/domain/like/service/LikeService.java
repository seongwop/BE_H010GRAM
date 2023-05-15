package com.hanghae.be_h010gram.domain.like.service;

import com.hanghae.be_h010gram.domain.comment.entity.Comment;
import com.hanghae.be_h010gram.domain.comment.repository.CommentRepository;
import com.hanghae.be_h010gram.domain.like.dto.LikeResponseDto;
import com.hanghae.be_h010gram.domain.like.entity.CommentLike;
import com.hanghae.be_h010gram.domain.like.entity.PostLike;
import com.hanghae.be_h010gram.domain.like.repository.CommentLikeRepository;
import com.hanghae.be_h010gram.domain.like.repository.PostLikeRepository;
import com.hanghae.be_h010gram.domain.member.entity.Member;
import com.hanghae.be_h010gram.domain.member.repository.MemberRepository;
import com.hanghae.be_h010gram.domain.post.entity.Post;
import com.hanghae.be_h010gram.domain.post.repository.PostRepository;
import com.hanghae.be_h010gram.exception.CustomException;
import com.hanghae.be_h010gram.exception.ExceptionEnum;
import com.hanghae.be_h010gram.util.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.hanghae.be_h010gram.exception.ExceptionEnum.*;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;
    private final CommentLikeRepository commentLikeRepository;

    //댓글 좋아요
    @Transactional
    public ResponseDto<LikeResponseDto> likeComment(Long commentId, Member member) {
        // 댓글 존재확인.
        Comment comment = commentRepository.findById(commentId).orElseThrow(() ->
             new CustomException(ExceptionEnum.COMMENT_NOT_FOUND)
        );

        Member existingMember = memberRepository.findById(member.getId()).orElseThrow(() ->
             new CustomException(INVALID_USER)
        );

        boolean isPostLikedByMember = commentLikeRepository.existsByCommentAndMember(comment, existingMember);
        if (isPostLikedByMember) {
            throw new CustomException(INVALID_LIKE);
        }

        commentLikeRepository.save(new CommentLike(comment, existingMember));
        comment.updateLike(true);
        return ResponseDto.setSuccess("댓글 좋아요 성공");
    }

    //댓글 좋아요 취소
    @Transactional
    public ResponseDto<LikeResponseDto> likeCancelComment(Long commentId, Member member) {
        // 댓글 존재확인.
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new CustomException(ExceptionEnum.COMMENT_NOT_FOUND));
        // 회원 확인.
        Member existingMember = memberRepository.findById(member.getId()).orElseThrow(() -> new CustomException(INVALID_USER));

        boolean isCommentLikedByMember = commentLikeRepository.existsByCommentAndMember(comment, existingMember);
        if (!isCommentLikedByMember) {
            throw new CustomException(INVALID_LIKE_CANCEL);
        }

        CommentLike commentLike = commentLikeRepository.findByCommentAndMember(comment, existingMember);
        commentLikeRepository.delete(commentLike);
        comment.updateLike(false);
        return ResponseDto.setSuccess("댓글 좋아요 취소 성공");
    }


    // 게시글 좋아요
    @Transactional
    public ResponseDto<?> likePost(Long id, Member member) {
        Post post = postRepository.findById(id).orElseThrow(() -> new CustomException(POST_NOT_FOUND));

        Member existingMember = memberRepository.findById(member.getId()).orElseThrow(() -> new CustomException(INVALID_USER));

        boolean isPostLikedByMember = postLikeRepository.existsByPostAndMember(post, existingMember);
        if (isPostLikedByMember) {
            throw new CustomException(INVALID_LIKE);
        }

        postLikeRepository.save(new PostLike(post, existingMember));
        post.updateLike(true);
        return ResponseDto.setSuccess("게시글 좋아요 성공");
    }

    // 게시글 좋아요 취소
    @Transactional
    public ResponseDto<?> likeCancelPost(Long postId, Member member) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new CustomException(ExceptionEnum.COMMENT_NOT_FOUND));

        Member existingMember = memberRepository.findById(member.getId()).orElseThrow(() -> new CustomException(INVALID_USER));

        boolean isPostLikedByMember = postLikeRepository.existsByPostAndMember(post, existingMember);
        if (!isPostLikedByMember) {
            throw new CustomException(INVALID_LIKE_CANCEL);
        }

        PostLike postLike = postLikeRepository.findByPostAndMember(post, existingMember);
        postLikeRepository.delete(postLike);
        post.updateLike(false);
        return ResponseDto.setSuccess("게시글 좋아요 취소 성공");
    }
}
