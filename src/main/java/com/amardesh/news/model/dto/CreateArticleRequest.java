package com.amardesh.news.model.dto;

public record CreateArticleRequest(
        String title,
        String article,
        String author,
        String image,
        Long categoryId,
        Long[] tagIds,
        Boolean published
) {
}
