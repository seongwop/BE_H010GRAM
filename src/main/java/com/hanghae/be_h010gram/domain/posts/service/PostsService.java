package com.hanghae.be_h010gram.domain.posts.service;

import com.hanghae.be_h010gram.domain.posts.dto.PostsRequestDto;
import com.hanghae.be_h010gram.domain.posts.dto.PostsResponseDto;
import com.hanghae.be_h010gram.domain.posts.entity.Posts;
import com.hanghae.be_h010gram.domain.posts.repository.PostsRepository;
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
public class PostsService {

    private final PostsRepository postsRepository;
//    private final PostsLikeRepository postLikeRepository;
//    private final MembersRepository userRepository;

    // 전체 게시물 목록 조회
    @Transactional(readOnly = true)
    public List<ResponseDto<PostsResponseDto>> getAllPosts() {
        return postsRepository
                .findAllByOrderByCreatedAtDesc()
                .stream()
                .map(post -> ResponseDto
                        .setSuccess("전체 게시물 목록 조회 성공", new PostsResponseDto(post)))
                .collect(Collectors.toList());
    }

    // 선택한 게시물 상세 조회
    @Transactional(readOnly = true)
    public ResponseDto<PostsResponseDto> getPost(Long id) {
        Posts posts = postsRepository.findById(id).orElseThrow(
                () -> new CustomException(POST_NOT_FOUND)
        );
        return ResponseDto.setSuccess(id + "번 게시글 조회 성공", new PostsResponseDto(posts));
    }

    // 게시물 등록
    @Transactional
    public ResponseDto<PostsResponseDto> createPost(PostsRequestDto postsRequestDto, Members members) {
        Posts posts = postsRepository.save(new Posts(postsRequestDto, members));
        return ResponseDto.setSuccess("게시글 등록 성공", new PostsResponseDto(posts));
    }

    // 게시물 수정
    @Transactional
    public ResponseDto<PostsResponseDto> updatePost(Long id, PostsRequestDto postRequestDto, Members members) {
        Posts posts = postsRepository.findById(id).orElseThrow(
                () -> new CustomException(POST_NOT_FOUND)
        );
        if (posts.getMember().getId().equals(members.getId())) {
            posts.update(postsRequestDto);
            return ResponseDto.setSuccess("게시글 수정 성공", new PostsResponseDto(posts));
        } else {
            throw new CustomException(INVALID_USER);
        }
    }

    // 게시물 삭제
    @Transactional
    public ResponseDto<PostsResponseDto> deletePost(Long id, Members members) {
        Posts posts = postsRepository.findById(id).orElseThrow(
                () -> new CustomException(POST_NOT_FOUND)
        );
        if (posts.getMembers().getId().equals(members.getId())) {
            postsLikeRepository.deleteByPost(posts);
            postsRepository.delete(posts);
            return ResponseDto.setSuccess("게시글 삭제 성공", null);
        } else {
            throw new CustomException(INVALID_USER);
        }
    }

//    // 좋아요
//    @Transactional
//    public ResponseDto<> updateLike(Long id) {
//        Posts posts = postsRepository.findById(id).orElseThrow(
//                () -> new CustomException(POST_NOT_FOUND)
//        );
//
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        Members members = membersRepository.findByUsername(authentication.getName()).orElseThrow(
//                () -> new CustomException(INVALID_USER)
//        );
//
//        if (postLikeRepository.findByPostAndUser(post, user) == null) {
//            postLikeRepository.save(new PostLike(post, user));
//            post.updateLike(true);
//            return UserResponseDto.setSuccess("좋아요 성공");
//        } else {
//            PostLike postLike = postLikeRepository.findByPostAndUser(post, user);
//            postLikeRepository.delete(postLike);
//            post.updateLike(false);
//            return UserResponseDto.setSuccess("좋아요 취소");
//        }
//
//    }

}
