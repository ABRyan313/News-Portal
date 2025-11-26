package com.amardesh.news.utils;

import org.springframework.stereotype.Component;

import java.util.function.Predicate;

@Component
public class Utils {

    /**
     * Convert a title into a clean slug.
     * Example: "Spring Boot Developer Guide!" -> "spring-boot-developer-guide"
     */
    public String slugify(String input) {
        if (input == null) return "";

        return input.toLowerCase()
                .replaceAll("[^a-z0-9\\s-]", "")  // remove special characters
                .replaceAll("\\s+", "-")          // spaces → hyphens
                .replaceAll("-+", "-")            // remove repeated hyphens
                .replaceAll("^-|-$", "");         // trim hyphens
    }

    /**
     * Generate a unique slug by adding -1, -2, -3 if needed.
     */
    public String makeUnique(String baseSlug, Predicate<String> existsBySlug) {
        String slug = baseSlug;
        int counter = 1;

        while (existsBySlug.test(slug)) {
            slug = baseSlug + "-" + counter++;
        }
        return slug;
    }
}

