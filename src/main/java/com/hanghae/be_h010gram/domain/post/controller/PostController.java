package com.hanghae.be_h010gram.domain.post.controller;

import com.hanghae.be_h010gram.domain.post.dto.MainPostResponseDto;
import com.hanghae.be_h010gram.domain.post.dto.PostRequestDto;
import com.hanghae.be_h010gram.domain.post.dto.PostResponseDto;
import com.hanghae.be_h010gram.domain.post.service.PostService;
import com.hanghae.be_h010gram.security.auth.UserDetailsImpl;
import com.hanghae.be_h010gram.util.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.print.Pageable;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/posts")
public class PostController {
    private final PostService postService;

    // 목록 조회
    @GetMapping
    public ResponseDto<Slice<MainPostResponseDto>> getAllPosts(@RequestParam int page, @RequestParam int size) {
        return postService.getAllPosts(page - 1, size);
    }

    // 상세 조회
    @GetMapping("{id}")
    public ResponseDto<PostResponseDto> getPost(@PathVariable Long id) {
        return postService.getPost(id);
    }

    // 추가
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ResponseDto<PostResponseDto> createPost(@RequestPart(value = "postRequestDto", required = false) PostRequestDto postRequestDto,
                                                   @RequestPart(value = "imageFile", required = false) MultipartFile image,
                                                   @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
        return postService.createPost(postRequestDto, image, userDetails.getMember());
    }

    // 수정
    @PutMapping(value = "{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ResponseDto<PostResponseDto> updatePost(@PathVariable Long id,
                                                   @RequestPart(value = "postRequestDto", required = false) PostRequestDto postRequestDto,
                                                   @RequestPart(value = "imageFile", required = false) MultipartFile image,
                                                   @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
        return postService.updatePost(id, postRequestDto, image, userDetails.getMember());
    }

    // 삭제
    @DeleteMapping("{id}")
    public ResponseDto<?> deletePost(@PathVariable Long id,
                                     @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.deletePost(id, userDetails.getMember());
    }
}
