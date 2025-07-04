package org.edukos.carchargerds.integration;

import org.junit.jupiter.api.ClassOrderer.OrderAnnotation;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.edukos.carchargerds.controller.CarChargerController;
import org.edukos.carchargerds.model.CarChargerStatus;
import org.edukos.carchargerds.model.ChargingEvent;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CarChargerIntegrationTest {

    @Autowired
    private CarChargerController carChargerController;

    @Test
    @Order(1)
    void testFullChargingCycle() {
        // Initial state
        assertEquals(CarChargerStatus.INITIAL_STATE, carChargerController.getChargingStatus());

        // Power on
        assertEquals(CarChargerStatus.POWER_PRESENT, 
            carChargerController.processChargingEvent(ChargingEvent.POWER_ON));

        // Ready to charge
        assertEquals(CarChargerStatus.READY_TO_CHARGE,
            carChargerController.processChargingEvent(ChargingEvent.READY_TO_CHARGE));

        // Vehicle connected
        assertEquals(CarChargerStatus.VEHICLE_CONNECTED,
            carChargerController.processChargingEvent(ChargingEvent.VEHICLE_CONNECTED));

        // Start charging
        assertEquals(CarChargerStatus.VEHICLE_CHARGING,
            carChargerController.processChargingEvent(ChargingEvent.START_CHARGING));

        // Stop charging
        assertEquals(CarChargerStatus.VEHICLE_CONNECTED,
            carChargerController.processChargingEvent(ChargingEvent.STOP_CHARGING));

        // Vehicle disconnected
        assertEquals(CarChargerStatus.READY_TO_CHARGE,
            carChargerController.processChargingEvent(ChargingEvent.VEHICLE_DISCONNECTED));

        // Power off
        assertEquals(CarChargerStatus.POWER_OFF,
            carChargerController.processChargingEvent(ChargingEvent.POWER_OFF));
    }

    @Test
    @Order(2)
    void testFailureScenario() {
        // Get to charging state
        carChargerController.processChargingEvent(ChargingEvent.POWER_ON);
        carChargerController.processChargingEvent(ChargingEvent.READY_TO_CHARGE);
        carChargerController.processChargingEvent(ChargingEvent.VEHICLE_CONNECTED);
        carChargerController.processChargingEvent(ChargingEvent.START_CHARGING);

        // Failure occurs
        assertEquals(CarChargerStatus.TROUBLE,
            carChargerController.processChargingEvent(ChargingEvent.FAILURE));
    }
}

