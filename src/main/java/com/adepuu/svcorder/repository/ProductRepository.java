package com.adepuu.svcorder.repository;

import com.adepuu.svcorder.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
