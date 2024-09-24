package com.example.todolistapplication.repository;

import com.example.todolistapplication.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
