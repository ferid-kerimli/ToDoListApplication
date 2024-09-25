package com.example.todolistapplication.controller;

import com.example.todolistapplication.dto.taskListDto.TaskListCreateDto;
import com.example.todolistapplication.dto.taskListDto.TaskListGetDto;
import com.example.todolistapplication.dto.taskListDto.TaskListUpdateDto;
import com.example.todolistapplication.response.ApiResponse;
import com.example.todolistapplication.service.abstraction.ITaskListService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RequestMapping("/taskList")
@RestController
public class TaskListController {
    private final ITaskListService taskListService;

    public TaskListController(ITaskListService taskListService) {
        this.taskListService = taskListService;
    }

    @GetMapping("/getAllTaskLists")
    public CompletableFuture<ResponseEntity<ApiResponse<List<TaskListGetDto>>>> getAllTaskLists() {
        return taskListService.getAllTaskLists()
                .thenApply(apiResponse -> ResponseEntity.status(apiResponse.getStatusCode()).body(apiResponse));
    }

    @GetMapping("/getTaskListById/{id}")
    public CompletableFuture<ResponseEntity<ApiResponse<TaskListGetDto>>> getTaskListById(@PathVariable Long id) {
        return taskListService.getTaskListById(id)
                .thenApply(apiResponse -> ResponseEntity.status(apiResponse.getStatusCode()).body(apiResponse));
    }

    @PostMapping("/createTaskList")
    public CompletableFuture<ResponseEntity<ApiResponse<TaskListCreateDto>>> createTaskList(@RequestBody TaskListCreateDto taskListCreateDto) {
        return taskListService.createTaskList(taskListCreateDto)
                .thenApply(apiResponse -> ResponseEntity.status(apiResponse.getStatusCode()).body(apiResponse));
    }

    @PutMapping("/updateTaskList")
    public CompletableFuture<ResponseEntity<ApiResponse<TaskListUpdateDto>>> updateTaskList(@RequestBody TaskListUpdateDto taskListUpdateDto) {
        return taskListService.updateTaskList(taskListUpdateDto)
                .thenApply(apiResponse -> ResponseEntity.status(apiResponse.getStatusCode()).body(apiResponse));
    }

    @DeleteMapping("/deleteTaskList/{id}")
    public CompletableFuture<ResponseEntity<ApiResponse<Boolean>>> deleteTaskList(@PathVariable Long id) {
        return taskListService.deleteTaskList(id)
                .thenApply(apiResponse -> ResponseEntity.status(apiResponse.getStatusCode()).body(apiResponse));
    }
}
