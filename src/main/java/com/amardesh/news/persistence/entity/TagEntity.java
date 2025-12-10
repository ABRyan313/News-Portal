package com.amardesh.news.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tags")
public class TagEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String name;

        @Column(unique = true)
        private String slug;

        @JsonIgnore //to avoid infinite recursion
        @ManyToMany(mappedBy = "tags")
        private Set<ArticleEntity> articles = new HashSet<>();
}


