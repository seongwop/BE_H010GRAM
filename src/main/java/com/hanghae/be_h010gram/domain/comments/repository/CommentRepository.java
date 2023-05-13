package com.hanghae.be_h010gram.domain.comments.repository;

import com.hanghae.be_h010gram.domain.comments.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
