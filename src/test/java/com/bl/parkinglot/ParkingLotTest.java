package com.bl.parkinglot;

import com.bl.parkinglot.exception.ParkingLotException;
import com.bl.parkinglot.observer.AirportSecurity;
import com.bl.parkinglot.service.ParkingLot;
import com.bl.parkinglot.observer.ParkingLotOwner;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ParkingLotTest {
    ParkingLot parkingLot;
    ParkingLotOwner owner;
    Object car;
    Object car2;

    @Before
    public void setup() {
        parkingLot = new ParkingLot( 1 );
        car = new Object();
        car2 = new Object();
        owner = new ParkingLotOwner();
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
    public void givenUnParkedCar_WhenTryUnPark_ShouldReturnFalse() {
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
        } catch (ParkingLotException e) {
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
            Assert.assertTrue( isPark1 && isPark2 );
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void given_Car_WhenParkingLotFull_ShouldInformAirportSecurity() {
        AirportSecurity airportSecurity = new AirportSecurity();
        parkingLot.registerObserver(airportSecurity);
        try {
            parkingLot.park( car );
            parkingLot.park( new Object() );
        } catch (ParkingLotException e) {
            boolean checkCapacityFull = airportSecurity.isCapacityFull();
            Assert.assertTrue( checkCapacityFull );
        }
    }
}

