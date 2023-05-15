package com.hanghae.be_h010gram.domain.post.repository;

import com.hanghae.be_h010gram.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByOrderByCreatedAtDesc();
    @Query("select p from Post p join fetch p.comments c join fetch p.member m where p.id = :id")
    Optional<Post> findPostFetchJoinById(Long id);
}
