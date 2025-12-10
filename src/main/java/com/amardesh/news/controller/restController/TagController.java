package com.amardesh.news.controller.restController;

import com.amardesh.news.mapper.TagMapper;
import com.amardesh.news.model.domain.Tag;
import com.amardesh.news.model.dto.TagCreateRequest;
import com.amardesh.news.service.TagService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/tags")
public class TagController {
    
    private final TagService tagService;
    private final TagMapper tagMapper;

    @PostMapping
    @Operation(summary = "Create a tag")
    public ResponseEntity<Tag> createTag(@RequestBody TagCreateRequest request) {
        Tag tag = tagService.createTag(request);
        return ResponseEntity.ok(tagMapper.domainToResponse(tag));
    }

    @GetMapping
    @Operation(summary = "Get all tags")
    public List<Tag> getAllTags() {
        return tagService.getAllTags()
                    .stream()
                    .map(tagMapper::domainToResponse)
                    .toList();
    }
}


