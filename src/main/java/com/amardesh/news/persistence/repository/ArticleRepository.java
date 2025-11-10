package com.amardesh.news.persistence.repository;


import com.amardesh.news.model.Status;
import com.amardesh.news.persistence.entity.ArticleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends JpaRepository<ArticleEntity, Long> {

    Optional<ArticleEntity> findBySlug(String slug);
    boolean existsBySlug(String slug);

    Page<ArticleEntity> findAllByPublishedIsTrue(Pageable pageable);
    Page<ArticleEntity> findAllByCategoryId(Long categoryId, Pageable pageable);

    List<ArticleEntity> findByStatus(Status status);
//    List<ArticleEntity> findByStatusOrderByCreatedAtDesc(Status status);
//
//    @Query("""
//        SELECT a FROM ArticleEntity a
//        WHERE (LOWER(a.title) LIKE LOWER(CONCAT('%', :keyword, '%'))
//            OR LOWER(a.summary) LIKE LOWER(CONCAT('%', :keyword, '%'))
//            OR LOWER(a.article) LIKE LOWER(CONCAT('%', :keyword, '%')))
//        AND a.status = :status
//    """)
//    List<ArticleEntity> searchByKeywordAndStatus(@Param("keyword") String keyword,
//                                                 @Param("status") Status status);
//
//    @Query("""
//        SELECT a FROM ArticleEntity a
//        WHERE a.category.id = :catId AND a.status = :status
//    """)
//    List<ArticleEntity> findByCategoryIdAndStatus(@Param("catId") Long catId,
//                                                  @Param("status") Status status);
//
//    @Query("""
//        SELECT a FROM ArticleEntity a
//        JOIN a.tags t
//        WHERE t.id = :tagId AND a.status = :status
//    """)
//    List<ArticleEntity> findByTagIdAndStatus(@Param("tagId") Long tagId,
//                                             @Param("status") Status status);
}