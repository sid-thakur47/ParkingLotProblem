package com.bl.parkinglot;

import com.bl.parkinglot.constant.DriverType;
import com.bl.parkinglot.exception.ParkingLotException;
import com.bl.parkinglot.model.Vehicle;
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

import static org.junit.Assert.assertEquals;

public class ParkingLotTest implements DriverType {

    ParkingLot parkingLot;
    ParkingLotOwner owner;
    AirportSecurity airportSecurity;
    ParkingManager parkingManager;
    ArrayList slotList;
    Vehicle car;
    Vehicle car2;
    Vehicle car3;
    Vehicle car4;

    @Before
    public void setup() {
        parkingLot = new ParkingLot( 3 );
        car = new Vehicle();
        car2 = new Vehicle();
        car3 = new Vehicle();
        car4 = new Vehicle();
        owner = new ParkingLotOwner();
        airportSecurity = new AirportSecurity();
        parkingManager = new ParkingManager();
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
            parkingLot.park( car2 );
        } catch(ParkingLotException e) {
            boolean checkCapacityFull = owner.isCapacityFull();
            Assert.assertTrue( checkCapacityFull );
        }
    }

    @Test
    public void givenWhenCapacity_ShouldAbleToParkAsPerCapacity() {
        ParkingLot parkingLot = new ParkingLot( 5 );
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
            parkingLot.park( car2 );
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
            parkingLot.park( car2 );
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

            assertEquals( ParkingLotException.Parking.ALREADY_PARKED, e.error );
        }
    }

    @Test
    public void givenWhen_ParkingSpaceIsAvailableAfterFull_Owner_ShouldReturnTrue() throws ParkingLotException {
        parkingLot = new ParkingLot( 2 );
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
        ParkingLot parkingLot = new ParkingLot( 2 );
        parkingLot.addLots( parkingLot );
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
    public void givenParkingLot_WhenCarParked_ShouldReturnEmptySlots() throws ParkingLotException {
        Vehicle car3 = new Vehicle();
        parkingLot = new ParkingLot( 4 );
        parkingLot.park( 1, car );
        parkingLot.park( 2, car2 );
        parkingLot.park( 3, car3 );
        parkingLot.unParkCar( car );
        parkingLot.unParkCar( car2 );
        List emptySlot = parkingLot.getSlots();
        assertEquals( 3, emptySlot.size() );
    }

    @Test
    public void given_Car_WhenParkedShouldFindItsLocation() throws ParkingLotException {
        ParkingLot parkingLot = new ParkingLot( 3 );
        parkingLot.park( car2 );
        parkingLot.park( car );
        int vehicleLocation = parkingLot.findCarLocation( car2 );
        assertEquals( vehicleLocation, 0 );
    }

    @Test
    public void given_Car_WhenParked_AndQueriedForLocationForAnotherCar_ShouldThrowException() {
        try {
            parkingLot.park( car );
            parkingLot.findCarLocation( car2 );
        } catch(ParkingLotException e) {
            assertEquals( ParkingLotException.Parking.CAR_NOT_FOUND, e.error );
        }
    }

    @Test
    public void given_ParkedCar_WhenObserverIsOwner_ShouldReturnItsParkingTime() throws ParkingLotException {
        parkingLot.registerObserver( owner );
        parkingLot.park( car );
        Date time = Calendar.getInstance().getTime();
        assertEquals( time, parkingLot.getParkedTime() );
    }

    @Test
    public void given_ParkedCar_WhenObserverIsSecurity_ShouldReturnItsParkingTime() throws ParkingLotException {
        parkingLot.registerObserver( airportSecurity );
        parkingLot.park( car );
        Date time = Calendar.getInstance().getTime();
        assertEquals( time, parkingLot.getParkedTime() );
    }
    //9
    @Test
    public void givenParkingLots_CarShouldGetParkedInAssignedParkingLot() throws ParkingLotException {
        ParkingLot parkingLot = new ParkingLot( 4 );
        ParkingLot parkingLot1 = new ParkingLot( 4 );
        parkingLot.addLots( parkingLot );
        parkingLot1.addLots( parkingLot1 );
        parkingLot.park( car );
        parkingLot1.park( car2 );
        Object firstParkingLot = parkingLot.getCarLot( car );
        Object secondParkingLot = parkingLot1.getCarLot( car2 );
        assertEquals( parkingLot, firstParkingLot );
        assertEquals( parkingLot1, secondParkingLot );
    }

    @Test
    public void givenMultipleCarsAndLots_WhenParkEvenly_shouldReturnEmptyParkingLot() throws ParkingLotException {
        ParkingLot parkingLot1 = new ParkingLot( 4 );
        ParkingLot parkingLot2 = new ParkingLot( 4 );
        ParkingLot parkingLot3 = new ParkingLot( 4 );
        ParkingLot parkingLot4 = new ParkingLot( 4 );
        parkingManager.addLots( parkingLot1 );
        parkingManager.addLots( parkingLot2 );
        parkingManager.addLots( parkingLot3 );
        parkingManager.addLots( parkingLot4 );
        parkingManager.parkCar( car );
        parkingManager.parkCar( car2 );
        parkingManager.parkCar( car3 );
        parkingManager.parkCar( car4 );
        assertEquals( parkingLot2.getSlots().size(), 3 );
        assertEquals( parkingLot1.getSlots().size(), 3 );
    }
    //10
    @Test
    public void givenCarToPark_WhenDriverIsHandCapped_ShouldParkAtNearestLocation() throws ParkingLotException {
        ParkingLot parkingLot = new ParkingLot( 5 );
        parkingLot.park( car, Driver.NORMAL );
        parkingLot.park( car2, Driver.HANDICAPPED );
        parkingLot.park( car3, Driver.NORMAL );
        parkingLot.park( car4, Driver.HANDICAPPED );
        int nearestLocation = parkingLot.findCarLocation( car2 );
        int nearestLocation2 = parkingLot.findCarLocation( car4 );
        assertEquals( 0, nearestLocation );
        assertEquals( 1, nearestLocation2 );
    }
    //11
    @Test
    public void givenLargeCar_WhenParked_ShouldParkedAtNearestLocation() throws ParkingLotException {
        ParkingLot parkingLot1 = new ParkingLot( 4 );
        ParkingLot parkingLot = new ParkingLot( 4 );
        parkingManager.addLots( parkingLot );
        parkingManager.addLots( parkingLot1 );
        parkingManager.parkCars( car, Driver.LARGE_CAR, Driver.NORMAL );
        parkingManager.parkCar( car2 );
        parkingManager.parkCar( car3 );
        parkingManager.parkCar( car4 );
        int firstParkingLot = parkingLot.getSlots().size();
        int secondParkingLot = parkingLot1.getSlots().size();
        int findLocation = parkingLot.findCarLocation( car );
        assertEquals( 0, findLocation );
        assertEquals( 2, firstParkingLot );
        assertEquals( 2, secondParkingLot );
    }

    //12
    @Test
    public void givenCarColor_WhenFindCarAccordingToColor_ShouldReturnCarAccordingToColor() throws ParkingLotException {
        parkingManager.addLots( parkingLot );
        Vehicle car1 = new Vehicle( "White" );
        Vehicle car2 = new Vehicle( "Black" );
        parkingManager.parkCars( car1, Driver.SMALL_CAR, Driver.NORMAL );
        parkingManager.parkCars( car2, Driver.SMALL_CAR, Driver.NORMAL );
        List vehicleColor = parkingLot.getCar( "White" );
        assertEquals( car1, vehicleColor.get( 0 ) );
    }
}