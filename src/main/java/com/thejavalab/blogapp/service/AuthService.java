package com.thejavalab.blogapp.service;

import com.thejavalab.blogapp.payload.LoginDto;
import com.thejavalab.blogapp.payload.RegisterDto;

public interface AuthService {
    String login(LoginDto loginDto);
    String register(RegisterDto registerDto);
}
