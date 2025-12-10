package com.amardesh.news.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TagCreateRequest {

    @NotBlank(message = "Tag name is required")
    private String name;
}
