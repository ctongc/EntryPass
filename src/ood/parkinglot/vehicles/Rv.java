package ood.parkinglot.vehicles;

public class Rv extends Vehicle {

    @Override
    public VehicleSize getSize() {
        return VehicleSize.Oversize;
    }
}
