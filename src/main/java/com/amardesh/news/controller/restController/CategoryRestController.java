package com.amardesh.news.controller.restController;

import com.amardesh.news.model.domain.Category;
import com.amardesh.news.model.dto.CreateCategoryRequest;
import com.amardesh.news.model.dto.UpdateCategoryRequest;
import com.amardesh.news.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name = "Category Resource", description = "API for managing categories")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories")
public class CategoryRestController {

    private final CategoryService categoryService;

    @Operation(summary = "Create a new category")
    @PostMapping
    public Long create(@RequestBody CreateCategoryRequest request) {
        return categoryService.create(request);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an Category by ID")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody UpdateCategoryRequest request) {
        categoryService.update(id, request);
        return ResponseEntity.noContent().build(); // 204 No Content
    }

    @Operation(summary = "Get category by ID")
    @GetMapping("/{id}")
    public Category getById(@PathVariable Long id) throws Exception {
        return categoryService.getCategoryById(id);
    }

    @Operation(summary = "Get all categories")
    @GetMapping()
    public List<Category> getAllCategories(){
        return categoryService.getAllCategory();
    }

    @Operation(summary = "Get category by slug")
    @GetMapping("/slug/{slug}")
    public Category getBySlug(@PathVariable String slug) {
        return categoryService.getCategoryBySlug(slug);
    }

    @Operation(summary = "Delete category by ID")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) throws Exception {
        categoryService.delete(id);
    }
}