package com.bl.parkinglot;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ParkingLotTest {
    ParkingLot parkingLot;
    Object car;

    @Before
    public void setup() {
        parkingLot = new ParkingLot();
        car = new Object();
    }

    @Test
    public void given_Car_WhenParked_ShouldReturnTrue() {
        boolean isPark = parkingLot.parkCar( car );
        Assert.assertTrue( isPark );
    }

    @Test
    public void given_Car_WhenUnParked_ShouldReturnTrue() {
        parkingLot.parkCar( car );
        boolean isUnParked = parkingLot.unParkCar( car );
        Assert.assertTrue( isUnParked );
    }

    @Test
    public void given_Car_WhenAlreadyParked_ShouldReturnFalse() {
        parkingLot.parkCar( car );
        boolean isPark = parkingLot.parkCar( new Object() );
        Assert.assertFalse( isPark );
    }
    @Test
    public void givenUnParkedCar_WhenTryUnPark_ShouldReturnFalse() {
        boolean isPark = parkingLot.unParkCar( car );
        Assert.assertFalse( isPark );
    }
    @Test
    public void givenCar_WhenTryToUnParkDifferentVehicle_ShouldReturnFalse() {
        parkingLot.parkCar(car);
        boolean isPark = parkingLot.unParkCar(new Object());
        Assert.assertFalse(isPark);
    }
}

