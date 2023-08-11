package com.thejavalab.blogapp.service.impl;

import com.thejavalab.blogapp.entity.Comment;
import com.thejavalab.blogapp.entity.Post;
import com.thejavalab.blogapp.exception.ResourceNotFoundException;
import com.thejavalab.blogapp.payload.CommentDto;
import com.thejavalab.blogapp.repository.CommentRepository;
import com.thejavalab.blogapp.repository.PostRepository;
import com.thejavalab.blogapp.service.CommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private PostRepository postRepository;


    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        CommentDto returnValue = new CommentDto();
        Comment comment = new Comment();

        // Convert DTO into Entity
        BeanUtils.copyProperties(commentDto, comment);

        // Retrieve Post Entity by ID
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        // Set Post Entity to Comment Entity
        comment.setPost(post);

        // Save/Persist Comment Entity into DB
        Comment comment1 = commentRepository.save(comment);

        // Convert Entity to DTO
        BeanUtils.copyProperties(comment1, returnValue);

        return returnValue;
    }

    @Override
    public List<CommentDto> getCommentsByPostId(long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
        List<CommentDto> returnValue = new ArrayList<>();
        for(Comment comment : comments) {
            CommentDto dto = new CommentDto();
            BeanUtils.copyProperties(comment, dto);
            returnValue.add(dto);
        }
        return returnValue;
    }
}
