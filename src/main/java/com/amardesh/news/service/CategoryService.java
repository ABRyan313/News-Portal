package com.amardesh.news.service;

import com.amardesh.news.mapper.CategoryMapper;
import com.amardesh.news.model.domain.Category;
import com.amardesh.news.model.dto.CreateCategoryRequest;
import com.amardesh.news.persistence.entity.CategoryEntity;
import com.amardesh.news.persistence.repository.CategoryRepository;
import com.amardesh.news.utils.CategoryUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final CategoryUtils categoryUtils;

    public Long create(CreateCategoryRequest request) {
        var entityToSave = categoryMapper.createRequestToEntity(request);
        var name = request.name();

        String slug = categoryUtils.getUniqueSlug(name);
        while (categoryRepository.existsBySlug(slug)) {
            slug = categoryUtils.getUniqueSlug(name);
        }
        entityToSave.setSlug(slug);

        var savedEntity = categoryRepository.save(entityToSave);
        return savedEntity.getId();
    }

    public List<Category> getAllCategory(Pageable pageable) {
        var entityList = categoryRepository.findAll(pageable).getContent();
        return entityList.stream()
                .map(categoryMapper::entityToDomain)
                .toList();
    }

    public Category getCategoryById(Long id) {
        var entity = categoryRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Category not found with id: " + id));
        return categoryMapper.entityToDomain(entity);
    }

    public Category getCategoryBySlug(String slug) {
        var categoryEntity = findEntityBySlug(slug);
        return categoryMapper.entityToDomain(categoryEntity);
    }

    private CategoryEntity findEntityBySlug(String slug) {
        return categoryRepository.findBySlug(slug)
                .orElseThrow(() -> new NoSuchElementException("Category not found with slug: " + slug));
    }

    public void delete(Long id) {
        findEntityById(id);
        categoryRepository.deleteById(id);
    }

    private CategoryEntity findEntityById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Category not found with id: " + id));
    }
}

