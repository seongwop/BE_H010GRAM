//package com.hanghae.be_h010gram.domain.posts.entity;
//
//import com.fasterxml.jackson.annotation.JsonManagedReference;
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//@Getter
//@Entity
//@NoArgsConstructor
//public class PostsLikes {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne
//    @JsonManagedReference
//    @JoinColumn(nullable = false)
//    private Members members;
//
//    @ManyToOne
//    @JsonManagedReference
//    @JoinColumn(name = "post_id", nullable = false)
//    private Posts posts;
//
//    public PostLike(Posts post, Members members) {
//        this.posts = post;
//        this.members = members;
//    }
//
//}
