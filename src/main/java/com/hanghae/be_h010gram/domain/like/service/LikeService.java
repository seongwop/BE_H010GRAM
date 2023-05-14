package com.hanghae.be_h010gram.domain.like.service;

import com.hanghae.be_h010gram.domain.comment.entity.Comment;

import com.hanghae.be_h010gram.domain.comment.repository.CommentRepository;

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

import static com.hanghae.be_h010gram.exception.ExceptionEnum.INVALID_USER;
import static com.hanghae.be_h010gram.exception.ExceptionEnum.POST_NOT_FOUND;

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
    public ResponseDto<?> likeComment(Long commentId, Member member) {
        // 댓글 존재확인.
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new CustomException(ExceptionEnum.COMMENT_NOT_FOUND));

        if (commentLikeRepository.findByMemberAndComment(member, comment) == null) {
            comment.plusLiked();
            commentLikeRepository.save(new CommentLike(member, comment));
            return ResponseDto.setSuccess("댓글 좋아요 성공");

        } else {
            CommentLike commentLike = commentLikeRepository.findByMemberAndComment(member, comment);
            comment.minusLiked();
            commentLikeRepository.delete(commentLike);
            return ResponseDto.setSuccess("댓글 좋아요 취소 성공");
        }
    }

    // 좋아요
    @Transactional
    public ResponseDto<?> updateLike(Long id, Member member) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new CustomException(POST_NOT_FOUND)
        );

        memberRepository.findById(member.getId()).orElseThrow(
                () -> new CustomException(INVALID_USER)
        );

        if (postLikeRepository.findByPostAndMember(post, member) == null) {
            postLikeRepository.save(new PostLike(post, member));
            post.updateLike(true);
            return ResponseDto.setSuccess("좋아요 성공");
        } else {
            PostLike postLike = postLikeRepository.findByPostAndMember(post, member);
            postLikeRepository.delete(postLike);
            post.updateLike(false);
            return ResponseDto.setSuccess("좋아요 취소");
        }
    }
}
