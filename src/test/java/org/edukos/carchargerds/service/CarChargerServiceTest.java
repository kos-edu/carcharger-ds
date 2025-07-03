package org.edukos.carchargerds.service;


import org.edukos.carchargerds.model.CarChargerStatus;
import org.edukos.carchargerds.model.ChargingEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CarChargerServiceTest {

    private CarChargerService carChargerService;

    @BeforeEach
    void setUp() {
        carChargerService = new CarChargerService();
    }

    @Test
    void getCurrentStatus_shouldReturnInitialStateByDefault() {
        assertEquals(CarChargerStatus.INITIAL_STATE, carChargerService.getCurrentStatus());
    }

    @ParameterizedTest
    @MethodSource("provideStateTransitions")
    void processEvent_shouldTransitionStatesCorrectly(
            CarChargerStatus initialState,
            ChargingEvent event,
            CarChargerStatus expectedState) {
        
        // ISTQB STATE TRANSITION BASED TESTING
    	
    	// TEST t_power_on (from INITIAL STATE to POWER PRESENT) tested
        // ... Set initial state : POWER_ON
        carChargerService.processEvent(ChargingEvent.POWER_ON); // To get out of INITIAL_STATE to POWER PRESENT
        
        
        // Set to desired initial state: POWER_OFF
        if (initialState == CarChargerStatus.POWER_OFF) {
            // Add logic to reach desired initial state
            // This would depend on your state machine implementation
            // For simplicity, we'll just set directly in this example
            // In a real test, you'd need to send the proper sequence of events
            carChargerService.processEvent(ChargingEvent.POWER_OFF);
            // etc...
        }
        
        // Set to desired initial state: POWER PRESENT (already is!)
        if (initialState == CarChargerStatus.POWER_PRESENT) {
            // Add logic to reach desired initial state
            // This would depend on your state machine implementation
            // For simplicity, we'll just set directly in this example
            // In a real test, you'd need to send the proper sequence of events
            ; // carChargerService.processEvent(ChargingEvent.POWER_OFF);
            // etc...
        }
        
        // Set to desired initial state: POWER PRESENT 
        if (initialState == CarChargerStatus.READY_TO_CHARGE) {
            // Add logic to reach desired initial state
            // This would depend on your state machine implementation
            // For simplicity, we'll just set directly in this example
            // In a real test, you'd need to send the proper sequence of events
            carChargerService.processEvent(ChargingEvent.POWER_OFF);
            carChargerService.processEvent(ChargingEvent.POWER_ON);
            carChargerService.processEvent(ChargingEvent.READY_TO_CHARGE);
            // etc...
        }
        
        // Set to desired initial state: VEHICLE CONNECTED (already is!)
        if (initialState == CarChargerStatus.VEHICLE_CONNECTED) {
            // Add logic to reach desired initial state
            // This would depend on your state machine implementation
            // For simplicity, we'll just set directly in this example
            // In a real test, you'd need to send the proper sequence of events
            carChargerService.processEvent(ChargingEvent.POWER_OFF);
            carChargerService.processEvent(ChargingEvent.POWER_ON);
            carChargerService.processEvent(ChargingEvent.READY_TO_CHARGE);
            carChargerService.processEvent(ChargingEvent.VEHICLE_CONNECTED);
        }
        // Set to desired initial state: VEHICLE CHARGING (already is!)
        if (initialState == CarChargerStatus.VEHICLE_CHARGING) {
            // Add logic to reach desired initial state
            // This would depend on your state machine implementation
            // For simplicity, we'll just set directly in this example
            // In a real test, you'd need to send the proper sequence of events
            carChargerService.processEvent(ChargingEvent.POWER_OFF);
            carChargerService.processEvent(ChargingEvent.POWER_ON);
            carChargerService.processEvent(ChargingEvent.READY_TO_CHARGE);
            carChargerService.processEvent(ChargingEvent.VEHICLE_CONNECTED);
            carChargerService.processEvent(ChargingEvent.START_CHARGING);
        }
        
        // Act
        CarChargerStatus result = carChargerService.processEvent(event);

        // Assert
        assertEquals(expectedState, result);
    }

    private static Stream<Arguments> provideStateTransitions() {
        return Stream.of(
            // From POWER_PRESENT
            /* t: ready to charge*/ Arguments.of(CarChargerStatus.POWER_PRESENT, ChargingEvent.READY_TO_CHARGE, CarChargerStatus.READY_TO_CHARGE),
            /* t: power off      */ Arguments.of(CarChargerStatus.POWER_PRESENT, ChargingEvent.POWER_OFF, CarChargerStatus.POWER_OFF),
            
            // From READY_TO_CHARGE
            /* t: vehicle_connected*/ Arguments.of(CarChargerStatus.READY_TO_CHARGE, ChargingEvent.VEHICLE_CONNECTED, CarChargerStatus.VEHICLE_CONNECTED),
            /* t: power off        */ Arguments.of(CarChargerStatus.READY_TO_CHARGE, ChargingEvent.POWER_OFF, CarChargerStatus.POWER_OFF) ,
            
            // From VEHICLE_CONNECTED
            /* t: start_charging      */ Arguments.of(CarChargerStatus.VEHICLE_CONNECTED, ChargingEvent.START_CHARGING, CarChargerStatus.VEHICLE_CHARGING) ,
            /* t: vehicle_disconnected*/ Arguments.of(CarChargerStatus.VEHICLE_CONNECTED, ChargingEvent.VEHICLE_DISCONNECTED, CarChargerStatus.READY_TO_CHARGE),
            /* t: failure             */ Arguments.of(CarChargerStatus.VEHICLE_CONNECTED, ChargingEvent.FAILURE, CarChargerStatus.TROUBLE) ,
            /* t: power off           */ Arguments.of(CarChargerStatus.VEHICLE_CONNECTED, ChargingEvent.POWER_OFF, CarChargerStatus.POWER_OFF) ,
            
            // From VEHICLE_CHARGING
            /* t: stop_charging       */ Arguments.of(CarChargerStatus.VEHICLE_CHARGING, ChargingEvent.STOP_CHARGING, CarChargerStatus.VEHICLE_CONNECTED),
            /* t: failure             */ Arguments.of(CarChargerStatus.VEHICLE_CHARGING, ChargingEvent.FAILURE, CarChargerStatus.TROUBLE),
            /* t: power off           */ Arguments.of(CarChargerStatus.VEHICLE_CHARGING, ChargingEvent.POWER_OFF, CarChargerStatus.POWER_OFF) ,
            
            // From TROUBLE
            /* t: power on       */ Arguments.of(CarChargerStatus.TROUBLE, ChargingEvent.POWER_ON, CarChargerStatus.POWER_PRESENT),
            /* t: power off      */ Arguments.of(CarChargerStatus.TROUBLE, ChargingEvent.POWER_OFF, CarChargerStatus.POWER_OFF)
            );
    }

    @Test
    void processEvent_withInvalidTransition_shouldRemainInCurrentState() {
        // Arrange - start in READY_TO_CHARGE state
        carChargerService.processEvent(ChargingEvent.POWER_ON);
        carChargerService.processEvent(ChargingEvent.READY_TO_CHARGE);
        
        // Act - try invalid transition
        CarChargerStatus result = carChargerService.processEvent(ChargingEvent.START_CHARGING);
        
        // Assert - should remain in READY_TO_CHARGE
        assertEquals(CarChargerStatus.READY_TO_CHARGE, result);
    }
}