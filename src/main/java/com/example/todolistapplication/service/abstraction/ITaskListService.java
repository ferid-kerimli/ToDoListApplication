package com.example.todolistapplication.service.abstraction;

import com.example.todolistapplication.dto.taskListDto.TaskListCreateDto;
import com.example.todolistapplication.dto.taskListDto.TaskListGetDto;
import com.example.todolistapplication.dto.taskListDto.TaskListUpdateDto;
import com.example.todolistapplication.response.ApiResponse;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface ITaskListService {
    CompletableFuture<ApiResponse<List<TaskListGetDto>>> getAllTaskLists();
    CompletableFuture<ApiResponse<TaskListGetDto>> getTaskListById(Long id);
    CompletableFuture<ApiResponse<TaskListCreateDto>> createTaskList(TaskListCreateDto taskListCreateDto);
    CompletableFuture<ApiResponse<TaskListUpdateDto>> updateTaskList(TaskListUpdateDto taskListUpdateDto);
    CompletableFuture<ApiResponse<Boolean>> deleteTaskList(Long id);
}
