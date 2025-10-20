package com.amardesh.news.controller.restController;


import com.amardesh.news.mapper.ArticleMapper;
import com.amardesh.news.model.domain.Article;
import com.amardesh.news.model.domain.Category;
import com.amardesh.news.model.dto.CreateCategoryRequest;
import com.amardesh.news.service.ArticleService;
import com.amardesh.news.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Category Resource", description = "API for managing Category")
@RequiredArgsConstructor
@RestController
@RequestMapping("api/categories")
public class CategoryRestController {

    private final ArticleService articleService;
    private final CategoryService categoryService;
    private final ArticleMapper articleMapper;

    @GetMapping
    public List<Category> getAllCategory(@ParameterObject Pageable pageable) {
        return categoryService.getAllCategory(pageable);
    }

    @Operation(summary = "Create a new Category")
    @PostMapping
    public void createCategory(@Valid @RequestBody CreateCategoryRequest createCategoryRequest) {
        categoryService.create(createCategoryRequest);
    }

    @Operation(summary = "Delete the Category by id")
    @DeleteMapping("{id}")
    public void deleteCategory(@PathVariable Long id) throws ChangeSetPersister.NotFoundException {
        categoryService.delete(id);
    }

    @Operation(summary = "Get all article from a category")
    @GetMapping("/{categoryId}/articles")
    public ResponseEntity<Page<Article>> getArticlesByCategory(
            @PathVariable Long categoryId,
            @PageableDefault(size = 20, sort = "publishDateTime", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Article> page = articleService.getArticlesByCategoryId(categoryId, pageable);
        Page<Article> article = page.map(articleMapper::toDto);
        return ResponseEntity.ok(article);
    }
}
