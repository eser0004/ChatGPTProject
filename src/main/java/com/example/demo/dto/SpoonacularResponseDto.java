package com.example.demo.dto;

import java.util.List;

public class SpoonacularResponseDto {
    private List<Product> products;

    public static class Product {
        private String title;

        public String getTitle() {
            return title;
        }

        // Other product fields if necessary
    }

    public List<Product> getProducts() {
        return products;
    }
}
