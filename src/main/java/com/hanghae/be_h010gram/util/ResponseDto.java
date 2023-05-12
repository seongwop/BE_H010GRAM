package com.hanghae.be_h010gram.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor(staticName = "set")
public class ResponseDto<T> {
    private int status;
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    public static <T> ResponseDto<T> setSuccess(int status, String message, T data) {
        return ResponseDto.set(status, message, data);
    }

    public static <T> ResponseDto<T> setSuccess(int status, String message) {
        return ResponseDto.set(status, message, null);
    }
}