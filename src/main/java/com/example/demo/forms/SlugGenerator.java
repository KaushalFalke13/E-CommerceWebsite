package com.example.demo.forms;

import java.text.Normalizer;

public class SlugGenerator {

    public static String generateSlug(String productName) {
        String normalized = Normalizer.normalize(productName, Normalizer.Form.NFD);
        String asciiOnly = normalized.replaceAll("[^\\x00-\\x7F]", "");
        String slug = asciiOnly.replaceAll("\\s+", "-").toLowerCase();
        
        slug = slug.replaceAll("[^a-z0-9-]", "");
        return slug;
    }
}

