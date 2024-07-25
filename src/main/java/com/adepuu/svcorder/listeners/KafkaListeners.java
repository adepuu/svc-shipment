package com.adepuu.svcorder.listeners;


import com.adepuu.svcorder.service.ShipmentService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


@Component
public class KafkaListeners {

    private final ShipmentService shipmentService;

    public KafkaListeners(ShipmentService shipmentService) {
        this.shipmentService = shipmentService;
    }

    @KafkaListener(topics = "order-create", groupId = "message-group")
    void handleOrderCreated(String data) {
        System.out.printf("Listener received: %s%n", data);
        shipmentService.handleShipmentMessage(data);
    }
//    Let's say we want to handle cancel order
//    @KafkaListener(topics = "order-cancel", groupId = "message-group")
//    void handleOrderCancelled(String data) {
//        System.out.printf("Listener received: %s%n", data);
//        shipmentService.cancelShipment(data);
//    }
}

