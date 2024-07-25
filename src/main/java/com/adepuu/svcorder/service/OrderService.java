package com.adepuu.svcorder.service;

import com.adepuu.svcorder.dto.CreateOrderDTO;
import com.adepuu.svcorder.entity.Order;

public interface OrderService {
    Order createOrder(CreateOrderDTO createOrderDTO);
}
