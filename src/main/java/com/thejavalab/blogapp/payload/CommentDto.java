package com.thejavalab.blogapp.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CommentDto {
    private Long id;

    // Name should not be null or empty
    @NotEmpty(message = "Name should not be null or empty")
    private String name;

    // Email should not be null or empty
    // Email Field Validation
    @NotEmpty(message = "Email should not be null or empty")
    @Email
    private String email;

    // Comment Body should not be null or empty
    // Comment Body must be minimum of 10 characters
    @NotEmpty
    @Size(min = 10, message = "Comment Body must be minimum of 10 characters")
    private String body;
}
