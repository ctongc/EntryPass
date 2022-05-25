package ood.parkinglot;

import ood.parkinglot.parking.ParkingLot;
import ood.parkinglot.vehicles.Car;
import ood.parkinglot.vehicles.Rv;
import ood.parkinglot.vehicles.Truck;
import ood.parkinglot.vehicles.Vehicle;

import java.util.ArrayList;
import java.util.List;

public class Control {

    public static void main(String[] args) {
        ParkingLot lot = new ParkingLot(4,10);
        List<Vehicle> vehicles = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            final Vehicle v = createVehicle(i);
            vehicles.add(v);
            boolean hasSpot = lot.hasSpot(v);
            if (i < 40) {
                // make sure you enable assert if using it for test
                assert hasSpot;
                assert lot.park(v);
            } else {
                assert !hasSpot;
                assert !lot.park(v);
            }
        }
        assert vehicles.size() == 50;
        int i = 0;
        for (Vehicle v : vehicles) {
            assert i >= 40 || lot.leave(v);
            i++;
        }
    }

    private static Vehicle createVehicle(int i) {
        if (i % 3 == 1) {
            return new Truck();
        } else if (i % 3 == 2) {
            return new Rv();
        }
        return new Car();
    }
}
