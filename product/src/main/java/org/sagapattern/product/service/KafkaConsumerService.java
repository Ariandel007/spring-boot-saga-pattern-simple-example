package org.sagapattern.product.service;


import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.sagapattern.commons.dtos.OrderEvent;
import org.sagapattern.product.models.Product;
import org.sagapattern.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumerService {
    @Autowired
    private KafkaTemplate<String, OrderEvent> kafkaTemplate;

    @Autowired
    ProductRepository productRepository;

    @KafkaListener(
            topics = "order-requested",
            groupId = "order-group",
            containerFactory = "kafkaListenerOrderEventContainerFactory"
    )
    public void listenOrderRequested(ConsumerRecord<String, OrderEvent> record) {
        OrderEvent orderEvent = record.value();
        Product product = this.productRepository.findById(orderEvent.getProductId()).get();
        if (product.getStock() >= orderEvent.getQuantity()) {
            try{
                // Update inventory
                product.setStock(product.getStock() - orderEvent.getQuantity());
                this.productRepository.save(product);
                // Send order-confirmed message
                orderEvent.setStatus("CONFIRMED");
                this.kafkaTemplate.send("order-confirmed", orderEvent);

            } catch (Exception e) {
                orderEvent.setStatus("REJECTED");
                this.kafkaTemplate.send("order-rejected", orderEvent);
            }
        } else {
            // Send order-rejected message
            orderEvent.setStatus("REJECTED");
            this.kafkaTemplate.send("order-rejected", orderEvent);
        }
    }

}
