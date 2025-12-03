package com.amardesh.news.mapper;

import com.amardesh.news.model.domain.Article;
import com.amardesh.news.model.domain.Category;
import com.amardesh.news.model.dto.CreateArticleRequest;
import com.amardesh.news.model.dto.UpdateArticleRequest;
import com.amardesh.news.persistence.entity.ArticleEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class ArticleMapper {

    /** ENTITY → DOMAIN (DTO used for API response) */
    public Article entityToDomain(ArticleEntity entity) {
        if (entity == null) {
            return null;
        }

        Article dto = new Article();
        BeanUtils.copyProperties(entity, dto);

        // Manually map category (BeanUtils will NOT map nested objects)
        if (entity.getCategory() != null) {
            Category categoryDto = new Category();
            categoryDto.setId(entity.getCategory().getId());
            categoryDto.setName(entity.getCategory().getName());
            categoryDto.setSlug(entity.getCategory().getSlug());

            dto.setCategory(categoryDto);
        }

        return dto;
    }


    /** CREATE REQUEST → ENTITY */
    public ArticleEntity createRequestToEntity(CreateArticleRequest request) {
        ArticleEntity entity = new ArticleEntity();
        BeanUtils.copyProperties(request, entity);

        // categoryId is not mapped here (we set it in service)
        return entity;
    }


    /** UPDATE REQUEST → ENTITY (only selected fields) */
    public void updateRequestToEntity(UpdateArticleRequest request, ArticleEntity entity) {
        entity.setArticle(request.article());
        entity.setTitle(request.title());
        entity.setPublished(request.published());

        // publishedAt will be auto-set using @PreUpdate if needed
    }

}
