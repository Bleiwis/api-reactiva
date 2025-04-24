package com.example.api_reactiva.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Lombok annotations to generate boilerplate code like getters, setters, and constructors
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    // Unique identifier for the product
    private String id;

    // Name of the product
    private String name;

    // Price of the product
    private double price;
}
