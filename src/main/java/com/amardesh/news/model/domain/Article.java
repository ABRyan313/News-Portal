package com.amardesh.news.model.domain;

import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Article {

    private Long id;
    private String title;
    private String article;
    private String author;
    private String slug;
    private Category category;
    private Boolean published;
    private LocalDateTime publishedAt;
    private String summary;
    private Set<Tag> tags = new HashSet<>();
}
