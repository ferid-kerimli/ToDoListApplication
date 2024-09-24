package com.example.todolistapplication.service.implementation;

import com.example.todolistapplication.dto.authenticationDto.LoginDto;
import com.example.todolistapplication.dto.authenticationDto.RegisterDto;
import com.example.todolistapplication.entity.User;
import com.example.todolistapplication.repository.UserRepository;
import com.example.todolistapplication.response.ApiResponse;
import com.example.todolistapplication.response.JwtTokenResponse;
import com.example.todolistapplication.service.abstraction.IAuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class AuthenticationService implements IAuthenticationService {
    private final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final TokenBlacklistService tokenBlacklistService;

    public AuthenticationService(PasswordEncoder passwordEncoder, UserRepository userRepository, JwtService jwtService, TokenBlacklistService tokenBlacklistService) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.tokenBlacklistService = tokenBlacklistService;
    }

    @Async
    @Override
    public CompletableFuture<ApiResponse<RegisterDto>> signup(RegisterDto registerDto) {
        return CompletableFuture.supplyAsync(() -> {
            var response = new ApiResponse<RegisterDto>();

            try {
                User user = new User();
                user.setEmail(registerDto.getEmail());
                user.setUsername(registerDto.getUsername());
                user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

                // Save user
                User savedUser = userRepository.save(user);

                RegisterDto savedUserDto = new RegisterDto();
                savedUserDto.setUsername(savedUser.getUsername());
                savedUserDto.setEmail(savedUser.getEmail());

                response.Success(savedUserDto, 201);
                logger.info("User successfully registered: {}", savedUser.getUsername());
            }
            catch (Exception e) {
                response.Failure(e.getMessage(), 500);
                logger.error("Exception occurred while registering user", e);
            }

            return response;
        });
    }

    @Async
    @Override
    public CompletableFuture<ApiResponse<JwtTokenResponse>> login(LoginDto loginDto) {
        return CompletableFuture.supplyAsync(() -> {
            var response = new ApiResponse<JwtTokenResponse>();

            try {
                Optional<User> userOptional = userRepository.findByEmail(loginDto.getEmail());

                if (userOptional.isEmpty()) {
                    response.Failure("User not found", 404);
                    logger.warn("User not found: {}", loginDto.getEmail());
                    return response;
                }

                User user = userOptional.get();

                if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
                    response.Failure("Invalid credentials", 401);
                    logger.warn("Invalid credentials: {}", loginDto.getEmail());
                    return response;
                }

                String token = jwtService.generateToken(user);
                long expiresAt = jwtService.getExpirationTime();

                JwtTokenResponse jwtTokenResponse = new JwtTokenResponse();
                jwtTokenResponse.setToken(token);
                jwtTokenResponse.setExpiresAt(expiresAt);

                response.Success(jwtTokenResponse, 200);
                logger.info("User successfully logged in: {}", user.getUsername());
            }
            catch (Exception e) {
                response.Failure(e.getMessage(), 500);
                logger.error("Exception occurred while login", e);
            }

            return response;
        });
    }

    @Async
    @Override
    public CompletableFuture<ApiResponse<String>> logout(String token) {
        return CompletableFuture.supplyAsync(() -> {
            var response = new ApiResponse<String>();

            try {
                // Invalidate the token
                tokenBlacklistService.addBlackListedToken(token);

                response.Success("User successfully logged out", 200);
                logger.info("Token invalidated and user logged out: {}", token);
            } catch (Exception e) {
                response.Failure("Error occurred during logout", 500);
                logger.error("Exception occurred while logging out", e);
            }

            return response;
        });
    }
}
