package com.amardesh.news.persistence.entity;

import com.amardesh.news.model.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "articles", uniqueConstraints = {
        @UniqueConstraint(columnNames = "slug")}
)
public class ArticleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String slug;

    @Column(length = 500)
    private String summary;

    @Lob
    @Column(nullable = false)
    private String article;

    private String author;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "is_published")
    private Boolean published;

    @CreationTimestamp
    private LocalDateTime createdAt;

    private LocalDateTime publishedAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = true) // make nullable if category optional
    private CategoryEntity category;

    /**
     * Auto–set publishedAt WHEN published = true
     * Works for both create & update.
     */
    @PrePersist
    @PreUpdate
    public void handlePublishTimestamp() {
        if (Boolean.TRUE.equals(published) && publishedAt == null) {
            publishedAt = LocalDateTime.now();
        }
    }

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "article_tag",
            joinColumns = @JoinColumn(name = "article_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<TagEntity> tags = new HashSet<>();

}
