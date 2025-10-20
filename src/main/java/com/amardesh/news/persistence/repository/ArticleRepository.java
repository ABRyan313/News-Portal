package com.amardesh.news.persistence.repository;

import com.amardesh.news.model.domain.Article;
import com.amardesh.news.model.Status;
import com.amardesh.news.persistence.entity.ArticleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends JpaRepository<ArticleEntity, Long> {

    Optional<ArticleEntity> findBySlug(String slug);
    boolean existsBySlug(String slug);
    Page<ArticleEntity> findAllByPublishedIsTrue(Pageable pageable);

    Page<ArticleEntity> findAllByCategoryId(Long categoryId, Pageable pageable);


    List<ArticleEntity> findByStatus(Status status);

    // Fetch published articles, ordered by publishDateTime desc
    List<ArticleEntity> findByStatusOrderByPublishDateTimeDesc(Status status);

    // Search by title or content (simplest form)
    List<ArticleEntity> searchByKeywordAndStatus(@Param("keyword") String keyword,
                                           @Param("status") Status status);

    // Find articles by a given category
    List<ArticleEntity> findByCategoryIdAndStatus(@Param("catId") Long catId,
                                            @Param("status") Status status);

    // Find articles by a given tag
    List<ArticleEntity> findByTagIdAndStatus(@Param("tagId") Long tagId,
                                       @Param("status") Status status);
}
