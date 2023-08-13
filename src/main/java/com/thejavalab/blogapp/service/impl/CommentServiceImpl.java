package com.thejavalab.blogapp.service.impl;

import com.thejavalab.blogapp.entity.Comment;
import com.thejavalab.blogapp.entity.Post;
import com.thejavalab.blogapp.exception.BlogAPIException;
import com.thejavalab.blogapp.exception.ResourceNotFoundException;
import com.thejavalab.blogapp.payload.CommentDto;
import com.thejavalab.blogapp.payload.PostDto;
import com.thejavalab.blogapp.repository.CommentRepository;
import com.thejavalab.blogapp.repository.PostRepository;
import com.thejavalab.blogapp.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private ModelMapper modelMapper;


    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        // Convert DTO into Entity
        Comment comment = mapToEntity(commentDto);
        // Retrieve Post Entity by ID
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        // Set Post Entity to Comment Entity
        comment.setPost(post);

        // Save/Persist Comment Entity into DB
        Comment response = commentRepository.save(comment);

        return mapToDto(response);
    }

    @Override
    public List<CommentDto> getCommentsByPostId(long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
        List<CommentDto> returnValue = new ArrayList<>();
        for(Comment comment : comments) {
            CommentDto dto = mapToDto(comment);
            returnValue.add(dto);
        }
        return returnValue;
    }

    @Override
    public CommentDto getCommentById(Long postId, Long commentId) {
        // Retrieve Post Entity by Post ID
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        // Retrieve Comment by Comment ID
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

        if(!comment.getPost().getId().equals(post.getId())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment doest not belong to post");
        }
        return mapToDto(comment);
    }

    @Override
    public CommentDto updateCommentById(Long postId, Long commentId, CommentDto commentDto) {
        // Retrieve Post Entity by Post ID
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        // Retrieve Comment by Comment ID
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

        if(!comment.getPost().getId().equals(post.getId())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment doest not belong to post");
        }

        comment.setName(commentDto.getName());
        comment.setBody(commentDto.getBody());
        comment.setEmail(commentDto.getEmail());

        // Save Comment into DB
        Comment response = commentRepository.save(comment);

        return mapToDto(response);
    }

    @Override
    public void deleteCommentById(Long postId, Long commentId) {
        // Retrieve Post Entity by Post ID
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        // Retrieve Comment by Comment ID
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

        if(!comment.getPost().getId().equals(post.getId())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment doest not belong to post");
        }

        commentRepository.delete(comment);

    }
    private CommentDto mapToDto(Comment comment) {
        return modelMapper.map(comment, CommentDto.class);
    }

    private Comment mapToEntity(CommentDto commentDto) {
        return modelMapper.map(commentDto, Comment.class);
    }
}
