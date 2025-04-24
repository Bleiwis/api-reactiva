package com.example.api_reactiva.service;

import com.example.api_reactiva.model.Product;
import com.example.api_reactiva.repository.ProductRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

// Annotation to indicate that this class is a service component
@Service
public class ProductService {

    private final ProductRepository productRepository;

    // Constructor injection for the repository
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // Save a product
    public Mono<Product> saveProduct(Product product) {
        return productRepository.save(product);
    }

    // Get a product by ID
    public Mono<Product> getProductById(String id) {
        return productRepository.findById(id);
    }

    // Get all products
    public Flux<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Delete a product by ID
    public Mono<Void> deleteProductById(String id) {
        return productRepository.deleteById(id);
    }
}
