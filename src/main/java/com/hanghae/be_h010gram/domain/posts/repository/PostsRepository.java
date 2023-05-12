package com.hanghae.be_h010gram.domain.posts.repository;

import com.hanghae.be_h010gram.domain.member.entity.Member;
import com.hanghae.be_h010gram.domain.posts.entity.Posts;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostsRepository extends JpaRepository<Member, Long> {
    List<Posts> findAllByOrderByCreatedAtDesc();
}
