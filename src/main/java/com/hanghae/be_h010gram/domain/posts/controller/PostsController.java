package com.hanghae.be_h010gram.domain.posts.controller;

import com.hanghae.be_h010gram.domain.posts.dto.PostsRequestDto;
import com.hanghae.be_h010gram.domain.posts.entity.Posts;
import com.hanghae.be_h010gram.domain.posts.service.PostsService;
import com.hanghae.be_h010gram.security.auth.UserDetailsImpl;
import com.hanghae.be_h010gram.util.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostsController {
    private final PostsService postsService;

    // 목록 조회
    @GetMapping("/posts")
    public ResponseDto<List<Posts>> getAllPosts() {
        return postsService.getAllPosts();
    }

    // 상세 조회
    @GetMapping("/posts/{id}")
    public ResponseDto<Posts> getPost(@PathVariable Long id) {
        return postsService.getPost(id);
    }

    // 추가
    @PostMapping("/post")
    public ResponseDto<Posts> createPost(@RequestBody PostsRequestDto postsRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postsService.createPost(postsRequestDto, userDetails.getMember());
    }

    // 수정
    @PutMapping("/post/{id}")
    public ResponseDto<Posts> updatePost(@PathVariable Long id, @RequestBody PostsRequestDto postsRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postsService.updatePost(id, postsRequestDto, userDetails.getMember());
    }

    // 삭제
    @DeleteMapping("/post/{id}")
    public ResponseDto<Posts> deletePost(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postsService.deletePost(id, userDetails.getMember());
    }

}
