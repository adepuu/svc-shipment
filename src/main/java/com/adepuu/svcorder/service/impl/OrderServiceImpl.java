package com.adepuu.svcorder.service.impl;

import com.adepuu.svcorder.dto.CreateOrderDTO;
import com.adepuu.svcorder.entity.Order;
import com.adepuu.svcorder.entity.OrderItem;
import com.adepuu.svcorder.entity.Product;
import com.adepuu.svcorder.exceptions.InsufficientStockException;
import com.adepuu.svcorder.exceptions.ProductNotFoundException;
import com.adepuu.svcorder.repository.OrderItemsRepository;
import com.adepuu.svcorder.repository.OrderRepository;
import com.adepuu.svcorder.repository.ProductRepository;
import com.adepuu.svcorder.service.OrderService;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final OrderItemsRepository orderItemRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public OrderServiceImpl(ProductRepository productRepository,
                            OrderRepository orderRepository,
                            OrderItemsRepository orderItemRepository,
                            KafkaTemplate<String, String> kafkaTemplate) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Transactional
    public Order createOrder(CreateOrderDTO createOrderDTO) {
        // Validate customer exists (assuming a CustomerService exists)
        // CustomerService.validateCustomer(createOrderDTO.getCustomerId());

        // Create new order
        Order order = new Order();
        order.setCustomerId(createOrderDTO.getCustomerId());
        order.setStatus("pending");

        BigDecimal totalAmount = BigDecimal.ZERO;
        List<OrderItem> orderItems = new ArrayList<>();

        for (CreateOrderDTO.OrderItemDTO itemDTO : createOrderDTO.getItems()) {
            Product product = productRepository.findById(itemDTO.getProductId())
                    .orElseThrow(() -> new ProductNotFoundException("Product not found: " + itemDTO.getProductId()));

            // Check stock
            if (product.getStockQuantity() < itemDTO.getQuantity()) {
                throw new InsufficientStockException("Insufficient stock for product: " + product.getName());
            }

            // Create order item
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(itemDTO.getQuantity());
            orderItem.setUnitPrice(product.getPrice());
            orderItem.setSubtotal(product.getPrice().multiply(BigDecimal.valueOf(itemDTO.getQuantity())));

            orderItems.add(orderItem);
            totalAmount = totalAmount.add(orderItem.getSubtotal());

            // Update product stock
            product.setStockQuantity(product.getStockQuantity() - itemDTO.getQuantity());
            productRepository.save(product);
        }

        order.setTotalAmount(totalAmount);
        order = orderRepository.save(order);

        // Save order items
        for (OrderItem item : orderItems) {
            item.setOrder(order);
            orderItemRepository.save(item);
        }

        // Publish Kafka message for shipment service
        publishShipmentMessage(order);

        return order;
    }


    private void publishShipmentMessage(Order order) {
        String message = String.format("{\"orderId\": %d, \"customerId\": %d, \"status\": \"%s\"}",
                order.getId(), order.getCustomerId(), order.getStatus());
        kafkaTemplate.send("order-create", message);
    }
}