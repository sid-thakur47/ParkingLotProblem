/**********************************************************
 * @Purpose: Parking and unParking the car
 * @Author: Siddhesh Thakur
 * @Date: 22/05/2020
 **********************************************************/
package com.bl.parkinglot.service;

import com.bl.parkinglot.exception.ParkingLotException;
import com.bl.parkinglot.observer.ParkingLotObserver;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.IntStream.range;

public class ParkingLot {

    List<ParkingLot> parkingLotList;
    /**
     * @param: car Car object
     * @param: carList to store list of cars
     * @oaram; observerList to store list of observer
     **/
    private int actualCapacity;
    private List carList;
    private List<ParkingLotObserver> observerList;
    private int flag;
    private Date time;
    private int slot;

    public ParkingLot(int capacity) {
        this.observerList = new ArrayList<>();
        this.carList = new ArrayList<>();
        this.actualCapacity = capacity;
        range( 0, this.actualCapacity ).forEach( i -> carList.add( null ) );
        this.flag = 0;
    }

    //to add type of observers to list
    public void registerObserver(ParkingLotObserver observer) {
        this.observerList.add( observer );
    }

    //to set the capacity of parking lot
    public void setCapacity(int capacity) {
        this.actualCapacity = capacity;
    }

    //getTime
    public Date getParkedTime() {
        return time;
    }
    //seTime
    public void setParkedTime(Date time) {
        this.time = time;
    }

    //to check car is parked
    public boolean isCarPark(Object car) {
        return this.carList.contains( car );
    }

    //to park the car
    public void park(Object car) throws ParkingLotException {
        flag = 1;
        if (isCarPark( car )) {
            throw new ParkingLotException( ParkingLotException.Parking.ALREADY_PARKED );
        }
        if (this.carList.size() == actualCapacity && !carList.contains( null )) {
            checkCapacity();
        }
        slot++;
        carList.add( car );
        park( slot, car );
        Date time = Calendar.getInstance().getTime();
        setParkedTime( time );
    }

    public void park(int slot, Object car) {
        this.carList.set( slot, car );
    }

    //to unPark the car
    public boolean unParkCar(Object car) throws ParkingLotException {
        flag = 0;
        if (this.carList == null) return false;
        if (this.carList.contains( car )) {
            this.carList.set( this.carList.indexOf( car ), null );
            checkCapacity();
            return true;
        }
        return false;
    }

    //check capacity is full or available
    public void checkCapacity() throws ParkingLotException {
        if (flag == 1) {
            for (ParkingLotObserver observer : observerList) {
                observer.capacityIsFull();
            }
            throw new ParkingLotException( ParkingLotException.Parking.PARKING_FULL );
        }
        for (ParkingLotObserver observer : observerList) {
            observer.capacityAvailable();
        }
    }

    //to return empty slots
    public List getSlots() {
        return range( 0, this.actualCapacity )
                .filter( slot -> carList.get( slot ) == null )
                .boxed()
                .collect( Collectors.toList() );
    }

    //findLocation of car
    public int findCarLocation(Object car) throws ParkingLotException {
        if (carList.contains( car )) {
            return carList.indexOf( car );
        }
        throw new ParkingLotException( ParkingLotException.Parking.CAR_NOT_FOUND );
    }
}