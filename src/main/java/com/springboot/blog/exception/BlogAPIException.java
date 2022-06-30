package com.springboot.blog.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BlogAPIException extends RuntimeException{
    private final HttpStatus httpStatus;
    private final String message;

    public BlogAPIException(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public BlogAPIException(HttpStatus httpStatus, String message1, String message2) {
        super(message1);
        this.httpStatus = httpStatus;
        this.message = message2;
    }
}
