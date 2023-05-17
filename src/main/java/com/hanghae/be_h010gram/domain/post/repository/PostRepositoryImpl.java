package com.hanghae.be_h010gram.domain.post.repository;


import com.hanghae.be_h010gram.domain.post.dto.MainPostResponseDto;

import com.querydsl.core.types.Projections;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.hanghae.be_h010gram.domain.post.entity.QPost.post;


@Repository
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public Slice<MainPostResponseDto> findAllPostPageableByOrderByCreatedAtDesc(Pageable pageable) {
        List<MainPostResponseDto> mainPostResponseDtoList = queryFactory.select(Projections.constructor(
                MainPostResponseDto.class, post))
                .from(post)
                .orderBy(post.createdAt.desc())
                .limit(pageable.getPageSize() + 1)
                .fetch();
        boolean hasNext = false;
        if (mainPostResponseDtoList.size() > pageable.getPageSize()) {
            mainPostResponseDtoList.remove(pageable.getPageSize());
            hasNext = true;
        }
        return new SliceImpl<>(mainPostResponseDtoList, pageable, hasNext);
    }
}