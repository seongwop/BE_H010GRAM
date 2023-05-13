package com.hanghae.be_h010gram.domain.post.service;

import com.hanghae.be_h010gram.domain.member.entity.Member;
import com.hanghae.be_h010gram.domain.post.dto.PostRequestDto;
import com.hanghae.be_h010gram.domain.post.dto.PostResponseDto;
import com.hanghae.be_h010gram.domain.post.entity.Post;
import com.hanghae.be_h010gram.domain.post.repository.PostLikeRepository;
import com.hanghae.be_h010gram.domain.post.repository.PostRepository;
import com.hanghae.be_h010gram.exception.CustomException;
import com.hanghae.be_h010gram.util.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.hanghae.be_h010gram.exception.ExceptionEnum.INVALID_USER;
import static com.hanghae.be_h010gram.exception.ExceptionEnum.POST_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;
//    private final MemberRepository memberRepository;

    // 전체 게시물 목록 조회
    @Transactional(readOnly = true)
    public List<ResponseDto<PostResponseDto>> getAllPosts() {
        return postRepository
                .findAllByOrderByCreatedAtDesc()
                .stream()
                .map(post -> ResponseDto
                        .setSuccess("전체 게시물 목록 조회 성공", new PostResponseDto(post)))
                .collect(Collectors.toList());
    }

    // 선택한 게시물 상세 조회
    @Transactional(readOnly = true)
    public ResponseDto<PostResponseDto> getPost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new CustomException(POST_NOT_FOUND)
        );
        return ResponseDto.setSuccess(id + "번 게시글 조회 성공", new PostResponseDto(post));
    }

    // 게시물 등록
    @Transactional
    public ResponseDto<PostResponseDto> createPost(PostRequestDto postRequestDto, Member member) {
        Post post = postRepository.save(new Post(postRequestDto, member));
        return ResponseDto.setSuccess("게시글 등록 성공", new PostResponseDto(post));
    }

    // 게시물 수정
    @Transactional
    public ResponseDto<PostResponseDto> updatePost(Long id, PostRequestDto postRequestDto, Member member) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new CustomException(POST_NOT_FOUND)
        );
        if (post.getMember().getId().equals(member.getId())) {
            post.update(postRequestDto);
            return ResponseDto.setSuccess("게시글 수정 성공", new PostResponseDto(post));
        } else {
            throw new CustomException(INVALID_USER);
        }
    }

    // 게시물 삭제
    @Transactional
    public ResponseDto<?> deletePost(Long id, Member member) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new CustomException(POST_NOT_FOUND)
        );
        if (post.getMember().getId().equals(member.getId())) {
            postLikeRepository.deleteById(id);
            postRepository.delete(post);
            return ResponseDto.setSuccess("게시글 삭제 성공", null);
        } else {
            throw new CustomException(INVALID_USER);
        }
    }

//    // 좋아요
//    @Transactional
//    public ResponseDto<> updateLike(Long id) {
//        Post posts = postRepository.findById(id).orElseThrow(
//                () -> new CustomException(POST_NOT_FOUND)
//        );
//
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        Member member = memberRepository.findById(id)).orElseThrow(
//                () -> new CustomException(INVALID_USER)
//        );
//
//        if (postLikeRepository.findByPostAndUser(post, user) == null) {
//            postLikeRepository.save(new PostLike(post, user));
//            post.updateLike(true);
//            return ResponseDto.setSuccess("좋아요 성공");
//        } else {
//            PostLike postLike = postLikeRepository.findByPostAndUser(post, user);
//            postLikeRepository.delete(postLike);
//            post.updateLike(false);
//            return ResponseDto.setSuccess("좋아요 취소");
//        }
//
//    }

}
