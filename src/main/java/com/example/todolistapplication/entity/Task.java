package com.example.todolistapplication.entity;

import com.example.todolistapplication.enums.TaskStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    @Enumerated(EnumType.STRING)
    private TaskStatus taskStatus;

    private LocalDateTime createdAt;
    private LocalDateTime dueDate;

    @ManyToOne
    @JoinColumn(name = "tasklist_id")
    private TaskList taskList;

    @ManyToMany
    @JoinTable(
            name = "task_tags",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags;
}
