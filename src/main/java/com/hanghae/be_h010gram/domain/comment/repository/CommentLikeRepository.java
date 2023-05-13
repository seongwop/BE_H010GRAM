package com.hanghae.be_h010gram.domain.comment.repository;

import com.hanghae.be_h010gram.domain.comment.entity.Comment;
import com.hanghae.be_h010gram.domain.comment.entity.CommentLike;
import com.hanghae.be_h010gram.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    CommentLike findByMemberAndComment(Member member, Comment comment);
}
