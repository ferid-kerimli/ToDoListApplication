package com.example.todolistapplication.response;

import lombok.Data;

@Data
public class JwtTokenResponse {
    private String token;
    private long expiresAt;
}