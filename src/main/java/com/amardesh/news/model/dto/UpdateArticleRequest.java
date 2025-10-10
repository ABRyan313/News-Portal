package com.amardesh.news.model.dto;

public record UpdateArticleRequest(

        String title,
        String article,
        String author,
        String image,
        Long categoryId,
        Long[] tagIds,
         boolean published
) {
}
