package com.thejavalab.blogapp.service.impl;

import com.thejavalab.blogapp.entity.Post;
import com.thejavalab.blogapp.exception.ResourceNotFoundException;
import com.thejavalab.blogapp.payload.PostDto;
import com.thejavalab.blogapp.payload.PostResponse;
import com.thejavalab.blogapp.repository.PostRepository;
import com.thejavalab.blogapp.service.PostService;
import org.springframework.beans.BeanUtils;
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

    @Override
    public PostDto createPost(PostDto postDto) {

        PostDto returnValue = new PostDto();
        Post post = new Post();

        // Convert DTO to Entity
        BeanUtils.copyProperties(postDto, post);

        // Save/Persist Entity in to DB
        Post post1 = postRepository.save(post);

        // Convert Entity to DTO
        BeanUtils.copyProperties(post1, returnValue);
        return returnValue;
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
            PostDto postDto = new PostDto();
            BeanUtils.copyProperties(post, postDto);
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
        PostDto returnValue = new PostDto();
        BeanUtils.copyProperties(post, returnValue);
        return returnValue;
    }

    @Override
    public PostDto updatePost(PostDto postDto, Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        Long id1 = post.getId();
        BeanUtils.copyProperties(postDto, post);
        post.setId(id1);
        Post response = postRepository.save(post);
        PostDto returnValue = new PostDto();
        BeanUtils.copyProperties(response, returnValue);
        return returnValue;
    }

    @Override
    public void deletePost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        postRepository.delete(post);
    }


}
