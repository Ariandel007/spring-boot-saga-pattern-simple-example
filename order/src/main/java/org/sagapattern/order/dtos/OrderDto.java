package org.sagapattern.order.dtos;

import lombok.Data;

@Data
public class OrderDto {
    private Long Id;
    private Long productId;
    private int quantity;
}
