package com.amardesh.news.persistence.entity;

import com.amardesh.news.model.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

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

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = true) // make nullable if category optional
    private CategoryEntity category;

}
