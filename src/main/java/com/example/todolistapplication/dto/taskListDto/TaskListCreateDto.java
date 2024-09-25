package com.example.todolistapplication.dto.taskListDto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskListCreateDto {
    private String name;
    private LocalDateTime createdAt;
}
