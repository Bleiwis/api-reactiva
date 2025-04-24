package com.example.api_reactiva.service;

import com.example.api_reactiva.model.Product;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

// Test class for ProductSubscriptionService
class ProductSubscriptionServiceTest {

    private final ProductSubscriptionService subscriptionService = new ProductSubscriptionService();

    /**
     * Test for subscribing to product updates.
     * This test verifies that the subscription correctly receives product updates
     * when they are published.
     */
    @Test
    void subscribeToProductUpdates() {
        Product product1 = new Product("1", "Product 1", 10.0);
        Product product2 = new Product("2", "Product 2", 20.0);

        // Subscribe to updates
        Flux<Product> updates = subscriptionService.subscribeToProductUpdates();

        // Publish updates
        subscriptionService.publishProductUpdate(product1);
        subscriptionService.publishProductUpdate(product2);

        // Verify updates are received
        StepVerifier.create(updates)
                .expectNext(product1)
                .expectNext(product2)
                .thenCancel()
                .verify();
    }
}
