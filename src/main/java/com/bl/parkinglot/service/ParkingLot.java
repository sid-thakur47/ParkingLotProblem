/**********************************************************
 * @Purpose: Parking and unParking the car
 * @Author: Siddhesh Thakur
 * @Date: 22/05/2020
 **********************************************************/
package com.bl.parkinglot.service;

import com.bl.parkinglot.exception.ParkingLotException;
import com.bl.parkinglot.model.Vehicle;
import com.bl.parkinglot.observer.ParkingLotObserver;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.IntStream.range;

public class ParkingLot {

    /**
     * @param: car Car object
     * @param: carList to store list of cars
     * @param: observerList to store list of observer
     * @param: parkingLotList to store list of parking lots
     * @param: to get parking time
     * @param: to set car to specific slot
     **/

    private int actualCapacity;
    private List<Vehicle> carList;
    private List<ParkingLotObserver> observerList;
    private List<ParkingLot> parkingLotList;
    private int flag;
    private Date time;
    private int slot;

    public ParkingLot(int capacity) {
        this.carList = new ArrayList<>();
        this.observerList = new ArrayList<>();
        this.parkingLotList = new ArrayList<>();
        this.actualCapacity = capacity;
        this.parkingLotList = new ArrayList<>();
        range( 0, this.actualCapacity ).forEach( i -> carList.add( null ) );
        this.flag = 0;
    }
    public void setActualCapacity(int actualCapacity) {
        this.actualCapacity = actualCapacity;
    }
    //to add type of observers to list
    public void registerObserver(ParkingLotObserver observer) {
        this.observerList.add( observer );
    }
    //getTime
    public Date getParkedTime() {
        return time;
    }
    //seTime
    public void setParkedTime(Date time) {
        this.time = time;
    }

    //add parking lots
    public void addLots(ParkingLot parkingLot) {
        parkingLotList.add( parkingLot );
    }

    //to check car is parked
    public boolean isCarPark(Vehicle car) {
        return this.carList.contains( car );
    }

    //to park the car
    public void park(Vehicle car) throws ParkingLotException {
        flag = 1;
        if(isCarPark( car )) {
            throw new ParkingLotException( ParkingLotException.Parking.ALREADY_PARKED );
        }
        if(carList.size() == actualCapacity && !carList.contains( null )) {
            checkCapacity();
        }
        park( slot++, car );
        Date time = Calendar.getInstance().getTime();
        setParkedTime( time );
    }

    public void park(int slot, Vehicle car) {
        this.carList.set( slot, car );
    }

    //to unPark the car
    public boolean unParkCar(Vehicle car) throws ParkingLotException {
        flag = 0;
        if(this.carList == null) return false;
        if(this.carList.contains( car )) {
            this.carList.set( this.carList.indexOf( car ), null );
            checkCapacity();
            return true;
        }
        return false;
    }

    //check capacity is full or available
    public void checkCapacity() throws ParkingLotException {
        if(flag == 1) {
            for(ParkingLotObserver observer : observerList) {
                observer.capacityIsFull();
            }
            throw new ParkingLotException( ParkingLotException.Parking.PARKING_FULL );
        }
        for(ParkingLotObserver observer : observerList) {
            observer.capacityAvailable();
        }
    }

    //to return empty slots
    public List<Integer> getSlots() {
        List<Integer> list;
        list = range( 0, this.actualCapacity ).filter( i -> carList.get( i ) == null )
                .boxed().collect( Collectors.toList() );
        return list;
    }

    //findLocation of car
    public int findCarLocation(Vehicle car) throws ParkingLotException {
        if(!carList.contains( car )) {
            throw new ParkingLotException( ParkingLotException.Parking.CAR_NOT_FOUND );
        } else {
            return carList.indexOf( car );
        }
    }

    //get parking lot in which car is parked
    public ParkingLot getCarLot(Vehicle car) {
        for(ParkingLot parkingLot : parkingLotList) {
            if(parkingLot.isCarPark( car )) {
                return parkingLot;
            }
        }
        return null;
    }
}