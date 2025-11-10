package com.amardesh.news.mapper;

import com.amardesh.news.model.domain.Article;
import com.amardesh.news.model.dto.CreateArticleRequest;
import com.amardesh.news.model.dto.UpdateArticleRequest;
import com.amardesh.news.persistence.entity.ArticleEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class ArticleMapper {

    public Article entityToDomain(ArticleEntity entity) {
        Article article = new Article();
        BeanUtils.copyProperties(entity, article);
        return article;
    }

    public ArticleEntity createRequestToEntity(CreateArticleRequest article) {
        ArticleEntity entity = new ArticleEntity();
        BeanUtils.copyProperties(article, entity);
        return entity;
    }

    public ArticleEntity updateRequestToEntity(UpdateArticleRequest articles, ArticleEntity entity) {
        entity.setArticle(articles.article());
        entity.setPublished(articles.published());
        return entity;
    }

    public Article toDto(Article article) {
        if (article == null) {
            return null;
        }
        Article dto = new Article();
        BeanUtils.copyProperties(article, dto);
        return dto;
    }
}
