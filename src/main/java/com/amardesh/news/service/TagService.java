package com.amardesh.news.service;

import com.amardesh.news.mapper.TagMapper;
import com.amardesh.news.model.domain.Tag;
import com.amardesh.news.model.dto.TagCreateRequest;
import com.amardesh.news.persistence.entity.TagEntity;
import com.amardesh.news.persistence.repository.TagRepository;
import com.amardesh.news.utils.SlugUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TagService {

    private final TagRepository tagRepository;
    private final TagMapper tagMapper;
    private final SlugUtils slugUtils;

    public Tag createTag(TagCreateRequest request) {


        TagEntity entity = new TagEntity();
        entity.setName(request.getName());

        // generate slug
        String slug = slugUtils.slugify(request.getName());
        if (tagRepository.existsBySlug(slug)) {
            throw new RuntimeException("Tag already exists");
        }
        String uniqueSlug = slugUtils.makeUnique(slug, tagRepository::existsBySlug);
        entity.setSlug(uniqueSlug);

        TagEntity saved = tagRepository.save(entity);
        return tagMapper.entityToDomain(saved);
    }

    public List<Tag> getAllTags() {
        return tagRepository.findAll()
                    .stream()
                    .map(tagMapper::entityToDomain)
                    .toList();
    }
}


