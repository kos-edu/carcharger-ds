package org.edukos.carchargerds.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import org.edukos.carchargerds.model.CarChargerStatus;
import org.edukos.carchargerds.model.ChargingEvent;
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
}