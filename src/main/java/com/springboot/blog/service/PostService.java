package com.springboot.blog.service;

import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;

import java.util.List;

public interface PostService {

    PostDto createPost(PostDto postDto);

    PostResponse allPosts(int pageNo, int pageSize);

    PostDto postById(long id);

    PostDto updatePostById(PostDto postDto, long id);

    void deletePostById(long id);

    void deleteAllPosts();
}
