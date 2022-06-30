package com.springboot.blog.service;

import com.springboot.blog.payload.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(long postId, CommentDto commentDto);
    List<CommentDto> allCommentsByPostId(long postId);

    CommentDto commentById(long postId, long commentId);
}
