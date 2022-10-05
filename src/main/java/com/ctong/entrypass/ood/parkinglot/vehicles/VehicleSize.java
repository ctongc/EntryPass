package com.ctong.entrypass.ood.parkinglot.vehicles;

public enum VehicleSize {
    Compact(1),
    MediumSize(2),
    Oversize(3);

    private final int size;

    VehicleSize(int size) {
        this.size = size;
    }

    public int get() {
        return size;
    }
}
