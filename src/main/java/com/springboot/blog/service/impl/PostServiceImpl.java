package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    /*
    When the class has only one constructor for another service or class then @Autowired annotation is
    not required and automatically added by Spring-boot.
     */
    private final PostRepository postRepository;
    @Autowired
    private ModelMapper modelMapper;

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
    public PostResponse allPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
        // if sortDir = asc then ascending or else descending
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();

        // create Pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        // create a page
        Page<Post> page = postRepository.findAll(pageable);

        // get the contents of the page in a list
        List<Post> posts = page.getContent();

        // convert to dto and return
        List<PostDto> content =  posts.stream().map(this::mapToDTO).collect(Collectors.toList());
        return makePostResponse(content, page);
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
        return modelMapper.map(post, PostDto.class);
    }

    // converted DTO to entity
    private Post mapToEntity(PostDto postDto) {
        return modelMapper.map(postDto, Post.class);
    }

    // converts PostDto to PostResponse
    private PostResponse makePostResponse(List<PostDto> content, Page<Post> page) {
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(page.getNumber());
        postResponse.setPageSize(page.getSize());
        postResponse.setTotalPages(page.getTotalPages());
        postResponse.setTotalElements(page.getTotalElements());
        postResponse.setLast(page.isLast());
        return postResponse;
    }
}
