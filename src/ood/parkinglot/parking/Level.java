package ood.parkinglot.parking;

import ood.parkinglot.vehicles.Vehicle;
import ood.parkinglot.vehicles.VehicleSize;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Level {

    private final List<ParkingSpot> spots;

    Level(int numOfSpots) {
        List<ParkingSpot> parkingSpots = new ArrayList<>(numOfSpots);
        for (int i = 0; i < numOfSpots; i++) {
            parkingSpots.add(createParkingSpot(i));
        }
        spots = Collections.unmodifiableList(parkingSpots);
    }

    boolean hasSpot(Vehicle v) {
        for (ParkingSpot s : spots) {
            if (s.fit(v)) {
                return true;
            }
        }
        return false;
    }

    boolean park(Vehicle v) {
        for (ParkingSpot s : spots) {
            if (s.fit(v)) {
                s.park(v);
                return true;
            }
        }
        return false;
    }

    boolean leave(Vehicle v) {
        for (ParkingSpot s : spots) {
            if (s.getVehicle() == v) {
                s.leave();
                return true;
            }
        }
        return false;
    }

    private ParkingSpot createParkingSpot(int i) {
        if (i % 3 == 1) {
            return new ParkingSpot(VehicleSize.MediumSize);
        } else if (i % 3 == 2) {
            return new ParkingSpot(VehicleSize.Oversize);
        }

        return new ParkingSpot(VehicleSize.Compact);
    }
}
