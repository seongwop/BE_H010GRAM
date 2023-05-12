package com.hanghae.be_h010gram.domain.posts.entity;

import com.hanghae.be_h010gram.util.Timestamped;
import jakarta.persistence.Entity;

import java.util.List;

@Entity
public class PostsEntity extends Timestamped {
    private String postsId;
    private String content;
    private Member member;

}
