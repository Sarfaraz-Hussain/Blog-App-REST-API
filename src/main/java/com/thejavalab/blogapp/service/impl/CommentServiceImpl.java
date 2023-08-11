package com.thejavalab.blogapp.service.impl;

import com.thejavalab.blogapp.payload.CommentDto;
import com.thejavalab.blogapp.repository.CommentRepository;
import com.thejavalab.blogapp.service.CommentService;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;

    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {

    }
}
