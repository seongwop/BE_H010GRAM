package com.hanghae.be_h010gram.domain.posts.service;

import com.hanghae.be_h010gram.domain.posts.dto.PostsRequestDto;
import com.hanghae.be_h010gram.domain.posts.entity.Posts;
import com.hanghae.be_h010gram.domain.posts.repository.PostsRepository;
import com.hanghae.be_h010gram.exception.CustomException;
import com.hanghae.be_h010gram.util.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public List<PostResponseDto> getAllPosts() {
        return postsRepository.findAllByOrderByCreatedAtDesc().stream().map(PostResponseDto::new).collect(Collectors.toList());
    }

    // 선택한 게시물 상세 조회
    @Transactional(readOnly = true)
    public PostResponseDto getPost(Long id) {
        Posts posts = postsRepository.findById(id).orElseThrow(
                () -> new CustomException(POST_NOT_FOUND)
        );
        return new PostsResponseDto(post);
    }

    // 게시물 등록
    @Transactional
    public PostResponseDto createPost(PostsRequestDto postsRequestDto, Members members) {
        Posts post = postsRepository.saveAndFlush(new Posts(postsRequestDto, members));
        return new PostsResponseDto(post);
    }

    // 게시물 수정
    @Transactional
    public PostResponseDto updatePost(Long id, PostsRequestDto postRequestDto, Members members) {

        Posts posts = postsRepository.findById(id).orElseThrow(
                () -> new CustomException(POST_NOT_FOUND)
        );

        if (posts.getMembers().getId().equals(members.getId())) {
            posts.update(postRequestDto);
            return new PostResponseDto(posts);
        } else {
            throw new CustomException(INVALID_USER);
        }
    }

    // 게시물 삭제
    @Transactional
    public UserResponseDto<Posts> deletePost(Long id, Members members) {
        Posts posts = postsRepository.findById(id).orElseThrow(
                () -> new CustomException(POST_NOT_FOUND)
        );

        if (posts.getMembers().getId().equals(members.getId())) {
            postsRepository.delete(post);
            return ResponseDto.setSuccess("게시글 삭제 성공");
        } else {
            throw new CustomException(INVALID_USER);
        }

    }

//    // 좋아요
//    @Transactional
//    public UserResponseDto<Posts> updateLike(Long id) {
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
