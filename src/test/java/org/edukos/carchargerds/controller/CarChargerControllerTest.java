package org.edukos.carchargerds.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import java.util.List;

import org.edukos.carchargerds.model.CarChargerStatus;
import org.edukos.carchargerds.model.ChargerStateInfo;
import org.edukos.carchargerds.model.ChargingEvent;
import org.edukos.carchargerds.model.EventInfo;
import org.edukos.carchargerds.service.CarChargerService;

class CarChargerControllerTest {

    @Mock
    private CarChargerService carChargerService;

    @InjectMocks
    private CarChargerController carChargerController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getChargingStatus_shouldReturnCurrentStatus() {
        // Arrange
        when(carChargerService.getCurrentStatus()).thenReturn(CarChargerStatus.READY_TO_CHARGE);

        // Act
        CarChargerStatus result = carChargerController.getChargingStatus();

        // Assert
        assertEquals(CarChargerStatus.READY_TO_CHARGE, result);
        verify(carChargerService, times(1)).getCurrentStatus();
    }

    @Test
    void processChargingEvent_shouldProcessEventAndReturnNewStatus() {
        // Arrange
        ChargingEvent event = ChargingEvent.VEHICLE_CONNECTED;
        when(carChargerService.processEvent(event)).thenReturn(CarChargerStatus.VEHICLE_CONNECTED);

        // Act
        CarChargerStatus result = carChargerController.processChargingEvent(event);

        // Assert
        assertEquals(CarChargerStatus.VEHICLE_CONNECTED, result);
        verify(carChargerService, times(1)).processEvent(event);
    }
    // Dashoard interface
    @Test
    void getAllChargerStates_shouldReturnAllPossibleStates() {
        // Act
        List<CarChargerStatus> result = carChargerController.getAllChargerStates();

        // Assert
        assertEquals(7, result.size());
        assertTrue(result.contains(CarChargerStatus.INITIAL_STATE));
        assertTrue(result.contains(CarChargerStatus.POWER_OFF));
        assertTrue(result.contains(CarChargerStatus.POWER_PRESENT));
        assertTrue(result.contains(CarChargerStatus.READY_TO_CHARGE));
        assertTrue(result.contains(CarChargerStatus.VEHICLE_CONNECTED));
        assertTrue(result.contains(CarChargerStatus.VEHICLE_CHARGING));
        assertTrue(result.contains(CarChargerStatus.TROUBLE));
    }
    // Dashoard interface Enhenced
    @Test
    void getAllChargerStatesDetailed_shouldReturnAllStatesWithDetails() {
        // Act
        List<ChargerStateInfo> result = carChargerController.getAllChargerStatesDetailed();

        // Assert
        assertEquals(7, result.size());
        
        // Verify one entry in detail
        ChargerStateInfo readyState = result.stream()
            .filter(s -> s.getId().equals("READY_TO_CHARGE"))
            .findFirst()
            .orElseThrow();
            
        assertEquals("READY_TO_CHARGE", readyState.getId());
        assertEquals("Ready to Charge", readyState.getName());
        assertEquals("Charger is ready for vehicle connection", readyState.getDescription());
    }
    @Test
    void getAllChargingEventsDetailed_shouldReturnAllEventsWithDetails() {
        // Act
        List<EventInfo> result = carChargerController.getAllChargingEventsDetailed();

        // Assert
        assertEquals(8, result.size());
        
        // Verify one entry in detail
        EventInfo startCharging = result.stream()
            .filter(e -> e.getId().equals("start_charging"))
            .findFirst()
            .orElseThrow();
            
        assertEquals("start_charging", startCharging.getId());
        assertEquals("Start Charging", startCharging.getName());
        assertEquals("Charging process begins", startCharging.getDescription());
    }
}