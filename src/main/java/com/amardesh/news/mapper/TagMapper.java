package com.amardesh.news.mapper;

import com.amardesh.news.model.domain.Tag;
import com.amardesh.news.persistence.entity.TagEntity;
import org.springframework.stereotype.Component;

@Component
public class TagMapper {
    public Tag entityToDomain(TagEntity entity) {
        Tag tag = new Tag();
        tag.setId(entity.getId());
        tag.setName(entity.getName());
        tag.setSlug(entity.getSlug());
        return tag;
    }

    public TagEntity domainToEntity(Tag tag) {
        TagEntity entity = new TagEntity();
        entity.setId(tag.getId());
        entity.setName(tag.getName());
        entity.setSlug(tag.getSlug());
        return entity;
    }
    
    public Tag domainToResponse(Tag tag) {
        Tag response = new Tag();
        response.setId(tag.getId());
        response.setName(tag.getName());
        response.setSlug(tag.getSlug());
        return response;
    }
}
