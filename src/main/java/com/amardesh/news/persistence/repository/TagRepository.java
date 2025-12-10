package com.amardesh.news.persistence.repository;

import com.amardesh.news.persistence.entity.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<TagEntity, Long> {
    boolean existsBySlug(String slug);
}


