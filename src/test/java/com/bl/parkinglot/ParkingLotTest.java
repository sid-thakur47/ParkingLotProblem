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
}

