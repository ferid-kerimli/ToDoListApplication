package com.example.todolistapplication.service.implementation;

import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class TokenBlacklistService {
    private final Set<String> blackListedTokens = new HashSet<>();

    public void addBlackListedToken(String token) {
        blackListedTokens.add(token);
    }

    public boolean isTokenBlacklisted(String token) {
        return blackListedTokens.contains(token);
    }
}
