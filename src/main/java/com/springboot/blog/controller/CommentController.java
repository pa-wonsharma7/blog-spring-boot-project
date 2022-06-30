package com.springboot.blog.controller;

import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/blog/")
public class CommentController {

  @Autowired private CommentService commentService;

  @PostMapping(value = "/posts/{postId}/comments")
  public ResponseEntity<CommentDto> createComment(
      @PathVariable(value = "postId") long postId, @RequestBody CommentDto commentDto) {
    return new ResponseEntity<>(
        commentService.createComment(postId, commentDto), HttpStatus.CREATED);
  }

  @GetMapping(value = "/posts/{postId}/comments")
  public List<CommentDto> allComments(@PathVariable(value = "postId") long postId) {
    return commentService.allCommentsByPostId(postId);
  }

  @GetMapping(value = "/posts/{postId}/comments/{id}")
  public ResponseEntity<CommentDto> commentById(
      @PathVariable(value = "postId") long postId, @PathVariable(value = "id") long commentId) {
    CommentDto commentDto = commentService.commentById(postId, commentId);
    return new ResponseEntity<>(commentDto, HttpStatus.OK);
  }
}
