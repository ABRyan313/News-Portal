package com.amardesh.news.model.dto;

public record UpdateArticleRequest(

        String title,
        String article,
        String author,
        Boolean published
) {
}
