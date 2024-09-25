package com.example.todolistapplication.dto.taskListDto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskListUpdateDto {
    private Long id;
    private String name;
    private LocalDateTime createdAt;
}
