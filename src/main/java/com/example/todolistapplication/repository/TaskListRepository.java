package com.example.todolistapplication.repository;

import com.example.todolistapplication.entity.TaskList;
import com.example.todolistapplication.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskListRepository extends JpaRepository<TaskList, Long> {
    List<TaskList> findByUser(User user);
    TaskList findByIdAndUser(Long id, User user);
}
