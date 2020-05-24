package com.bl.parkinglot.observer;

public class AirportSecurity implements ParkingLotObserver {

    private boolean isFullCapacity;

    @Override
    public void capacityIsFull() {
        isFullCapacity = true;
    }
    @Override
    public void capacityAvailable() {
        isFullCapacity = false;
    }

    public boolean isCapacityFull() {
        return this.isFullCapacity;
    }
}
