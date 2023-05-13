package com.hanghae.be_h010gram.domain.comment.repository;

import com.hanghae.be_h010gram.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
