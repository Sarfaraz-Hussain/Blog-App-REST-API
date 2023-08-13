package com.thejavalab.blogapp.service.impl;

import com.thejavalab.blogapp.entity.Post;
import com.thejavalab.blogapp.exception.ResourceNotFoundException;
import com.thejavalab.blogapp.payload.PostDto;
import com.thejavalab.blogapp.payload.PostResponse;
import com.thejavalab.blogapp.repository.PostRepository;
import com.thejavalab.blogapp.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PostDto createPost(PostDto postDto) {

        // Convert DTO to Entity
        Post post = mapToEntity(postDto);

        // Save/Persist Entity in to DB
        Post post1 = postRepository.save(post);

        return mapToDto(post1);
    }

    @Override
    public PostResponse getAllPosts(int pageNo, int PageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        // Create Pageable Instance
        Pageable pageable = PageRequest.of(pageNo, PageSize, sort);

        Page<Post> posts = postRepository.findAll(pageable);

        // Get Content from Page Object/Instance
        List<Post> listOfPosts = posts.getContent();

        List<PostDto> content = new ArrayList<>();
        for(Post post : listOfPosts) {
            PostDto postDto = mapToDto(post);
            content.add(postDto);
        }
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(posts.isLast());
        return postResponse;
    }

    @Override
    public PostDto getPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        return mapToDto(post);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        Post response = postRepository.save(post);
        return mapToDto(response);
    }

    @Override
    public void deletePost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        postRepository.delete(post);
    }

    private PostDto mapToDto(Post post) {
        return modelMapper.map(post, PostDto.class);
    }

    private Post mapToEntity(PostDto postDto) {
        return modelMapper.map(postDto, Post.class);
    }
}
