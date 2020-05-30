package com.bl.parkinglot.observer;

public class ParkingLotOwner implements ParkingLotObserver {
    private boolean isFullCapacity;

    public void capacityIsFull() {
        isFullCapacity = true;
    }
    @Override
    public void capacityAvailable() {
        isFullCapacity=false;
    }
    public boolean isCapacityFull() {
        return this.isFullCapacity;
    }
}
