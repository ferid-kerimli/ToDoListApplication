package com.example.todolistapplication.service.abstraction;

import com.example.todolistapplication.dto.tagDto.TagCreateDto;
import com.example.todolistapplication.dto.tagDto.TagGetDto;
import com.example.todolistapplication.dto.tagDto.TagUpdateDto;
import com.example.todolistapplication.response.ApiResponse;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface ITagService {
    CompletableFuture<ApiResponse<List<TagGetDto>>> getAllTags();
    CompletableFuture<ApiResponse<TagGetDto>> getTagById(Long tagId);
    CompletableFuture<ApiResponse<TagCreateDto>> createTag(TagCreateDto tagCreateDto);
    CompletableFuture<ApiResponse<TagUpdateDto>> updateTag(TagUpdateDto tagUpdateDto);
    CompletableFuture<ApiResponse<Boolean>> deleteTag(Long tagId);
}
