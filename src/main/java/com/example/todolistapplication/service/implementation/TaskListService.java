package com.example.todolistapplication.service.implementation;

import com.example.todolistapplication.dto.taskListDto.TaskListCreateDto;
import com.example.todolistapplication.dto.taskListDto.TaskListGetDto;
import com.example.todolistapplication.dto.taskListDto.TaskListUpdateDto;
import com.example.todolistapplication.entity.TaskList;
import com.example.todolistapplication.entity.User;
import com.example.todolistapplication.repository.TaskListRepository;
import com.example.todolistapplication.repository.UserRepository;
import com.example.todolistapplication.response.ApiResponse;
import com.example.todolistapplication.service.abstraction.ITaskListService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class TaskListService implements ITaskListService {
    private final Logger logger = LoggerFactory.getLogger(TaskListService.class);
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final TaskListRepository taskListRepository;

    public TaskListService(ModelMapper modelMapper, UserRepository userRepository, TaskListRepository taskListRepository) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.taskListRepository = taskListRepository;
    }

    @Async
    @Override
    public CompletableFuture<ApiResponse<List<TaskListGetDto>>> getAllTaskLists() {
        return CompletableFuture.supplyAsync(() -> {
            logger.info("getAllTaskLists called");
            var response = new ApiResponse<List<TaskListGetDto>>();

            try {
                String username = SecurityContextHolder.getContext().getAuthentication().getName();
                User user = userRepository.findByUsername(username);

                if (user == null) {
                    response.Failure("User not logged in", 401);
                    logger.warn("User not logged in");
                    return response;
                }

                List<TaskList> taskLists = taskListRepository.findByUser(user);

                List<TaskListGetDto> taskListGetDtoS = taskLists.stream()
                        .map(taskList -> modelMapper.map(taskList, TaskListGetDto.class))
                        .toList();

                response.Success(taskListGetDtoS, 200);
                logger.info("getAllTaskLists completed successfully for: {}", user);
            }
            catch (Exception e) {
                response.Failure(e.getMessage(), 500);
                logger.error("Error while getting all task lists", e);
            }

            return response;
        });
    }

    @Async
    @Override
    public CompletableFuture<ApiResponse<TaskListGetDto>> getTaskListById(Long id) {
        return CompletableFuture.supplyAsync(() -> {
            logger.info("getTaskListById called");
            var response = new ApiResponse<TaskListGetDto>();

            try {
                String username = SecurityContextHolder.getContext().getAuthentication().getName();
                User user = userRepository.findByUsername(username);

                if (user == null) {
                    response.Failure("User not logged in", 401);
                    logger.warn("User not logged in");
                    return response;
                }

                TaskList taskList = taskListRepository.findByIdAndUser(id, user);

                if (taskList == null) {
                    response.Failure("Task list not found", 404);
                    logger.warn("Task list not found with this id: {}", id);
                    return response;
                }

                TaskListGetDto taskListGetDto = modelMapper.map(taskList, TaskListGetDto.class);

                response.Success(taskListGetDto, 200);
                logger.info("getTaskListById completed successfully for: {}", user);
            }
            catch (Exception e) {
                response.Failure(e.getMessage(), 500);
                logger.error("Error while getting task list", e);
            }

            return response;
        });
    }

    @Async
    @Override
    public CompletableFuture<ApiResponse<TaskListCreateDto>> createTaskList(TaskListCreateDto taskListCreateDto) {
        return CompletableFuture.supplyAsync(() -> {
            logger.info("createTaskList called");
            var response = new ApiResponse<TaskListCreateDto>();

            try {
                String username = SecurityContextHolder.getContext().getAuthentication().getName();
                User user = userRepository.findByUsername(username);

                if (user == null) {
                    response.Failure("User not logged in", 401);
                    logger.warn("User not logged in");
                    return response;
                }

                TaskList taskList = modelMapper.map(taskListCreateDto, TaskList.class);
                taskList.setUser(user);
                taskList.setCreatedAt(LocalDateTime.now());

                taskList = taskListRepository.save(taskList);

                var createdTaskListDto = modelMapper.map(taskList, TaskListCreateDto.class);

                response.Success(createdTaskListDto, 201);
                logger.info("createTaskList completed successfully for user: {}", user);
            }
            catch (Exception e) {
                response.Failure(e.getMessage(), 500);
                logger.error("Error creating task list", e);
            }

            return response;
        });
    }

    @Async
    @Override
    public CompletableFuture<ApiResponse<TaskListUpdateDto>> updateTaskList(TaskListUpdateDto taskListUpdateDto) {
        return CompletableFuture.supplyAsync(() -> {
            logger.info("updateTaskList called");
            var response = new ApiResponse<TaskListUpdateDto>();

            try {
                String username = SecurityContextHolder.getContext().getAuthentication().getName();
                User user = userRepository.findByUsername(username);

                if (user == null) {
                    response.Failure("User not logged in", 401);
                    logger.warn("User not logged in");
                    return response;
                }

                TaskList taskList = taskListRepository.findByIdAndUser(taskListUpdateDto.getId(), user);

                if (taskList == null) {
                    response.Failure("Task list not found", 404);
                    logger.warn("Task list not found with this id: {}", taskListUpdateDto.getId());
                    return response;
                }

                taskList.setName(taskListUpdateDto.getName());
                taskList = taskListRepository.save(taskList);

                var updatedTaskListDto = modelMapper.map(taskList, TaskListUpdateDto.class);

                response.Success(updatedTaskListDto, 200);
                logger.info("updateTaskList completed successfully for user: {}", user);
            }
            catch (Exception e) {
                response.Failure(e.getMessage(), 500);
                logger.error("Error happened while updating task list", e);
            }

            return response;
        });
    }

    @Async
    @Override
    public CompletableFuture<ApiResponse<Boolean>> deleteTaskList(Long id) {
        return CompletableFuture.supplyAsync(() -> {
            logger.info("deleteTaskList called");
            var response = new ApiResponse<Boolean>();

            try {
                String username = SecurityContextHolder.getContext().getAuthentication().getName();
                User user = userRepository.findByUsername(username);

                if (user == null) {
                    response.Failure("User not logged in", 401);
                    logger.warn("User not logged in");
                    return response;
                }

                TaskList taskList = taskListRepository.findByIdAndUser(id, user);

                if (taskList == null) {
                    response.Failure("Task list not found", 404);
                    logger.warn("Task list not found with this id: {}", id);
                    return response;
                }

                taskListRepository.delete(taskList);

                response.Success(true, 200);
                logger.info("deleteTaskList completed successfully for: {}", user);
            }
            catch (Exception e) {
                response.Failure(e.getMessage(), 500);
                logger.error("Error happened while deleting task list", e);
            }

            return response;
        });
    }
}
