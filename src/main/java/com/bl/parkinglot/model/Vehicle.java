package com.bl.parkinglot.model;

import java.time.LocalDateTime;

public class Vehicle {
    private LocalDateTime time;
    private String modelName;
    private String numberPlate;
    private String color;

    public Vehicle() {
    }

    public Vehicle(String color, String numberPlate, String modelName) {
        this.time = LocalDateTime.now();
        this.numberPlate = numberPlate;
        this.modelName = modelName;
        this.color = color;
    }
    public String getColor() {
        return color;
    }
    public String getNumberPlate() {


        return numberPlate;
    }
    public String getModelName() {
        return modelName;
    }
}
