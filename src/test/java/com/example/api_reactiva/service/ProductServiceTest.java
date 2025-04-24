package com.example.api_reactiva.service;

import com.example.api_reactiva.model.Product;
import com.example.api_reactiva.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

// Test class for ProductService
class ProductServiceTest {

    private ProductRepository productRepository;
    private ProductService productService;

    // Set up mocks and the service before each test
    @BeforeEach
    void setUp() {
        productRepository = Mockito.mock(ProductRepository.class);
        productService = new ProductService(productRepository);
    }

    // Test for saving a product
    @Test
    void saveProduct() {
        Product product = new Product("1", "Test Product", 10.0);
        when(productRepository.save(any(Product.class))).thenReturn(Mono.just(product));

        Mono<Product> result = productService.saveProduct(product);

        StepVerifier.create(result)
                .expectNext(product)
                .verifyComplete();

        verify(productRepository, times(1)).save(product);
    }

    // Test for retrieving a product by ID
    @Test
    void getProductById() {
        Product product = new Product("1", "Test Product", 10.0);
        when(productRepository.findById("1")).thenReturn(Mono.just(product));

        Mono<Product> result = productService.getProductById("1");

        StepVerifier.create(result)
                .expectNext(product)
                .verifyComplete();

        verify(productRepository, times(1)).findById("1");
    }

    // Test for retrieving all products
    @Test
    void getAllProducts() {
        Product product1 = new Product("1", "Product 1", 10.0);
        Product product2 = new Product("2", "Product 2", 20.0);
        when(productRepository.findAll()).thenReturn(Flux.just(product1, product2));

        Flux<Product> result = productService.getAllProducts();

        StepVerifier.create(result)
                .expectNext(product1, product2)
                .verifyComplete();

        verify(productRepository, times(1)).findAll();
    }

    // Test for deleting a product by ID
    @Test
    void deleteProductById() {
        when(productRepository.deleteById("1")).thenReturn(Mono.empty());

        Mono<Void> result = productService.deleteProductById("1");

        StepVerifier.create(result)
                .verifyComplete();

        verify(productRepository, times(1)).deleteById("1");
    }
}
