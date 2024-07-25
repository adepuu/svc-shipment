package com.adepuu.svcorder.service.impl;

import com.adepuu.svcorder.service.ShipmentService;
import org.springframework.stereotype.Service;

@Service
public class ShipmentServiceImpl implements ShipmentService {

    public void handleShipmentMessage(String message) {
        System.out.printf("Handling shipment message: %s%n", message);
        // TODO: Handle more details about the shipping logic
    }
}