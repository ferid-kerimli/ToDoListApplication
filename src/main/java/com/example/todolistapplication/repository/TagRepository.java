package com.example.todolistapplication.repository;

import com.example.todolistapplication.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
}
