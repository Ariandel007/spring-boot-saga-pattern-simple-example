package org.sagapattern.order.service;

import org.sagapattern.order.dtos.OrderDto;
import org.sagapattern.order.models.Order;
import org.sagapattern.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    OrderProducer orderProducer;

    @Autowired
    OrderRepository orderRepository;

    public OrderDto createOrder(OrderDto orderDto) {
        Order orderToSave = Order.builder()
                        .id(orderDto.getId())
                        .productId(orderDto.getProductId())
                        .quantity(orderDto.getQuantity()).build();
        orderRepository.save(orderToSave);
        orderProducer.sendOrder(orderDto);
        return orderDto;
    }

    public Optional<Order> getOrder(Long orderId) {
        return this.orderRepository.findById(orderId);
    }
}
