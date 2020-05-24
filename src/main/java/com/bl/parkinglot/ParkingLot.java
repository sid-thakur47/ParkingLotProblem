/**********************************************************
 * @Purpose: Parking and unParking the car
 * @Author: Siddhesh Thakur
 * @Date: 22/05/2020
 **********************************************************/
package com.bl.parkinglot;

import java.util.ArrayList;
import java.util.List;

public class ParkingLot {

    /**
     * @param: car Car object
     **/
    private int actualCapacity;
    private List<Object> carList;
    private ParkingLotOwner owner;
    private AirportSecurity security;

    public ParkingLot(int capacity) {
        this.carList = new ArrayList<>();
        this.actualCapacity = capacity;
    }

    public void registerOwner(ParkingLotOwner owner) {
        this.owner = owner;
    }

    public void registerSecurity(AirportSecurity airportSecurity) {
        this.security = airportSecurity;
    }

    public void setCapacity(int capacity) {
        this.actualCapacity = capacity;
    }

    //to park the car
    public boolean isCarPark(Object car) {
        if (this.carList.contains( car )) {
            return true;
        }
        return false;
    }

    public void park(Object car) throws ParkingLotException {
        if (this.carList.size() == actualCapacity) {
            owner.capacityIsFull();
            security.capacityIsFull();
            throw new ParkingLotException( ParkingLotException.Parking.PARKING_FULL );
        }
        this.carList.add( car );
    }

    //to unPark the car
    public boolean unParkCar(Object car) {
        if (this.carList == null) return false;
        if (this.carList.contains( car )) {
            this.carList.remove( car );
            return true;
        }
        return false;
    }
}

