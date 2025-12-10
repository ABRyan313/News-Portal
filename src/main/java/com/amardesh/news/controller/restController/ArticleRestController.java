package com.amardesh.news.controller.restController;

import com.amardesh.news.model.domain.Article;
import com.amardesh.news.model.dto.AddTagRequest;
import com.amardesh.news.model.dto.CreateArticleRequest;
import com.amardesh.news.model.dto.UpdateArticleRequest;
import com.amardesh.news.service.ArticleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Tag(name = "Article Resource", description = "API for managing articles")
@RestController
@RequestMapping("/articles")
@RequiredArgsConstructor
public class ArticleRestController {

    private final ArticleService articleService;

    @PostMapping
    @Operation(summary = "Create a new article")
    public ResponseEntity<Long> create(@RequestBody CreateArticleRequest request) {
        Long id = articleService.create(request);
        return ResponseEntity.status(201).body(id); // 201 Created
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get article by ID")
    public ResponseEntity<Article> getById(@PathVariable Long id) {
        Article article = articleService.getById(id);
        return ResponseEntity.ok(article); // 200 OK
    }

    @GetMapping("/slug/{slug}")
    @Operation(summary = "Get article by slug")
    public ResponseEntity<Article> getBySlug(@PathVariable String slug) throws ChangeSetPersister.NotFoundException {
        Article article = articleService.getBySlug(slug);
        return ResponseEntity.ok(article);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an article by ID")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody UpdateArticleRequest request) {
        articleService.update(id, request);
        return ResponseEntity.noContent().build(); // 204 No Content
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an article by ID")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        articleService.delete(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }


    @GetMapping("/category/{categoryId}")
    @Operation(summary = "Get articles by category ID (paginated)")
    public ResponseEntity<Page<Article>> getByCategory(@PathVariable Long categoryId,
                                                       @PageableDefault(size = 10) Pageable pageable) {
        Page<Article> page = articleService.getArticlesByCategoryId(categoryId, pageable);
        return ResponseEntity.ok(page);
    }

    @Operation(summary = "Add Tag")
    @PostMapping("/{id}/add-tag")
    public ResponseEntity<?> addTag(
            @PathVariable Long id,
            @RequestBody AddTagRequest request) {
        articleService.addTagToArticle(id, request.tagId());
        return ResponseEntity.ok("Tag added");
    }

    @Operation(summary = "Delete tag")
    @DeleteMapping("/{id}/remove-tag/{tagId}")
    public ResponseEntity<?> removeTag(
            @PathVariable Long id,
            @PathVariable Long tagId) {
        articleService.removeTagFromArticle(id, tagId);
        return ResponseEntity.ok("Tag removed");
    }
}