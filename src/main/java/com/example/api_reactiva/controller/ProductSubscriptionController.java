package com.example.api_reactiva.controller;

import com.example.api_reactiva.model.Product;
import com.example.api_reactiva.service.ProductSubscriptionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

// Controller to handle product subscriptions and notifications
@RestController
@RequestMapping("/products")
public class ProductSubscriptionController {

    private final ProductSubscriptionService subscriptionService;
    private static final Logger logger = LoggerFactory.getLogger(ProductSubscriptionController.class);

    public ProductSubscriptionController(ProductSubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    // Endpoint to subscribe to product updates using Server-Sent Events (SSE)
    @GetMapping(value = "/subscribe", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Product> subscribeToProductUpdates() {
        logger.info("Client subscribed to product updates");
        return subscriptionService.subscribeToProductUpdates();
    }

    // Endpoint to publish a product update (for testing purposes)
    @PostMapping("/update")
    public Mono<Void> publishProductUpdate(@RequestBody Product product) {
        logger.info("Received request to publish product update: {}", product);
        subscriptionService.publishProductUpdate(product);
        return Mono.empty();
    }
}
