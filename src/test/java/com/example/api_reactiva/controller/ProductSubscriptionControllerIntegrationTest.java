package com.example.api_reactiva.controller;

import com.example.api_reactiva.model.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;

// Integration test for ProductSubscriptionController
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductSubscriptionControllerIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    // Test for subscribing to product updates
    @Test
    void subscribeToProductUpdates() {
        Product product = new Product("1", "Test Product", 10.0);

        // Subscribe to updates with increased timeout
        Flux<Product> updates = webTestClient.get()
                .uri("/products/subscribe")
                .accept(MediaType.TEXT_EVENT_STREAM)
                .exchange()
                .expectStatus().isOk()
                .returnResult(Product.class)
                .getResponseBody()
                .timeout(Duration.ofSeconds(15)); // Increased timeout to 15 seconds

        // Publish an update
        webTestClient.post()
                .uri("/products/update")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(product), Product.class)
                .exchange()
                .expectStatus().isOk();

        // Verify the update is received
        StepVerifier.create(updates)
                .expectNext(product)
                .thenCancel()
                .verify();
    }
}
