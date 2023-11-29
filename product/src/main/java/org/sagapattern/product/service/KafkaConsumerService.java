package org.sagapattern.product.service;


import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.sagapattern.commons.dtos.OrderEvent;
import org.sagapattern.product.models.Product;
import org.sagapattern.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumerService {

    @Autowired
    ProductRepository productRepository;

    @KafkaListener(
            topics = "stock-update-topic",
            groupId = "order-group",
            containerFactory = "kafkaListenerOrderEventContainerFactory"
    )
    public void consumeOrder(OrderEvent orderEvent) {
        Product product = productRepository
                .findById(orderEvent.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        product.setStock(product.getStock() - orderEvent.getQuantity());
        productRepository.save(product);
    }

}
