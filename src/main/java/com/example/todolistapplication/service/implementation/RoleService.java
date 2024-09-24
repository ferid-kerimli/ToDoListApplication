package com.example.todolistapplication.service.implementation;

import com.example.todolistapplication.dto.roleDto.RoleGetDto;
import com.example.todolistapplication.entity.Role;
import com.example.todolistapplication.repository.RoleRepository;
import com.example.todolistapplication.response.ApiResponse;
import com.example.todolistapplication.service.abstraction.IRoleService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class RoleService implements IRoleService {
    private final Logger logger = LoggerFactory.getLogger(RoleService.class);
    private final ModelMapper modelMapper;
    private final RoleRepository roleRepository;

    public RoleService(ModelMapper modelMapper, RoleRepository roleRepository) {
        this.modelMapper = modelMapper;
        this.roleRepository = roleRepository;
    }

    @Async
    @Override
    public CompletableFuture<ApiResponse<List<RoleGetDto>>> getAllRoles() {
        return CompletableFuture.supplyAsync(() -> {
            logger.info("getAllRoles called");
            var response = new ApiResponse<List<RoleGetDto>>();

            try {
                var roles = roleRepository.findAll();

                if (roles.isEmpty()) {
                    response.Failure("No roles found", 400);
                    logger.warn("No roles found");
                    return response;
                }

                var roleDtoS = roles.stream()
                        .map(role -> modelMapper.map(role, RoleGetDto.class))
                        .toList();

                response.Success(roleDtoS, 200);
                logger.info("getAllRoles completed successfully");
            }
            catch (Exception e) {
                response.Failure(e.getMessage(), 500);
                logger.error("getAllRoles failed", e);
            }

            return response;
        });
    }

    @Async
    @Override
    public CompletableFuture<ApiResponse<RoleGetDto>> getRoleById(Long id) {
        return CompletableFuture.supplyAsync(() -> {
            logger.info("getRoleById called");
            var response = new ApiResponse<RoleGetDto>();

            try {
                Optional<Role> roleOpt = roleRepository.findById(id);

                if (roleOpt.isPresent()) {
                    RoleGetDto roleDto = modelMapper.map(roleOpt.get(), RoleGetDto.class);
                    response.Success(roleDto, 200);
                    logger.info("Role retrieved successfully with id: {}", id);

                } else {
                    response.Failure("Role not found", 404);
                    logger.warn("Role not found with id: {}", id);
                    return response;
                }
            }
            catch (Exception e) {
                response.Failure(e.getMessage(), 500);
                logger.error("getRoleById failed", e);
            }

            return response;
        });
    }

    @Async
    @Override
    public CompletableFuture<ApiResponse<Boolean>> createRole(String roleName) {
        return CompletableFuture.supplyAsync(() -> {
            logger.info("createRole called");
            var response = new ApiResponse<Boolean>();

            try {
                Role role = new Role();
                role.setName(roleName);
                roleRepository.save(role);

                response.Success(true, 201);
                logger.info("Role created successfully with name: {}", roleName);
            }
            catch (Exception e) {
                response.Failure(e.getMessage(), 500);
                logger.error("createRole failed", e);
            }

            return response;
        });
    }

    @Async
    @Override
    public CompletableFuture<ApiResponse<Boolean>> deleteRole(Long id) {
        return CompletableFuture.supplyAsync(() -> {
            logger.info("deleteRole called with id: {}", id);
            var response = new ApiResponse<Boolean>();

            try {
                Optional<Role> roleOpt = roleRepository.findById(id);

                if (roleOpt.isPresent()) {
                    roleRepository.deleteById(id);
                    response.Success(true, 200);
                    logger.info("Role deleted successfully: {}", id);

                } else {
                    response.Failure("Role not found", 404);
                    logger.warn("Role not found with this id: {}", id);
                    return response;
                }

            } catch (Exception e) {
                response.Failure(e.getMessage(), 500);
                logger.error("Error in deleteRole", e);
            }

            return response;
        });
    }
}
