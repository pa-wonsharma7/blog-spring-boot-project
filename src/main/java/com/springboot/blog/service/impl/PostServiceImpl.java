package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public PostDto createPost(PostDto postDto) {

        // What we want here is to save the data that has come in the database and again send back the data.
        // For that we need to make the DTO to entity so that it can be saved in database.
        // convert DTO to entity
        Post post = mapToEntity(postDto);

        // save method of postRepository saves the data in database and returns the object of the same data
        Post newPost = postRepository.save(post);

        // convert entity newPost to DTO using mapToDTO method and return
        return mapToDTO(newPost);

    }

    @Override
    public List<PostDto> allPosts() {
        List<Post> posts = postRepository.findAll();
        return posts.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    /*
    This is a service. It should return what it needs to return.
    I first thought that we need to return ResponseEntity from here, but that's not the case.
    ResponseEntity is to be a part of controller because that is being exposed to client.
     */

    @Override
    public PostDto postById(long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        return mapToDTO(post);
    }

    @Override
    public PostDto updatePostById(PostDto postDto, long id) {

        // get post by id
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());

        // update the post entity by saving
        Post updatedPost = postRepository.save(post);

        // return the postDTO after converting entity to DTO
        return mapToDTO(updatedPost);
    }

    @Override
    public void deletePostById(long id) {

        // get post entity by id
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        postRepository.delete(post);
    }

    @Override
    public void deleteAllPosts() {

        // delete all posts
        postRepository.deleteAll();
    }

    // converted entity to post
    private PostDto mapToDTO(Post post) {

        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setDescription(post.getDescription());
        postDto.setContent(post.getContent());
        return postDto;

    }

    // converted DTO to entity
    private Post mapToEntity(PostDto postDto) {

        Post post = new Post(); // Create a new entity Post and set all the fields
        post.setId(postDto.getId());  // Where do we get the data to set all the fields? From the PostDto object
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        return post;

    }
}
