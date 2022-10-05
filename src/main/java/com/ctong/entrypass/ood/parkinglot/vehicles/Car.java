package com.ctong.entrypass.ood.parkinglot.vehicles;

public class Car extends Vehicle {

    @Override
    public VehicleSize getSize() {
        return VehicleSize.Compact;
    }
}
