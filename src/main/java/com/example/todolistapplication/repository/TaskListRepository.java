package com.example.todolistapplication.repository;

import com.example.todolistapplication.entity.TaskList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskListRepository extends JpaRepository<TaskList, Long> {
}
