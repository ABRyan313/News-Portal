package com.amardesh.news.service;

import com.amardesh.news.mapper.ArticleMapper;
import com.amardesh.news.model.domain.Article;
import com.amardesh.news.model.dto.CreateArticleRequest;
import com.amardesh.news.model.dto.UpdateArticleRequest;
import com.amardesh.news.persistence.entity.ArticleEntity;
import com.amardesh.news.persistence.entity.TagEntity;
import com.amardesh.news.persistence.repository.ArticleRepository;
import com.amardesh.news.persistence.repository.CategoryRepository;
import com.amardesh.news.persistence.repository.TagRepository;
import com.amardesh.news.utils.SlugUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final ArticleMapper articleMapper;
    private final CategoryRepository categoryRepository;
    private final SlugUtils slugUtils;
    private final TagRepository tagRepository;

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

        var entity = articleMapper.createRequestToEntity(request);

        if (request.published()) {
            entity.setPublishedAt(LocalDateTime.now());
        }

        // set summary
        entity.setSummary(generateSummary(entity.getArticle()));

        // assign category
        var category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new NoSuchElementException("Category not found: " + request.categoryId()));
        entity.setCategory(category);

        // Generate slug using utility class
        String baseSlug = slugUtils.slugify(request.title());
        String uniqueSlug = slugUtils.makeUnique(baseSlug, articleRepository::existsBySlug);
        entity.setSlug(uniqueSlug);

        // Save
        var saved = articleRepository.save(entity);
        return saved.getId();
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

        String oldTitle = articleEntity.getTitle(); // capture before update

        articleMapper.updateRequestToEntity(request, articleEntity);

        if (!Objects.equals(oldTitle, request.title())) {
            String baseSlug = slugUtils.slugify(request.title());
            String uniqueSlug = slugUtils.makeUnique(baseSlug, articleRepository::existsBySlug);
            articleEntity.setSlug(uniqueSlug);
        }

        articleEntity.setUpdatedAt(LocalDateTime.now());
        articleRepository.save(articleEntity);
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

    @Transactional
    public void addTagToArticle(Long articleId, Long tagId) {

        ArticleEntity article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Article not found"));

        TagEntity tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tag not found"));

        article.getTags().add(tag);
        tag.getArticles().add(article);

        articleRepository.save(article);
    }

    @Transactional
    public void removeTagFromArticle(Long articleId, Long tagId) {

        ArticleEntity article = articleRepository.findById(articleId)
                .orElseThrow(() -> new NoSuchElementException("Article not found"));

        TagEntity tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new NoSuchElementException("Tag not found"));

        article.getTags().remove(tag);

        articleRepository.save(article);
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
