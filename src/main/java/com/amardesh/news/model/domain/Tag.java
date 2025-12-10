package com.amardesh.news.model.domain;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Tag {

    private Long id;
    private String name;
    private String slug;

}
