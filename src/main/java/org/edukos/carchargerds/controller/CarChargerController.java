package org.edukos.carchargerds.controller;

import org.edukos.carchargerds.model.CarChargerStatus;
import org.edukos.carchargerds.model.ChargingEvent;
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
}