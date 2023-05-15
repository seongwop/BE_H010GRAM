package com.hanghae.be_h010gram.domain.like.repository;

import com.hanghae.be_h010gram.domain.like.entity.PostLike;
import com.hanghae.be_h010gram.domain.member.entity.Member;
import com.hanghae.be_h010gram.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    PostLike findByPostAndMember(Post post, Member member);
}
