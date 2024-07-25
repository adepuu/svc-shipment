package com.adepuu.svcorder.repository;

import com.adepuu.svcorder.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemsRepository extends JpaRepository<OrderItem, Long> {
}
