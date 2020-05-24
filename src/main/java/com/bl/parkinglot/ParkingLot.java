/**********************************************************
 * @Purpose: Parking and unParking the car
 * @Author: Siddhesh Thakur
 * @Date: 22/05/2020
 **********************************************************/
package com.bl.parkinglot;

public class ParkingLot {

    /**
     * @param: car Car object
     **/

    private Object car;

    //to park the car
    public boolean parkCar(Object car) {
        if (this.car != null) return false;
        this.car = car;
        return true;
    }

    //to unPark the car
    public boolean unParkCar(Object car) {
        if (this.car == null) return false;
        if (this.car.equals( car )) {
            this.car = null;
            return true;
        }
        return false;
    }
}

