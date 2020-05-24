/**********************************************************
 * @Purpose: Parking and unParking the car
 * @Author: Siddhesh Thakur
 * @Date: 22/05/2020
 **********************************************************/
package com.bl.parkinglot.service;

import com.bl.parkinglot.exception.ParkingLotException;
import com.bl.parkinglot.observer.ParkingLotObserver;

import java.util.ArrayList;
import java.util.List;

public class ParkingLot {

    /**
     * @param: car Car object
     **/
    private int actualCapacity;
    private List<Object> carList;
    private List<ParkingLotObserver> observerList;

    public ParkingLot(int capacity) {
        this.observerList = new ArrayList<>();
        this.carList = new ArrayList<>();
        this.actualCapacity = capacity;
    }

    public void registerObserver(ParkingLotObserver observer) {
        this.observerList.add( observer );
    }

    public void setCapacity(int capacity) {
        this.actualCapacity = capacity;
    }

    //to park the car
    public boolean isCarPark(Object car) {
        return this.carList.contains( car );
    }

    public void park(Object car) throws ParkingLotException {
        if (this.carList.size() == actualCapacity) {
            for (ParkingLotObserver observer : observerList) {
                observer.capacityIsFull();
            }
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

