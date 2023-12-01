package org.sagapattern.order.controllers;

import jakarta.validation.Valid;
import org.aspectj.weaver.ast.Or;
import org.sagapattern.order.dtos.OrderDto;
import org.sagapattern.order.models.Order;
import org.sagapattern.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping(value = "")
    public ResponseEntity<OrderDto> createOrder(
            @Valid @RequestBody OrderDto order
    ) {
        return ResponseEntity.ok().body(this.orderService.createOrder(order));
    }

    @GetMapping(value = "/{idProduct}")
    public ResponseEntity<Order> getOrder(@PathVariable Long idProduct) {
        return ResponseEntity.ok().body(this.orderService.getOrder(idProduct).get());
    }

}
