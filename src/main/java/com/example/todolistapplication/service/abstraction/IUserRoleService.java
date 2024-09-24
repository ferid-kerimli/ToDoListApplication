package com.example.todolistapplication.service.abstraction;

import com.example.todolistapplication.response.ApiResponse;

import java.util.concurrent.CompletableFuture;

public interface IUserRoleService {
    CompletableFuture<ApiResponse<Boolean>> assignRoleToUser(Long userId, String roleName);
}
