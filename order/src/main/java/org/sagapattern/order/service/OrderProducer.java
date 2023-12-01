package org.sagapattern.order.service;

import org.sagapattern.commons.dtos.OrderEvent;
import org.sagapattern.order.dtos.OrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderProducer {
    @Autowired
    private KafkaTemplate<String, OrderEvent> kafkaTemplate;

    public void sendOrder(OrderDto order) {
        OrderEvent orderEvent = new OrderEvent();
        orderEvent.setOrderId(order.getId());
        orderEvent.setProductId(order.getProductId());
        orderEvent.setQuantity(order.getQuantity());
        orderEvent.setStatus("PENDING");
        kafkaTemplate.send("order-requested", orderEvent);
    }

}
