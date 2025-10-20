package com.amardesh.news.service;

import com.amardesh.news.mapper.ArticleMapper;
import com.amardesh.news.model.domain.Article;
import com.amardesh.news.model.dto.CreateArticleRequest;
import com.amardesh.news.model.dto.UpdateArticleRequest;
import com.amardesh.news.persistence.entity.ArticleEntity;
import com.amardesh.news.persistence.repository.ArticleRepository;
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

@RequiredArgsConstructor
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final ArticleMapper articleMapper;

    public List<Article> getAllArticle(Pageable pageable) {
        List<ArticleEntity> entityList = articleRepository.findAll(pageable).getContent();
        return entityList.stream().map(articleMapper::entityToDomain).toList();
    }

    public Page<Article> getArticlesByCategoryId(Long categoryId, Pageable pageable) {
        return articleRepository.findAllByCategoryId(categoryId, pageable)
                .map(articleMapper::entityToDomain);
    }


    public Page<Article> getAllPublishedArticle(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "publishedAt"));
        var entityPage = articleRepository.findAllByPublishedIsTrue(pageable);
        return entityPage.map(articleMapper::entityToDomain);
    }


    public Long create(CreateArticleRequest request) {
        var entityToSave = articleMapper.createRequestToEntity(request);
        entityToSave.setSummary(generateSummary(entityToSave.getArticle()));

        var title = request.title();

        ArticleUtils postUtils = new ArticleUtils();
        String slug = postUtils.getUniqueSlug(title);

        while (articleRepository.existsBySlug(slug)) {
            slug = postUtils.getUniqueSlug(title);
        }
        entityToSave.setSlug(slug);

        if (request.published()) {
            entityToSave.setCreatedAt(LocalDateTime.now());
        }

        var savedEntity = articleRepository.save(entityToSave);
        return savedEntity.getId();
    }


    public Article getById(Long id) throws ChangeSetPersister.NotFoundException {
        var articleEntity = this.findEntityById(id);
        return articleMapper.entityToDomain(articleEntity);
    }

    public Article getBySlug(String slug) throws ChangeSetPersister.NotFoundException {
        var articleEntity = this.findEntityBySlug(slug);
        System.out.println(articleEntity.getTitle());
        return articleMapper.entityToDomain(articleEntity);
    }


    public void update(Long id, UpdateArticleRequest request) throws ChangeSetPersister.NotFoundException {
        ArticleEntity articleEntity = this.findEntityById(id);
        ArticleEntity updatedArticleEntity = articleMapper.updateRequestToEntity(request, articleEntity);
        updatedArticleEntity.setSummary(generateSummary(updatedArticleEntity.getArticle()));

        if (request.published()) {
            updatedArticleEntity.setUpdatedAt(LocalDateTime.now());
        } else {
            updatedArticleEntity.setUpdatedAt(null);
        }

        articleRepository.save(updatedArticleEntity);
    }


    public void delete(Long id) throws ChangeSetPersister.NotFoundException {
        this.findEntityById(id);
        articleRepository.deleteById(id);
    }

    private ArticleEntity findEntityById(Long id) throws ChangeSetPersister.NotFoundException {
        return articleRepository.findById(id)
                .orElseThrow(() -> new ChangeSetPersister.NotFoundException());
    }

    public ArticleEntity findEntityBySlug(String slug) throws ChangeSetPersister.NotFoundException {
        return articleRepository.findBySlug(slug)
                .orElseThrow(() -> new ChangeSetPersister.NotFoundException());
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
