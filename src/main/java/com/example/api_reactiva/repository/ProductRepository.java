package com.example.api_reactiva.repository;

import com.example.api_reactiva.model.Product;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

// Annotation to indicate that this class is a repository component
@Repository
public class ProductRepository {

    // In-memory data store for products
    private final Map<String, Product> productStore = new ConcurrentHashMap<>();

    // Save a product to the store
    public Mono<Product> save(Product product) {
        productStore.put(product.getId(), product);
        return Mono.just(product);
    }

    // Find a product by its ID
    public Mono<Product> findById(String id) {
        return Mono.justOrEmpty(productStore.get(id));
    }

    // Retrieve all products
    public Flux<Product> findAll() {
        return Flux.fromIterable(productStore.values());
    }

    // Delete a product by its ID
    public Mono<Void> deleteById(String id) {
        productStore.remove(id);
        return Mono.empty();
    }
}
