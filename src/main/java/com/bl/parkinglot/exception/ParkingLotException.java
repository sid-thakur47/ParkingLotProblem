/**********************************************************
 * @Purpose: To use Custom Exception when error occurs
 * @Author: Siddhesh Thakur
 * @Date: 22/05/2020
 **********************************************************/

package com.bl.parkinglot.exception;

public class ParkingLotException extends Exception {
    public final Parking error;

    public ParkingLotException(Parking error) {
        this.error = error;
    }

    public enum Parking {
        PARKING_FULL,ALREADY_PARKED,CAR_NOT_FOUND
    }
}
