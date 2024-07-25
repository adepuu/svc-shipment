package com.adepuu.svcorder.repository;

import com.adepuu.svcorder.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
