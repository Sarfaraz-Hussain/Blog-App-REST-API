package com.thejavalab.blogapp.service;

import com.thejavalab.blogapp.payload.CommentDto;

public interface CommentService {
    CommentDto createComment(long postId, CommentDto commentDto);
}
