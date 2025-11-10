package com.amardesh.news.model.domain;

import lombok.Getter;
import lombok.Setter;


import java.util.List;

@Getter
@Setter
public class Category {

    private Long id;
    private String name;
    private String slug;
}
