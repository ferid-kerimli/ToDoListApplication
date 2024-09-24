package com.example.todolistapplication.dto.userDto;

import lombok.Data;

@Data
public class UserGetDto {
    private Long id;
    private String username;
    private String email;
}
