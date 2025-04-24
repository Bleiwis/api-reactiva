package com.example.api_reactiva.service;

import com.example.api_reactiva.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

// Service to handle product subscriptions and notifications
@Service
public class ProductSubscriptionService {

    private static final Logger logger = LoggerFactory.getLogger(ProductSubscriptionService.class);

    // Sink to emit product updates to subscribers
    private final Sinks.Many<Product> productSink = Sinks.many().multicast().onBackpressureBuffer();

    /**
     * Publishes a product update to all subscribers.
     * This method emits the given product to the sink, which will notify all active subscribers.
     *
     * @param product The product to be published.
     */
    public void publishProductUpdate(Product product) {
        logger.info("Publishing product update: {}", product);
        Sinks.EmitResult result = productSink.tryEmitNext(product);
        if (result.isFailure()) {
            logger.error("Failed to emit product update: {} due to: {}", product, result);
            // Handle the error gracefully, e.g., by logging or notifying an external system
        } else {
            logger.info("Successfully emitted product update: {}", product);
        }
    }

    /**
     * Subscribes to product updates.
     * This method returns a Flux stream of products that subscribers can listen to.
     * Each emitted product represents an update.
     *
     * @return A Flux stream of product updates.
     */
    public Flux<Product> subscribeToProductUpdates() {
        logger.info("New subscription to product updates");
        return productSink.asFlux()
                .doOnNext(product -> logger.info("Emitting product update: {}", product))
                .doOnError(error -> logger.error("Error occurred in product updates stream: {}", error))
                .onErrorResume(error -> {
                    logger.warn("Providing fallback for error: {}", error);
                    return Flux.empty(); // Provide a fallback or alternative stream
                });
    }
}
