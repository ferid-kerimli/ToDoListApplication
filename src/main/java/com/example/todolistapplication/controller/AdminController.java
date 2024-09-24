package com.example.todolistapplication.controller;

import com.example.todolistapplication.dto.roleDto.RoleGetDto;
import com.example.todolistapplication.dto.userDto.UserGetDto;
import com.example.todolistapplication.response.ApiResponse;
import com.example.todolistapplication.service.abstraction.IRoleService;
import com.example.todolistapplication.service.abstraction.IUserRoleService;
import com.example.todolistapplication.service.abstraction.IUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RequestMapping("/admin")
@RestController
public class AdminController {
    private final IUserService userService;
    private final IRoleService roleService;
    private final IUserRoleService userRoleService;

    public AdminController(IUserService userService, IRoleService roleService, IUserRoleService userRoleService) {
        this.userService = userService;
        this.roleService = roleService;
        this.userRoleService = userRoleService;
    }

    @GetMapping("/getAllUsers")
    public CompletableFuture<ResponseEntity<ApiResponse<List<UserGetDto>>>> getAllUsers() {
        return userService.getAllUsers()
                .thenApply(apiResponse -> ResponseEntity.status(apiResponse.getStatusCode()).body(apiResponse));
    }

    @GetMapping("/getUserById/{id}")
    public CompletableFuture<ResponseEntity<ApiResponse<UserGetDto>>> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .thenApply(apiResponse -> ResponseEntity.status(apiResponse.getStatusCode()).body(apiResponse));
    }

    @GetMapping("/getUserByEmail/{email}")
    public CompletableFuture<ResponseEntity<ApiResponse<UserGetDto>>> getUserByEmail(@PathVariable String email) {
        return userService.getUserByEmail(email)
                .thenApply(apiResponse -> ResponseEntity.status(apiResponse.getStatusCode()).body(apiResponse));
    }

    @GetMapping("/getUserByUsername/{username}")
    public CompletableFuture<ResponseEntity<ApiResponse<UserGetDto>>> getUserByUsername(@PathVariable String username) {
        return userService.getUserByUsername(username)
                .thenApply(apiResponse -> ResponseEntity.status(apiResponse.getStatusCode()).body(apiResponse));
    }

    @DeleteMapping("/deleteUser/{id}")
    public CompletableFuture<ResponseEntity<ApiResponse<Boolean>>> deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id)
                .thenApply(apiResponse -> ResponseEntity.status(apiResponse.getStatusCode()).body(apiResponse));
    }

    @GetMapping("/getAllRoles")
    public CompletableFuture<ResponseEntity<ApiResponse<List<RoleGetDto>>>> getAllRoles() {
        return roleService.getAllRoles()
                .thenApply(apiResponse -> ResponseEntity.status(apiResponse.getStatusCode()).body(apiResponse));
    }

    @GetMapping("/getRoleById/{id}")
    public CompletableFuture<ResponseEntity<ApiResponse<RoleGetDto>>> getRoleById(@PathVariable Long id) {
        return roleService.getRoleById(id)
                .thenApply(apiResponse -> ResponseEntity.status(apiResponse.getStatusCode()).body(apiResponse));
    }

    @PostMapping("/createRole/{roleName}")
    public CompletableFuture<ResponseEntity<ApiResponse<Boolean>>> createRole(@PathVariable String roleName) {
        return roleService.createRole(roleName)
                .thenApply(apiResponse -> ResponseEntity.status(apiResponse.getStatusCode()).body(apiResponse));
    }

    @DeleteMapping("/deleteRole/{id}")
    public CompletableFuture<ResponseEntity<ApiResponse<Boolean>>> deleteRole(@PathVariable Long id) {
        return roleService.deleteRole(id)
                .thenApply(apiResponse -> ResponseEntity.status(apiResponse.getStatusCode()).body(apiResponse));
    }

    @PostMapping("/assignRoleToUser/{userId}/{roleName}")
    public CompletableFuture<ResponseEntity<ApiResponse<Boolean>>> assignRoleToUser(@PathVariable Long userId, @PathVariable String roleName) {
        return userRoleService.assignRoleToUser(userId, roleName)
                .thenApply(apiResponse -> ResponseEntity.status(apiResponse.getStatusCode()).body(apiResponse));
    }
}
