package org.edukos.carchargerds.service;

import org.edukos.carchargerds.model.CarChargerStatus;
import org.edukos.carchargerds.model.ChargingEvent;
import org.springframework.stereotype.Service;

@Service
public class CarChargerService {

    private CarChargerStatus currentStatus = CarChargerStatus.INITIAL_STATE;

    public CarChargerStatus getCurrentStatus() {
        return currentStatus;
    }

    public CarChargerStatus processEvent(ChargingEvent event) {
        switch (currentStatus) {
            case INITIAL_STATE:
                if (event == ChargingEvent.POWER_ON) {
                    currentStatus = CarChargerStatus.POWER_PRESENT;
                }
                break;
            case POWER_OFF:
                if (event == ChargingEvent.POWER_ON) {
                    currentStatus = CarChargerStatus.POWER_PRESENT;
                }
                break;
            case POWER_PRESENT:
                if (event == ChargingEvent.READY_TO_CHARGE) {
                    currentStatus = CarChargerStatus.READY_TO_CHARGE;
                } else if (event == ChargingEvent.POWER_OFF) {
                    currentStatus = CarChargerStatus.POWER_OFF;
                }
                break;
            case READY_TO_CHARGE:
                if (event == ChargingEvent.VEHICLE_CONNECTED) {
                    currentStatus = CarChargerStatus.VEHICLE_CONNECTED;
                } else if (event == ChargingEvent.POWER_OFF) {
                    currentStatus = CarChargerStatus.POWER_OFF;
                }
                break;
            case VEHICLE_CONNECTED:
                if (event == ChargingEvent.START_CHARGING) {
                    currentStatus = CarChargerStatus.VEHICLE_CHARGING;
                } else if (event == ChargingEvent.VEHICLE_DISCONNECTED) {
                    currentStatus = CarChargerStatus.READY_TO_CHARGE;
                } else if (event == ChargingEvent.POWER_OFF) {
                    currentStatus = CarChargerStatus.POWER_OFF;
                } else if (event == ChargingEvent.FAILURE) {
                    currentStatus = CarChargerStatus.TROUBLE;
                }
                break;
            case VEHICLE_CHARGING:
                if (event == ChargingEvent.STOP_CHARGING) {
                    currentStatus = CarChargerStatus.VEHICLE_CONNECTED;
                } else if (event == ChargingEvent.VEHICLE_DISCONNECTED) {
                    currentStatus = CarChargerStatus.READY_TO_CHARGE;
                } else if (event == ChargingEvent.POWER_OFF) {
                    currentStatus = CarChargerStatus.POWER_OFF;
                } else if (event == ChargingEvent.FAILURE) {
                    currentStatus = CarChargerStatus.TROUBLE;
                }
                break;
            case TROUBLE:
                if (event == ChargingEvent.POWER_OFF) {
                    currentStatus = CarChargerStatus.POWER_OFF;
                } else if (event == ChargingEvent.POWER_ON) {
                    currentStatus = CarChargerStatus.POWER_PRESENT;
                }
                break;
        }
        return currentStatus;
    }
}