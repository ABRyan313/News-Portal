package com.amardesh.news.persistence.repository;

import com.amardesh.news.persistence.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

    Optional<CategoryEntity> findBySlug(String slug);
    Optional<CategoryEntity> findById(Long id);
    boolean existsBySlug(String slug);
}
