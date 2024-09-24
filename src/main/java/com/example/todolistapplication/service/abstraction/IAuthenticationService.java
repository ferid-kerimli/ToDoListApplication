package com.example.todolistapplication.service.abstraction;

import com.example.todolistapplication.dto.authenticationDto.LoginDto;
import com.example.todolistapplication.dto.authenticationDto.RegisterDto;
import com.example.todolistapplication.response.ApiResponse;
import com.example.todolistapplication.response.JwtTokenResponse;

import java.util.concurrent.CompletableFuture;

public interface IAuthenticationService {
    CompletableFuture<ApiResponse<RegisterDto>> signup(RegisterDto registerDto);
    CompletableFuture<ApiResponse<JwtTokenResponse>> login(LoginDto loginDto);
    CompletableFuture<ApiResponse<String>> logout(String token);
}
