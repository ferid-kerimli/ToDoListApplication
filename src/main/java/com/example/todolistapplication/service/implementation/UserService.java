package com.example.todolistapplication.service.implementation;

import com.example.todolistapplication.dto.userDto.UserGetDto;
import com.example.todolistapplication.repository.UserRepository;
import com.example.todolistapplication.response.ApiResponse;
import com.example.todolistapplication.service.abstraction.IUserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class UserService implements IUserService {
    private final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    public UserService(ModelMapper modelMapper, UserRepository userRepository) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }

    @Async
    @Override
    public CompletableFuture<ApiResponse<List<UserGetDto>>> getAllUsers() {
        return CompletableFuture.supplyAsync(() -> {
            logger.info("getAllUsers called");
            var response = new ApiResponse<List<UserGetDto>>();

            try {
                var users = userRepository.findAll();

                if (users.isEmpty()) {
                    response.Failure("There is no existing user", 400);
                    logger.warn("There is no existing user");
                    return response;
                }

                var mappedUsers = users.stream()
                                .map(user -> modelMapper.map(user, UserGetDto.class))
                                .toList();

                response.Success(mappedUsers, 200);
                logger.info("getAllUsers completed successfully");
            }
            catch (Exception e) {
                response.Failure(e.getMessage(), 500);
                logger.error("getAllUsers failed", e);
            }

            return response;
        });
    }

    @Async
    @Override
    public CompletableFuture<ApiResponse<UserGetDto>> getUserById(Long id) {
        return CompletableFuture.supplyAsync(() -> {
            logger.info("getUserById called");
            var response = new ApiResponse<UserGetDto>();

            try {
                var user = userRepository.findById(id);

                if (user.isEmpty()) {
                    response.Failure("User not found", 400);
                    logger.warn("User not found with id {}", id);
                    return response;
                }

                var mappedUser = modelMapper.map(user, UserGetDto.class);

                response.Success(mappedUser, 200);
                logger.info("getUserById completed successfully");
            }
            catch (Exception e) {
                response.Failure(e.getMessage(), 500);
                logger.error("getUserById failed", e);
            }

            return response;
        });
    }

    @Async
    @Override
    public CompletableFuture<ApiResponse<UserGetDto>> getUserByEmail(String email) {
        return CompletableFuture.supplyAsync(() -> {
            logger.info("getUserByEmail called");
            var response = new ApiResponse<UserGetDto>();

            try {
                var user = userRepository.findByEmail(email);

                if (user.isEmpty()) {
                    response.Failure("User not found", 400);
                    logger.warn("User not found with email {}", email);
                    return response;
                }

                var mappedUser = modelMapper.map(user, UserGetDto.class);
                response.Success(mappedUser, 200);
                logger.info("getUserByEmail completed successfully");
            }
            catch (Exception e) {
                response.Failure(e.getMessage(), 500);
                logger.error("getUserByEmail failed", e);
            }

            return response;
        });
    }

    @Async
    @Override
    public CompletableFuture<ApiResponse<UserGetDto>> getUserByUsername(String username) {
        return CompletableFuture.supplyAsync(() -> {
            logger.info("getUserByUsername called");
            var response = new ApiResponse<UserGetDto>();

            try {
                var user = userRepository.findByUsername(username);

                if (user.isEmpty()) {
                    response.Failure("User not found", 400);
                    logger.warn("User not found with name {}", username);
                    return response;
                }

                var mappedUser = modelMapper.map(user, UserGetDto.class);
                response.Success(mappedUser, 200);
                logger.info("getUserByUsername completed successfully");
            }
            catch (Exception e) {
                response.Failure(e.getMessage(), 500);
                logger.error("getUserByUsername failed", e);
            }

            return response;
        });
    }

    @Async
    @Override
    public CompletableFuture<ApiResponse<Boolean>> deleteUser(Long id) {
        return CompletableFuture.supplyAsync(() -> {
            logger.info("deleteUserById called");
            var response = new ApiResponse<Boolean>();

            try {
                var user = userRepository.findById(id);

                if (user.isPresent()) {
                    userRepository.deleteById(id);
                    response.Success(true, 200);
                    logger.info("User deleted successfully: {}", user.get());
                } else {
                    response.Failure("User not found", 404);
                    logger.warn("User with this id not found" + id);
                }
            }
            catch (Exception e) {
                response.Failure(e.getMessage(), 500);
                logger.error("deleteUserById failed", e);
            }

            return response;
        });
    }
}
