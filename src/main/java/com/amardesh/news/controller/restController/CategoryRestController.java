package com.amardesh.news.controller.restController;

import com.amardesh.news.model.domain.Category;
import com.amardesh.news.model.dto.CreateCategoryRequest;
import com.amardesh.news.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

    @Operation(summary = "Get all categories with pagination")
    @GetMapping
    public List<Category> getAll(@PageableDefault(size = 10) Pageable pageable) {
        return categoryService.getAllCategory(pageable);
    }

    @Operation(summary = "Get category by ID")
    @GetMapping("/{id}")
    public Category getById(@PathVariable Long id) throws Exception {
        return categoryService.getCategoryById(id);
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