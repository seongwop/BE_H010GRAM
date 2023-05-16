package com.hanghae.be_h010gram.domain.post.service;

import com.hanghae.be_h010gram.domain.comment.repository.CommentRepository;
import com.hanghae.be_h010gram.domain.like.repository.PostLikeRepository;
import com.hanghae.be_h010gram.domain.member.entity.Member;
import com.hanghae.be_h010gram.domain.post.dto.MainPostResponseDto;
import com.hanghae.be_h010gram.domain.post.dto.PostRequestDto;
import com.hanghae.be_h010gram.domain.post.dto.PostResponseDto;
import com.hanghae.be_h010gram.domain.post.entity.Post;
import com.hanghae.be_h010gram.domain.post.repository.PostRepository;
import com.hanghae.be_h010gram.exception.CustomException;
import com.hanghae.be_h010gram.util.ResponseDto;
import com.hanghae.be_h010gram.util.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static com.hanghae.be_h010gram.exception.ExceptionEnum.*;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;
    private final CommentRepository commentRepository;
    private final S3Service s3Service;

    // 전체 게시물 목록 조회
    @Transactional(readOnly = true)
    public ResponseDto<List<MainPostResponseDto>> getAllPosts() {
        List<MainPostResponseDto> mainPostResponseDtos = postRepository
                .findAllByOrderByCreatedAtDesc()
                .stream()
                .map(post -> new MainPostResponseDto(post, commentRepository.countByPostId(post.getId())))
                .collect(Collectors.toList());
        return ResponseDto.setSuccess("전체 게시물 조회 성공", mainPostResponseDtos);
    }

    // 선택한 게시물 상세 조회
    @Transactional(readOnly = true)
    public ResponseDto<PostResponseDto> getPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new CustomException(POST_NOT_FOUND));
        return ResponseDto.setSuccess(postId + "번 게시글 조회 성공", new PostResponseDto(post));
    }

    // 내 게시글 목록 조회
    @Transactional(readOnly = true)
    public ResponseDto<List<MainPostResponseDto>> getMyPosts(Member member) {
        List<MainPostResponseDto> mainPostResponseDtos = postRepository
                .findAllByMemberIdOrderByCreatedAtDesc(member.getId())
                .stream()
                .map(post -> new MainPostResponseDto(post, commentRepository.countByPostId(post.getId())))
                .collect(Collectors.toList());
        return ResponseDto.setSuccess("내 게시물 조회 성공", mainPostResponseDtos);
    }

    // 게시물 등록
    @Transactional
    public ResponseDto<PostResponseDto> createPost(PostRequestDto postRequestDto, MultipartFile image, Member member) throws IOException {
        if (image == null || image.isEmpty()) {
            throw new CustomException(FILE_UNUPLOADED);
        }

        String imageUrl = s3Service.uploadFile(image);

        Post post = new Post(postRequestDto, member);
        post.setPostImage(imageUrl);
        postRepository.save(post);

        return ResponseDto.setSuccess("게시글 등록 성공", new PostResponseDto(post));
    }

    // 게시물 수정
    @Transactional
    public ResponseDto<PostResponseDto> updatePost(Long postId, PostRequestDto postRequestDto, MultipartFile image, Member member) throws IOException {
        Post post = postRepository.findById(postId).orElseThrow(() -> new CustomException(POST_NOT_FOUND));
        if (!post.getMember().getId().equals(member.getId())) {
            throw new CustomException(INVALID_USER);
        }

        post.update(postRequestDto);

        if (image != null && !image.isEmpty()) {
            s3Service.delete(post.getPostImage());
            String imageUrl = s3Service.uploadFile(image);
            post.setPostImage(imageUrl);
        }

        postRepository.save(post);
        return ResponseDto.setSuccess("게시글 수정 성공", new PostResponseDto(post));
    }

    // 게시물 삭제
    @Transactional
    public ResponseDto<?> deletePost(Long postId, Member member) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new CustomException(POST_NOT_FOUND));
        if (post.getMember().getId().equals(member.getId())) {
            postLikeRepository.deleteById(postId);
            postRepository.delete(post);
            return ResponseDto.setSuccess("게시글 삭제 성공", null);
        } else {
            throw new CustomException(INVALID_USER);
        }
    }
}