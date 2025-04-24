package com.example.api_reactiva.controller;

import com.example.api_reactiva.model.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.BodyInserters.fromValue;

// Integration test for ProductController
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductControllerIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    // Test for creating a product
    @Test
    void createProduct() {
        Product product = new Product("1", "Test Product", 10.0);

        webTestClient.post()
                .uri("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(product), Product.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Product.class)
                .isEqualTo(product);
    }

    // Test for retrieving all products
    @Test
    void getAllProducts() {
        webTestClient.get()
                .uri("/products")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Product.class);
    }

    // Test for retrieving a product by ID
    @Test
    void getProductById() {
        // Create a product first
        Product product = new Product("1", "Test Product", 10.0);
        webTestClient.post()
                .uri("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(product), Product.class)
                .exchange()
                .expectStatus().isCreated();

        // Then retrieve the product by ID
        webTestClient.get()
                .uri("/products/{id}", "1")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Product.class)
                .isEqualTo(product);
    }

    // Test for deleting a product by ID
    @Test
    void deleteProductById() {
        String productId = "1";

        webTestClient.delete()
                .uri("/products/{id}", productId)
                .exchange()
                .expectStatus().isNoContent();
    }
}
