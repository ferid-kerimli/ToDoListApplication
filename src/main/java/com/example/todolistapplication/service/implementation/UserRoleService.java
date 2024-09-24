package com.example.todolistapplication.service.implementation;

import com.example.todolistapplication.entity.Role;
import com.example.todolistapplication.entity.User;
import com.example.todolistapplication.repository.RoleRepository;
import com.example.todolistapplication.repository.UserRepository;
import com.example.todolistapplication.response.ApiResponse;
import com.example.todolistapplication.service.abstraction.IUserRoleService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class UserRoleService implements IUserRoleService {
    private final Logger logger = LoggerFactory.getLogger(UserRoleService.class);
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserRoleService(ModelMapper modelMapper, UserRepository userRepository, RoleRepository roleRepository) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Async
    @Override
    public CompletableFuture<ApiResponse<Boolean>> assignRoleToUser(Long userId, String roleName) {
        return CompletableFuture.supplyAsync(() -> {
            logger.info("assignRoleToUser called");
            var response = new ApiResponse<Boolean>();

            try {
                Optional<User> userOpt = userRepository.findById(userId);
                Optional<Role> roleOpt = roleRepository.findByName(roleName);

                if (userOpt.isPresent() && roleOpt.isPresent()) {
                    User user = userOpt.get();
                    Role role = roleOpt.get();
                    user.getRoles().add(role);
                    userRepository.save(user);

                    response.Success(true,200);
                    logger.info("assignRoleToUser completed successfully");
                } else {
                    String message = userOpt.isEmpty() ? "User not found" : "Role not found";
                    response.Failure(message, 404);
                    logger.warn(message);
                }
            }
            catch (Exception e) {
                response.Failure(e.getMessage(), 500);
                logger.error("assignRoleToUser failed", e);
            }

            return response;
        });
    }
}
