package com.amardesh.news.service;

import com.amardesh.news.mapper.ArticleMapper;
import com.amardesh.news.model.domain.Article;
import com.amardesh.news.model.dto.CreateArticleRequest;
import com.amardesh.news.model.dto.UpdateArticleRequest;
import com.amardesh.news.persistence.entity.ArticleEntity;
import com.amardesh.news.persistence.repository.ArticleRepository;
import com.amardesh.news.persistence.repository.CategoryRepository;
import com.amardesh.news.utils.ArticleUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final ArticleMapper articleMapper;
    private final CategoryRepository categoryRepository;
    private final ArticleUtils articleUtils;

    public List<Article> getAllArticles(Pageable pageable) {
        List<ArticleEntity> entityList = articleRepository.findAll(pageable).getContent();
        return entityList.stream().map(articleMapper::entityToDomain).toList();
    }

    public Page<Article> getArticlesByCategoryId(Long categoryId, Pageable pageable) {
        if (!categoryRepository.existsById(categoryId)) {
            throw new NoSuchElementException("Category not found with id: " + categoryId);
        }

        return articleRepository.findAllByCategoryId(categoryId, pageable)
                .map(articleMapper::entityToDomain);
    }

    public Page<Article> getAllPublishedArticles(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "publishedAt"));
        var entityPage = articleRepository.findAllByPublishedIsTrue(pageable);
        return entityPage.map(articleMapper::entityToDomain);
    }


    public Long create(CreateArticleRequest request) {
        var entityToSave = articleMapper.createRequestToEntity(request);
        entityToSave.setSummary(generateSummary(entityToSave.getArticle()));

        var title = request.title();

        String slug = articleUtils.getUniqueSlug(title);

        while (articleRepository.existsBySlug(slug)) {
            slug = articleUtils.getUniqueSlug(title);
        }
        entityToSave.setSlug(slug);

        var savedEntity = articleRepository.save(entityToSave);
        return savedEntity.getId();
    }

    public void assignCategory(Long articleId, Long categoryId) {
        var articleEntity = findEntityById(articleId);
        var categoryEntity = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NoSuchElementException("Category not found with id: " + categoryId));
        articleEntity.setCategory(categoryEntity);
        articleRepository.save(articleEntity);
    }


    public Article getById(Long id) {
        var articleEntity = this.findEntityById(id);
        return articleMapper.entityToDomain(articleEntity);
    }

    public Article getBySlug(String slug) throws ChangeSetPersister.NotFoundException {
        var articleEntity = this.findEntityBySlug(slug);
        return articleMapper.entityToDomain(articleEntity);
    }

    public void update(Long id, UpdateArticleRequest request) {
        ArticleEntity articleEntity = this.findEntityById(id);
        ArticleEntity updatedArticleEntity = articleMapper.updateRequestToEntity(request, articleEntity);
        updatedArticleEntity.setSummary(generateSummary(updatedArticleEntity.getArticle()));

        updatedArticleEntity.setUpdatedAt(LocalDateTime.now());

        articleRepository.save(updatedArticleEntity);
    }

    public void delete(Long id) {
        this.findEntityById(id);
        articleRepository.deleteById(id);
    }

    private ArticleEntity findEntityById(Long id) throws NoSuchElementException {
        return articleRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Article not found with id: " + id));
    }

    public ArticleEntity findEntityBySlug(String slug) throws NoSuchElementException {
        return articleRepository.findBySlug(slug)
                .orElseThrow(() -> new NoSuchElementException("Article not found with id: " + slug));

    }

    private String generateSummary(String content) {
        if (content == null || content.isBlank()) return "";

        // Simple approach: take first 100 characters
        int summaryLength = 100;
        return content.length() <= summaryLength
                ? content
                : content.substring(0, summaryLength) + "...";
    }
}
