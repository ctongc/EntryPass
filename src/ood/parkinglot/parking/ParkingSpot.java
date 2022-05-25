package ood.parkinglot.parking;

import ood.parkinglot.vehicles.Vehicle;
import ood.parkinglot.vehicles.VehicleSize;

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
