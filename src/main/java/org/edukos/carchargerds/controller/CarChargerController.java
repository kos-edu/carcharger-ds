package org.edukos.carchargerds.controller;

import java.util.Arrays;
import java.util.List;

import org.edukos.carchargerds.model.CarChargerStatus;
import org.edukos.carchargerds.model.ChargerStateInfo;
import org.edukos.carchargerds.model.ChargingEvent;
import org.edukos.carchargerds.model.EventInfo;
import org.edukos.carchargerds.service.CarChargerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/charger")
public class CarChargerController {

    private final CarChargerService carChargerService;

    @Autowired
    public CarChargerController(CarChargerService carChargerService) {
        this.carChargerService = carChargerService;
    }

    @GetMapping("/status")
    public CarChargerStatus getChargingStatus() {
        return carChargerService.getCurrentStatus();
    }

    @PostMapping("/event")
    public CarChargerStatus processChargingEvent(@RequestBody ChargingEvent event) {
        return carChargerService.processEvent(event);
    }   
    // Dashboard Interface
    @GetMapping("/states")
    public List<CarChargerStatus> getAllChargerStates() {
        return Arrays.asList(CarChargerStatus.values());
    }
    @GetMapping("/states/detailed")
    public List<ChargerStateInfo> getAllChargerStatesDetailed() {
        return Arrays.asList(
            new ChargerStateInfo("INITIAL_STATE", "Initial State", "Charger is in its initial state after startup"),
            new ChargerStateInfo("POWER_OFF", "Power Off", "Charger has no power"),
            new ChargerStateInfo("POWER_PRESENT", "Power Present", "Charger has power but not ready to charge"),
            new ChargerStateInfo("READY_TO_CHARGE", "Ready to Charge", "Charger is ready for vehicle connection"),
            new ChargerStateInfo("VEHICLE_CONNECTED", "Vehicle Connected", "Vehicle is connected but not charging"),
            new ChargerStateInfo("VEHICLE_CHARGING", "Vehicle Charging", "Vehicle is actively charging"),
            new ChargerStateInfo("TROUBLE", "Trouble", "Charger has detected a problem")
        );
    }
    @GetMapping("/events/detailed")
    public List<EventInfo> getAllChargingEventsDetailed() {
        return Arrays.asList(
            new EventInfo("power_on", "Power On", "Charger receives power"),
            new EventInfo("ready_to_charge", "Ready to Charge", "Charger is prepared for charging"),
            new EventInfo("vehicle_disconnected", "Vehicle Disconnected", "Vehicle is unplugged"),
            new EventInfo("start_charging", "Start Charging", "Charging process begins"),
            new EventInfo("stop_charging", "Stop Charging", "Charging process stops"),
            new EventInfo("vehicle_connected", "Vehicle Connected", "Vehicle is plugged in"),
            new EventInfo("failure", "Failure", "Charger detected a problem"),
            new EventInfo("power_off", "Power Off", "Charger loses power")
        );
    }
}