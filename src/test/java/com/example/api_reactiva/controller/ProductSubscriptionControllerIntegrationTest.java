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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductSubscriptionControllerIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void subscribeToProductUpdates() {
        // Arrange
        Product product = new Product("1", "Test Product", 10.0);

        // Act: Subscribe FIRST
        Flux<Product> updates = webTestClient.get()
                .uri("/products/subscribe")
                .accept(MediaType.TEXT_EVENT_STREAM)
                .exchange()
                .expectStatus().isOk()
                .returnResult(Product.class)
                .getResponseBody()
                .timeout(Duration.ofSeconds(5)); // Reduced timeout for initial subscription

        // Assert: Verify subscription is established
        StepVerifier.create(updates)
                .expectSubscription()
                .thenAwait(Duration.ofMillis(100)) // Give time to establish
                .thenCancel()
                .verify(Duration.ofSeconds(5));

        // Act: Publish update
        webTestClient.post()
                .uri("/products/update")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(product), Product.class)
                .exchange()
                .expectStatus().isOk();

        // Assert: Verify update is received
        StepVerifier.create(webTestClient.get()  // Re-subscribe to get the update
                                .uri("/products/subscribe")
                                .accept(MediaType.TEXT_EVENT_STREAM)
                                .exchange()
                                .expectStatus().isOk()
                                .returnResult(Product.class)
                                .getResponseBody()
                )
                .expectNext(product)
                .expectComplete()
                .verify(Duration.ofSeconds(5));
    }
}