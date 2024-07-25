package com.adepuu.svcorder.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @ColumnDefault("nextval('orders_id_seq'::regclass)")
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @Column(name = "customer_id", nullable = false)
    private Integer customerId;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "order_date")
    private OffsetDateTime orderDate;

    @NotNull
    @Column(name = "total_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount;

    @Size(max = 50)
    @NotNull
    @ColumnDefault("'pending'")
    @Column(name = "status", nullable = false, length = 50)
    private String status;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

}