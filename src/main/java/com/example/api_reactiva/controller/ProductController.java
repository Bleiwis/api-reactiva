package com.example.api_reactiva.controller;

import com.example.api_reactiva.model.Product;
import com.example.api_reactiva.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

// Annotation to define this class as a REST controller
@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    // Constructor injection for the service
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // Endpoint to create a new product
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Product> createProduct(@RequestBody Product product) {
        return productService.saveProduct(product);
    }

    // Endpoint to get a product by ID
    @GetMapping("/{id}")
    public Mono<ResponseEntity<Product>> getProductById(@PathVariable String id) {
        return productService.getProductById(id)
                .map(product -> ResponseEntity.ok(product))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    // Endpoint to get all products
    @GetMapping
    public Flux<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    // Endpoint to delete a product by ID
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteProductById(@PathVariable String id) {
        return productService.deleteProductById(id);
    }
}
