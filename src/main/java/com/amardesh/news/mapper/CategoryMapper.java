package com.amardesh.news.mapper;

import com.amardesh.news.model.domain.Category;
import com.amardesh.news.model.dto.CreateCategoryRequest;
import com.amardesh.news.model.dto.UpdateCategoryRequest;
import com.amardesh.news.persistence.entity.CategoryEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper{

    public Category entityToDomain(CategoryEntity entity){

        Category category = new Category();
        BeanUtils.copyProperties(entity, category);
        return category;
    }

    public CategoryEntity createRequestToEntity(CreateCategoryRequest category){
        CategoryEntity entity = new CategoryEntity();
        BeanUtils.copyProperties(category, entity);
        return entity;
    }

    public CategoryEntity updateRequestToEntity(UpdateCategoryRequest category, CategoryEntity entity){
        entity.setName(category.name());
        entity.setSlug(category.slug());
        return entity;
    }
}
