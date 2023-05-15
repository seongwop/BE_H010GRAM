package com.hanghae.be_h010gram.service;

import com.hanghae.be_h010gram.domain.member.entity.Member;
import com.hanghae.be_h010gram.domain.post.dto.PostRequestDto;
import com.hanghae.be_h010gram.domain.post.dto.PostResponseDto;
import com.hanghae.be_h010gram.domain.post.entity.Post;
import com.hanghae.be_h010gram.domain.post.repository.PostRepository;
import com.hanghae.be_h010gram.domain.post.service.PostService;
import com.hanghae.be_h010gram.util.ResponseDto;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Autowired
    PostRepository postRepository;
    @Autowired
    EntityManager em;


//    @Test
//    void getAllPosts() {
//    }
//
    @Test
    @DisplayName("특정 게시글 조회")
    void getPost() {

    }

    @Test
    @DisplayName("게시글 작성")
    @Transactional
    void createPost() {
        // given
        PostRequestDto postRequestDto = new PostRequestDto("test");
        Member member = Member.builder().id(1L).email("test").build();
        Post post = new Post(postRequestDto, member);

        // when
         postRepository.save(post);
         em.flush();
         em.clear();

         Post post1 = postRepository.findById(4L).get();
         // Post post1 = postRepository.findPostFetchJoinById(4L).get();


         Member member1 = post1.getMember();


        // then

        assertEquals(post.getMember().getId(), 1L);
    }
}