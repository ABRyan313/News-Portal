package com.amardesh.news.model.dto;

import com.amardesh.news.model.domain.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateArticleRequest(

        @NotBlank(message = "Title is mandatory")
        @Size(min = 5, message = "Title must be at least 5 characters long")
        String title,

        @NotBlank(message = "Content is mandatory")
        @Size(min = 10, message = "Content must be at least 10 characters long")
        String article,

        String author,

        @NotNull(message = "Published status is required")
        Boolean published,

        Category category

) {
}
