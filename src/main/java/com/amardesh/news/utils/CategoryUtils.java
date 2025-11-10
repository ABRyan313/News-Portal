package com.amardesh.news.utils;

import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class CategoryUtils {

    public String getUniqueSlug(String Category_title) {
        Category_title = Category_title.toLowerCase().replaceAll("[^a-z0-9\\s]", "").replaceAll("\\s+", "-");
        long timestamp = System.currentTimeMillis();
        String base64Timestamp = Base64.getUrlEncoder().withoutPadding()
                .encodeToString(Long.toString(timestamp).getBytes());
        String slug = Category_title + "-" + base64Timestamp;
        return slug;
    }
}
