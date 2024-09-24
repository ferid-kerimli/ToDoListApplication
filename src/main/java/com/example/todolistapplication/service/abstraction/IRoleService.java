package com.example.todolistapplication.service.abstraction;

import com.example.todolistapplication.dto.roleDto.RoleGetDto;
import com.example.todolistapplication.response.ApiResponse;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface IRoleService {
    CompletableFuture<ApiResponse<List<RoleGetDto>>> getAllRoles();
    CompletableFuture<ApiResponse<RoleGetDto>> getRoleById(Long id);
    CompletableFuture<ApiResponse<Boolean>> createRole(String roleName);
    CompletableFuture<ApiResponse<Boolean>> deleteRole(Long id);
}
