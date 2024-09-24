package com.example.todolistapplication.service.implementation;

import com.example.todolistapplication.dto.tagDto.TagCreateDto;
import com.example.todolistapplication.dto.tagDto.TagGetDto;
import com.example.todolistapplication.dto.tagDto.TagUpdateDto;
import com.example.todolistapplication.entity.Tag;
import com.example.todolistapplication.repository.TagRepository;
import com.example.todolistapplication.response.ApiResponse;
import com.example.todolistapplication.service.abstraction.ITagService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class TagService implements ITagService {
    private final Logger logger = LoggerFactory.getLogger(TagService.class);
    private final ModelMapper modelMapper;
    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository, ModelMapper modelMapper) {
        this.tagRepository = tagRepository;
        this.modelMapper = modelMapper;
    }

    @Async
    @Override
    public CompletableFuture<ApiResponse<List<TagGetDto>>> getAllTags() {
        return CompletableFuture.supplyAsync(() -> {
            logger.info("GetAllTags called");
            var response = new ApiResponse<List<TagGetDto>>();

           try {
               var tags = tagRepository.findAll();

               if (tags.isEmpty()) {
                   response.Failure("There is no any existing tags", 400);
                   logger.warn("There is no any existing tags");
                   return response;
               }

               var tagGetDtoS = tags.stream()
                               .map(tag -> modelMapper.map(tag, TagGetDto.class))
                               .toList();

               response.Success(tagGetDtoS, 200);
               logger.info("GetAllTags completed successfully");
           }
           catch (Exception e) {
               response.Failure(e.getMessage(), 500);
               logger.error("Error getting tags", e);
           }

           return response;
        });
    }

    @Async
    @Override
    public CompletableFuture<ApiResponse<TagGetDto>> getTagById(Long tagId) {
        return CompletableFuture.supplyAsync(() -> {
            logger.info("GetTagById called");
            var response = new ApiResponse<TagGetDto>();

            try {
                var tag = tagRepository.findById(tagId);

                if (tag.isEmpty()) {
                    response.Failure("There is no any existing tag", 400);
                    logger.warn("There is no any existing tag");
                    return response;
                }

                var tagGetDto = modelMapper.map(tag, TagGetDto.class);

                response.Success(tagGetDto, 200);
                logger.info("GetTagById completed successfully");
            }
            catch (Exception e) {
                response.Failure(e.getMessage(), 500);
                logger.error("Error getting tag", e);
            }

            return response;
        });
    }

    @Async
    @Override
    public CompletableFuture<ApiResponse<TagCreateDto>> createTag(TagCreateDto tagCreateDto) {
        return CompletableFuture.supplyAsync(() -> {
           logger.info("CreateTag called");
           var response = new ApiResponse<TagCreateDto>();

           try {
               Tag tag = modelMapper.map(tagCreateDto, Tag.class);

               tag = tagRepository.save(tag);

               TagCreateDto createdTagDto = modelMapper.map(tag, TagCreateDto.class);

               response.Success(createdTagDto, 201);
               logger.info("Tag created successfully: {}", tagCreateDto);
           }
           catch (Exception e) {
               response.Failure(e.getMessage(), 500);
               logger.error("Error creating tag", e);
           }

           return response;
        });
    }

    @Async
    @Override
    public CompletableFuture<ApiResponse<TagUpdateDto>> updateTag(TagUpdateDto tagUpdateDto) {
        return CompletableFuture.supplyAsync(() -> {
            logger.info("UpdateTag called");
            var response = new ApiResponse<TagUpdateDto>();

            try {
                Tag existingTag = tagRepository.findById(tagUpdateDto.getId())
                        .orElseThrow(() -> new EntityNotFoundException("Tag not found"));

                modelMapper.map(tagUpdateDto, existingTag);

                existingTag = tagRepository.save(existingTag);

                TagUpdateDto updatedTagDto = modelMapper.map(existingTag, TagUpdateDto.class);

                response.Success(updatedTagDto, 200);
                logger.info("Tag updated successfully");
            }
            catch (Exception e) {
                response.Failure(e.getMessage(), 500);
                logger.error("Error updating tag", e);
            }

            return response;
        });
    }

    @Async
    @Override
    public CompletableFuture<ApiResponse<Boolean>> deleteTag(Long tagId) {
        return CompletableFuture.supplyAsync(() -> {
            logger.info("DeleteTag called");
            var response = new ApiResponse<Boolean>();

            try {
                if (!tagRepository.existsById(tagId)) {
                    response.Failure("Tag not found", 404);
                    logger.warn("Tag not found with this id: {}", tagId);
                    return response;
                }

                tagRepository.deleteById(tagId);
                response.Success(true, 200);
                logger.info("Tag deleted successfully");
            }
            catch (Exception e) {
                response.Failure(e.getMessage(), 500);
                logger.error("Error deleting tag", e);
            }

            return response;
        });
    }
}
