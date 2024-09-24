package com.example.todolistapplication.controller;

import com.example.todolistapplication.dto.tagDto.TagCreateDto;
import com.example.todolistapplication.dto.tagDto.TagGetDto;
import com.example.todolistapplication.dto.tagDto.TagUpdateDto;
import com.example.todolistapplication.response.ApiResponse;
import com.example.todolistapplication.service.abstraction.ITagService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RequestMapping("/tag")
@RestController
public class TagController {
    private final ITagService tagService;

    public TagController(ITagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping("/getAllTags")
    public CompletableFuture<ResponseEntity<ApiResponse<List<TagGetDto>>>> getAllTags() {
        return tagService.getAllTags()
                .thenApply(apiResponse -> ResponseEntity.status(apiResponse.getStatusCode()).body(apiResponse));
    }

    @GetMapping("/id/{tagId}")
    public CompletableFuture<ResponseEntity<ApiResponse<TagGetDto>>> getTagById(@PathVariable Long tagId) {
        return tagService.getTagById(tagId)
                .thenApply(apiResponse -> ResponseEntity.status(apiResponse.getStatusCode()).body(apiResponse));
    }

    @PostMapping("/createTag")
    public CompletableFuture<ResponseEntity<ApiResponse<TagCreateDto>>> createTag(@RequestBody TagCreateDto tagCreateDto) {
        return tagService.createTag(tagCreateDto)
                .thenApply(apiResponse -> ResponseEntity.status(apiResponse.getStatusCode()).body(apiResponse));
    }

    @PutMapping("/updateTag")
    public CompletableFuture<ResponseEntity<ApiResponse<TagUpdateDto>>> updateTag(@RequestBody TagUpdateDto tagUpdateDto) {
        return tagService.updateTag(tagUpdateDto)
                .thenApply(apiResponse -> ResponseEntity.status(apiResponse.getStatusCode()).body(apiResponse));
    }

    @DeleteMapping("/id/{tagId}")
    public CompletableFuture<ResponseEntity<ApiResponse<Boolean>>> deleteTag(@PathVariable Long tagId) {
        return tagService.deleteTag(tagId)
                .thenApply(apiResponse -> ResponseEntity.status(apiResponse.getStatusCode()).body(apiResponse));
    }
}
