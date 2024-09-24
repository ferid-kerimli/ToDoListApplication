package com.example.todolistapplication.controller;

import com.example.todolistapplication.dto.authenticationDto.LoginDto;
import com.example.todolistapplication.dto.authenticationDto.RegisterDto;
import com.example.todolistapplication.response.ApiResponse;
import com.example.todolistapplication.response.JwtTokenResponse;
import com.example.todolistapplication.service.abstraction.IAuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {
    private final IAuthenticationService authenticationService;

    public AuthenticationController(IAuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public CompletableFuture<ResponseEntity<ApiResponse<RegisterDto>>> signup(@RequestBody RegisterDto registerDto) {
        return authenticationService.signup(registerDto)
                .thenApply(apiResponse -> ResponseEntity.status(apiResponse.getStatusCode()).body(apiResponse));
    }

    @PostMapping("/login")
    public CompletableFuture<ResponseEntity<ApiResponse<JwtTokenResponse>>> login(@RequestBody LoginDto loginDto) {
        return authenticationService.login(loginDto)
                .thenApply(apiResponse -> ResponseEntity.status(apiResponse.getStatusCode()).body(apiResponse));
    }

    @PostMapping("/logout")
    public CompletableFuture<ResponseEntity<ApiResponse<String>>> logout(String token) {
        return authenticationService.logout(token)
                .thenApply(apiResponse -> ResponseEntity.status(apiResponse.getStatusCode()).body(apiResponse));
    }
}