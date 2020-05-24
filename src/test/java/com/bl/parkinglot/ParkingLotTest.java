package com.bl.parkinglot;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ParkingLotTest {
    ParkingLot parkingLot;
    ParkingLotOwner owner ;
    Object car;

    @Before
    public void setup() {
        parkingLot = new ParkingLot( 1 );
        car = new Object();
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
        boolean isPark = parkingLot.isCarPark( new Object() );
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
        boolean isPark = parkingLot.unParkCar( new Object() );
        Assert.assertFalse( isPark );
    }
    @Test
    public void given_Car_WhenParkingLotFull_ShouldInformOwner() {
        parkingLot.registerOwner( owner );
        try {
            parkingLot.park( car );
            parkingLot.park( new Object() );
        } catch (ParkingLotException e) {
            boolean checkCapacityFull = owner.isCapacityFull();
            Assert.assertTrue( checkCapacityFull );
        }
    }
}

