package com.amardesh.news.controller.restController;

import com.amardesh.news.model.domain.Article;
import com.amardesh.news.model.dto.CreateArticleRequest;
import com.amardesh.news.model.dto.UpdateArticleRequest;
import com.amardesh.news.service.ArticleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Pageable;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Article Resource", description = "API for managing articles")
@RequiredArgsConstructor
@RestController
@RequestMapping("api/articles")
public class ArticleRestController {

    private final ArticleService articleService;

    @GetMapping
    public List<Article> getAllArticle(@ParameterObject Pageable pageable) {
        return articleService.getAllArticle(pageable);
    }

    @Operation(summary = "Create a new article")
    @PostMapping
    public void createArticle(@Valid @RequestBody CreateArticleRequest createPostRequest) {
        articleService.create(createPostRequest);
    }

    @Operation(summary = "Update the article by id")
    @PutMapping("{id}")
    public void updateArticle(@PathVariable Long id,@Valid @RequestBody UpdateArticleRequest request) throws ChangeSetPersister.NotFoundException {
        articleService.update(id, request);
    }

    @Operation(summary = "Delete the article by id")
    @DeleteMapping("{id}")
    public void deleteArticle(@PathVariable Long id) throws ChangeSetPersister.NotFoundException {
        articleService.delete(id);
    }

    @Operation(summary = "Get the article by id")
    @GetMapping("{id}")
    public Article getArticleById(@PathVariable Long id) throws ChangeSetPersister.NotFoundException {
        return articleService.getById(id);
    }
}
