package com.springboot.blog.controller;

import com.springboot.blog.entity.Post;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/blog/posts")
public class PostController {

    /*
    When the class has only one constructor for another service or class then @Autowired annotation is
    not required and automatically added by Spring-boot.
     */
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    // create blog post rest api
    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto) {
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }

    // get all post rest api
    @GetMapping
    public PostResponse allPosts(
            @RequestParam(value = "pageNo", defaultValue = "0") int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "8") int pageSize
    ) {
        return postService.allPosts(pageNo, pageSize);
    }

    // get post by id rest api
    @GetMapping(value = "/{id}")
    public ResponseEntity<PostDto> postById(@PathVariable(name = "id") long id) {
        return ResponseEntity.ok(postService.postById(id));
    }

    // update post by id rest api
    @PutMapping(value = "/{id}")
    public ResponseEntity<PostDto> updatePostById(@RequestBody PostDto postDto,@PathVariable(name = "id") long id) {
        return new ResponseEntity<>(postService.updatePostById(postDto, id), HttpStatus.OK);
    }

    // delete post rest api
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deletePostById(@PathVariable(name = "id") long id) {
        postService.deletePostById(id);
        return new ResponseEntity<>("Post with Id: " + id + " deleted successfully", HttpStatus.OK);
    }

    // delete all posts rest api
    @DeleteMapping
    public ResponseEntity<String> deleteAllPosts() {
        postService.deleteAllPosts();
        return new ResponseEntity<>("All posts deleted successfully", HttpStatus.OK);
    }
}
