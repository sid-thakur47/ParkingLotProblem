package com.bl.parkinglot;

import com.bl.parkinglot.exception.ParkingLotException;
import com.bl.parkinglot.observer.AirportSecurity;
import com.bl.parkinglot.observer.ParkingLotOwner;
import com.bl.parkinglot.service.ParkingLot;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ParkingLotTest {
    ParkingLot parkingLot;
    ParkingLotOwner owner;
    AirportSecurity airportSecurity;
    ArrayList slotList;
    Object car;
    Object car2;

    @Before
    public void setup() {
        parkingLot = new ParkingLot( 3 );
        car = new Object();
        car2 = new Object();
        owner = new ParkingLotOwner();
        airportSecurity = new AirportSecurity();
        slotList = new ArrayList();
    }

    @Test
    public void given_Car_WhenParked_ShouldReturnTrue() throws ParkingLotException {
        parkingLot.park( car );
        boolean isPark = parkingLot.isCarPark( car );
        Assert.assertTrue( isPark );
    }

    @Test
    public void given_Car_WhenUnParked_ShouldReturnTrue() throws ParkingLotException {
        parkingLot.park( car );
        boolean isUnParked = parkingLot.unParkCar( car );
        Assert.assertTrue( isUnParked );
    }

    @Test
    public void given_Car_WhenAlreadyParked_ShouldReturnFalse() throws ParkingLotException {
        parkingLot.park( car );
        boolean isPark = parkingLot.isCarPark( car2 );
        Assert.assertFalse( isPark );
    }

    @Test
    public void givenUnParkedCar_WhenTryUnPark_ShouldReturnFalse() throws ParkingLotException {
        boolean isPark = parkingLot.unParkCar( car );
        Assert.assertFalse( isPark );
    }

    @Test
    public void givenCar_WhenTryToUnParkDifferentVehicle_ShouldReturnFalse() throws ParkingLotException {
        parkingLot.park( car );
        boolean isPark = parkingLot.unParkCar( car2 );
        Assert.assertFalse( isPark );
    }

    @Test
    public void given_Car_WhenParkingLotFull_ShouldInformOwner() {
        parkingLot.registerObserver( owner );
        try {
            parkingLot.park( car );
            parkingLot.park( new Object() );
        } catch(ParkingLotException e) {
            boolean checkCapacityFull = owner.isCapacityFull();
            Assert.assertTrue( checkCapacityFull );
        }
    }

    @Test
    public void givenWhenCapacity_ShouldAbleToParkAsPerCapacity() {
        parkingLot.setCapacity( 2 );
        try {
            parkingLot.park( car );
            parkingLot.park( car2 );
            boolean isPark1 = parkingLot.isCarPark( car );
            boolean isPark2 = parkingLot.isCarPark( car2 );
            Assert.assertTrue( isPark1 );
            Assert.assertTrue( isPark2 );
        } catch(ParkingLotException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void given_Car_WhenParkingLotFull_ShouldInformAirportSecurity() {
        AirportSecurity airportSecurity = new AirportSecurity();
        parkingLot.registerObserver( airportSecurity );
        try {
            parkingLot.park( car );
            parkingLot.park( new Object() );
        } catch(ParkingLotException e) {
            boolean checkCapacityFull = airportSecurity.isCapacityFull();
            Assert.assertTrue( checkCapacityFull );
        }
    }
    @Test
    public void given_Car_WhenParkingLotFull_ShouldInformObservers() {
        parkingLot.registerObserver( owner );
        parkingLot.registerObserver( airportSecurity );
        try {
            parkingLot.park( car );
            parkingLot.park( new Object() );
        } catch(ParkingLotException e) {
            boolean checkCapacityFullOwner = owner.isCapacityFull();
            boolean checkCapacityFullSecurity = airportSecurity.isCapacityFull();
            Assert.assertTrue( checkCapacityFullOwner && checkCapacityFullSecurity );
        }
    }
    @Test
    public void given_Car_ParkingSameAgain_ShouldReturnException() {
        parkingLot.registerObserver( owner );
        try {
            parkingLot.park( car );
            parkingLot.park( car );
        } catch(ParkingLotException e) {
            Assert.assertEquals( ParkingLotException.Parking.ALREADY_PARKED, e.error );
        }
    }

    @Test
    public void givenWhen_ParkingSpaceIsAvailableAfterFull_Owner_ShouldReturnTrue() throws ParkingLotException {
        parkingLot.registerObserver( owner );
        try {
            parkingLot.park( car );
            parkingLot.park( car2 );
        } catch(ParkingLotException e) {
            parkingLot.unParkCar( car );
            boolean checkFull = owner.isCapacityFull();
            Assert.assertFalse( checkFull );
        }
    }
    @Test
    public void givenWhen_ParkingSpaceIsAvailableAfterFull_Security_ShouldReturnTrue() throws ParkingLotException {
        parkingLot.registerObserver( airportSecurity );
        try {
            parkingLot.park( car );
            parkingLot.park( car2 );
        } catch(ParkingLotException e) {
            parkingLot.unParkCar( car );
            boolean checkFull = airportSecurity.isCapacityFull();
            Assert.assertFalse( checkFull );
        }
    }

    @Test
    public void givenParkingLotSystem_WhenListOfEmptySlotsCalled_ShouldReturnAvailableSlots() throws ParkingLotException {
        Object car3 = new Object();
        parkingLot = new ParkingLot( 4 );
        slotList.add( 0 );
        slotList.add( 1 );
        slotList.add( 2 );
        parkingLot.park( 1, car );
        parkingLot.park( 2, car2 );
        parkingLot.park( 3, car3 );
        parkingLot.unParkCar( car );
        parkingLot.unParkCar( car2 );
        List emptySlot = parkingLot.getSlots();
        Assert.assertEquals( slotList, emptySlot );
    }
    @Test
    public void given_Car_WhenParkedShouldFindItsLocation() throws ParkingLotException {
        parkingLot.park( 1, car );
        parkingLot.park( 2, car2 );
        int vehicleLocation = parkingLot.findCarLocation( car2 );
        Assert.assertEquals( vehicleLocation, 2 );
    }
    @Test
    public void given_Car_WhenParked_AndQueriedForLocation_ShouldThrowException() {
        try {
            parkingLot.park( car );
            parkingLot.findCarLocation( car2 );
        } catch (ParkingLotException e) {
            Assert.assertEquals( ParkingLotException.Parking.CAR_NOT_FOUND, e.error );
        }
    }

    @Test
    public void given_ParkedCar_WhenObserverIsOwner_ShouldReturnItsParkingTime() throws ParkingLotException {
        parkingLot.registerObserver( owner );
        parkingLot.park( car );
        Date time = Calendar.getInstance().getTime();
        Assert.assertEquals( time, parkingLot.getParkedTime() );
    }
}