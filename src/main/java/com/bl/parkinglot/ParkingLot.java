/**********************************************************
 * @Purpose: Parking and unParking the car
 * @Author: Siddhesh Thakur
 * @Date: 22/05/2020
 **********************************************************/
package com.bl.parkinglot;

public class ParkingLot {

    /**
     * @param: car Car object
     **/
    private int actualCapacity;
    private int currentCapacity;
    private Object car;
    private ParkingLotOwner owner;

    public ParkingLot(int capacity) {
        this.actualCapacity = capacity;
        this.currentCapacity = 0;
    }

    public void registerOwner(ParkingLotOwner owner) {
        this.owner = owner;
    }

    public void setCapacity(int capacity) {
        this.actualCapacity = capacity;
    }

    //to park the car
    public boolean isCarPark(Object car) {
        if(this.car.equals( car ))
        return true;
        return  false;
    }

    public void park(Object car) throws ParkingLotException {
        if (this.currentCapacity == actualCapacity) {
            owner.capacityIsFull();
            throw new ParkingLotException( ParkingLotException.Parking.PARKING_FULL );
        }
        this.currentCapacity++;
        this.car = car;
    }

    //to unPark the car
    public boolean unParkCar(Object car) {
        if (this.car == null) return false;
        if (this.car.equals( car )) {
            this.car = null;
            return true;
        }
        return false;
    }
}

