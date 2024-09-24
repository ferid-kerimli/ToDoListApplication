package com.example.todolistapplication.response;

import lombok.Data;

import java.util.List;

@Data
public class ApiResponse<T> {
    private boolean isSuccess;
    private T data;
    private List<String> errors;
    private int statusCode;

    public void Success(T data, int statusCode) {
        isSuccess = true;
        this.data = data;
        errors = null;
        this.statusCode = statusCode;
    }

    public void Failure(List<String> errors, int statusCode) {
        isSuccess = false;
        data = null;
        this.errors = errors;
        this.statusCode = statusCode;
    }

    public void Failure(String error, int statusCode) {
        isSuccess = false;
        data = null;
        errors = List.of(error);
        this.statusCode = statusCode;
    }
}
