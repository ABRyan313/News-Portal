package com.amardesh.news.service;

import com.amardesh.news.mapper.ArticleMapper;
import com.amardesh.news.mapper.CategoryMapper;
import com.amardesh.news.model.domain.Category;
import com.amardesh.news.model.dto.CreateCategoryRequest;
import com.amardesh.news.persistence.entity.CategoryEntity;
import com.amardesh.news.persistence.repository.ArticleRepository;
import com.amardesh.news.persistence.repository.CategoryRepository;
import com.amardesh.news.utils.CategoryUtils;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ArticleRepository ArticleRepository;
    private final ArticleRepository articleRepository;
    private final CategoryMapper categoryMapper;
    private final ArticleMapper articleMapper;

    public Long create(CreateCategoryRequest request) {
        var entityToSave = categoryMapper.createRequestToEntity(request);
       entityToSave.getName();

        var name = request.name();

        CategoryUtils categoryUtils = new CategoryUtils();
        String slug = categoryUtils.getUniqueSlug(name);

        while (categoryRepository.existsBySlug(slug)) {
            slug = categoryUtils.getUniqueSlug(name);
        }
        entityToSave.setSlug(slug);

        var savedEntity = categoryRepository.save(entityToSave);
        return savedEntity.getId();
    }

    public List<Category> getAllCategory(Pageable pageable){
        var entityList = categoryRepository.findAll(pageable).getContent();
        return entityList.stream().map(categoryMapper::entityToDomain).toList();
    }

    public Category getCategoryById(Long id) throws Exception {
        var entity = categoryRepository.findById(id).orElseThrow(()-> new Exception());
        return categoryMapper.entityToDomain(entity);
    }

    public Category getCategoryBySlug(String slug) throws ChangeSetPersister.NotFoundException {
        var categoryEntity = this.findEntityBySlug(slug);
        System.out.println(categoryEntity.getName());
        return categoryMapper.entityToDomain(categoryEntity);
    }

    public
    CategoryEntity findEntityBySlug(String slug) throws ChangeSetPersister.NotFoundException{
        return categoryRepository.findBySlug(slug)
                .orElseThrow(() -> new ChangeSetPersister.NotFoundException());
    }

    public void delete(Long id) throws ChangeSetPersister.NotFoundException {
        this.findEntityById(id);
        categoryRepository.deleteById(id);
    }

    private CategoryEntity findEntityById(Long id) throws ChangeSetPersister.NotFoundException {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ChangeSetPersister.NotFoundException());
    }



}
