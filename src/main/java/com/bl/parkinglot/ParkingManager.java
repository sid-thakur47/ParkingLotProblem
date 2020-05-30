package com.bl.parkinglot;

import com.bl.parkinglot.exception.ParkingLotException;
import com.bl.parkinglot.model.Vehicle;
import com.bl.parkinglot.service.ParkingLot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ParkingManager {
    List<ParkingLot> parkingLotList;

    public ParkingManager() {
        parkingLotList = new ArrayList<>();
    }

    //add parking lots
    public void addLots(ParkingLot parkingLot) {
        parkingLotList.add( parkingLot );
    }
    //to park cars evenly
    public void parkCar(Vehicle car) throws ParkingLotException {
        Collections.sort( parkingLotList, Comparator.comparing( list -> list.getSlots()
                .size(), Comparator.reverseOrder() ) );
        ParkingLot parkingLot = parkingLotList.get( 0 );
        parkingLot.park( car );
    }
}
