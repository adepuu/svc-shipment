package com.adepuu.svcorder.dto;

import lombok.Data;

import java.util.List;

@Data
public class CreateOrderDTO {
    private Integer customerId;
    private List<OrderItemDTO> items;

    @Data
    public static class OrderItemDTO {
        private Long productId;
        private Integer quantity;
    }
}