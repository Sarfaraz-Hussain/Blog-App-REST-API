package com.thejavalab.blogapp.service;

import com.thejavalab.blogapp.payload.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(long postId, CommentDto commentDto);
    List<CommentDto> getCommentsByPostId(long postId);
}
