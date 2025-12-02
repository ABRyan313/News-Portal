package com.amardesh.news.service;

import com.amardesh.news.mapper.CategoryMapper;
import com.amardesh.news.model.domain.Category;
import com.amardesh.news.model.dto.CreateCategoryRequest;
import com.amardesh.news.model.dto.UpdateCategoryRequest;
import com.amardesh.news.persistence.entity.CategoryEntity;
import com.amardesh.news.persistence.repository.CategoryRepository;
import com.amardesh.news.utils.SlugUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final SlugUtils slugUtils;

    public Long create(CreateCategoryRequest request) {
        // Map request DTO → entity
        var entityToSave = categoryMapper.createRequestToEntity(request);

        // Generate unique slug
        String baseSlug = slugUtils.slugify(request.name());
        String uniqueSlug = slugUtils.makeUnique(baseSlug, categoryRepository::existsBySlug);
        entityToSave.setSlug(uniqueSlug);

        // Save and return ID
        var savedEntity = categoryRepository.save(entityToSave);
        return savedEntity.getId();
    }

    public void update(Long id, UpdateCategoryRequest request) {
        // 1️⃣ Find the existing entity
        var categoryEntity = categoryRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Category not found: " + id));

        // 2️⃣ Update fields from request
        categoryEntity.setName(request.name().trim());

        // 3️⃣ Regenerate slug ONLY if name changed
        if (!categoryEntity.getName().equalsIgnoreCase(request.name().trim())) {
            String baseSlug = slugUtils.slugify(request.name());
            String uniqueSlug = slugUtils.makeUnique(baseSlug, categoryRepository::existsBySlug);
            categoryEntity.setSlug(uniqueSlug);
        }

        // 5️⃣ Save updated entity
        categoryRepository.save(categoryEntity);
    }


    public List<Category> getAllCategory() {
        List<CategoryEntity> entityList = categoryRepository.findAll();
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

    private void findEntityById(Long id) {
        categoryRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Category not found with id: " + id));
    }
}

