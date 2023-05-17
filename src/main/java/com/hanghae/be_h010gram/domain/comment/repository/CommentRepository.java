package com.hanghae.be_h010gram.domain.comment.repository;

import com.hanghae.be_h010gram.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByPostIdOrderByCreatedAtDesc(Long postId);

    int countByPostId(Long id);

}
