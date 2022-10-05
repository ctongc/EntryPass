package com.ctong.entrypass.ood.parkinglot.parking;

import com.ctong.entrypass.ood.parkinglot.vehicles.VehicleSize;
import com.ctong.entrypass.ood.parkinglot.vehicles.Vehicle;

public class ParkingSpot {

    private final VehicleSize size;
    private Vehicle currentVehicle;

    ParkingSpot(VehicleSize size) {
        this.size = size;
    }

    boolean fit(Vehicle v) {
        return currentVehicle == null && size.get() >= v.getSize().get();
    }

    /**
     * record a vehicle is parked in by updating the currentVehicle field
     */
    void park(Vehicle v) {
        currentVehicle = v;
    }

    void leave() {
        currentVehicle = null;
    }

    Vehicle getVehicle() {
        return currentVehicle;
    }
}
