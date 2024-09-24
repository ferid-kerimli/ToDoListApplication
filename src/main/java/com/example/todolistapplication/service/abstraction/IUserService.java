package com.example.todolistapplication.service.abstraction;

import com.example.todolistapplication.dto.userDto.UserGetDto;
import com.example.todolistapplication.response.ApiResponse;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface IUserService {
    CompletableFuture<ApiResponse<List<UserGetDto>>> getAllUsers();
    CompletableFuture<ApiResponse<UserGetDto>> getUserById(Long id);
    CompletableFuture<ApiResponse<UserGetDto>> getUserByEmail(String email);
    CompletableFuture<ApiResponse<UserGetDto>> getUserByUsername(String username);
    CompletableFuture<ApiResponse<Boolean>> deleteUser(Long id);
}
