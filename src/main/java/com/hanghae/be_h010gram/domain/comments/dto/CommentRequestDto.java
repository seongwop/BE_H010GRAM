package com.hanghae.be_h010gram.domain.comments.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CommentRequestDto {
    @Size(max = 255, message = "{comment}")
    private String content;
}
