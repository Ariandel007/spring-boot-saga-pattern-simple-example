package org.sagapattern.order.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.sagapattern.commons.dtos.OrderEvent;
import org.sagapattern.order.models.Order;
import org.sagapattern.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class OrderRejectedListener {
    @Autowired
    OrderRepository orderRepository;


    @KafkaListener(topics = "order-rejected",
            groupId = "order-group",
            containerFactory = "kafkaListenerOrderEventContainerFactory"
    )
    public void listenOrderRejected(ConsumerRecord<String, OrderEvent> record) {
        OrderEvent orderEvent = record.value();
        Order order = this.orderRepository.findById(orderEvent.getOrderId()).get();
        // Update order status to REJECTED
        order.setStatus("REJECTED");
        this.orderRepository.save(order);
        // Notify customer about the order rejection
        // You can use any method you prefer, such as email, SMS, etc.
        // For simplicity, we just print a message to the console
        System.out.println("Order rejected: " + order);
    }
}
