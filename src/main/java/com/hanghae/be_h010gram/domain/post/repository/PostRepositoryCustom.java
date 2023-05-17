package com.hanghae.be_h010gram.domain.post.repository;

import com.hanghae.be_h010gram.domain.post.dto.MainPostResponseDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;


public interface PostRepositoryCustom {
    Slice<MainPostResponseDto> findAllPostPageableByOrderByCreatedAtDesc(Pageable pageable);
}
