package com.thejavalab.blogapp.service;

import com.thejavalab.blogapp.payload.PostDto;
import com.thejavalab.blogapp.payload.PostResponse;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);
    PostResponse getAllPosts(int pageNo, int PageSize, String sortBy, String sortDir);

    PostDto getPostById(Long id);

    PostDto updatePost(PostDto postDto, Long id);

    void deletePost(Long id);

    List<PostDto> getPostByCategory(Long categoryId);
}
