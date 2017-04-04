package models;

import java.io.Serializable;

/**
 * Created 21/03/2017.
 */
public class Vehicle implements Serializable {
    private String type;
    private String model;
    private String colour;
    private String licensePlate;
    private String performance;
    private int year;
    private int physicalSeats;

    public Vehicle(String type, String model, String colour, String licensePlate, String performance, int year, int physicalSeats) {
        this.type = type;
        this.model = model;
        this.colour = colour;
        this.licensePlate = licensePlate;
        this.performance = performance;
        this.year = year;
        this.physicalSeats = physicalSeats;
    }

    @Override
    public String toString() {
        return type + " " + model + " " + licensePlate;
    }

    public String getType() {
        return type;
    }

    public String getTypeAndModel() {
        return type + " " + model;
    }

    public String getColour() {
        return colour;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public String getPerformance() {
        return performance;
    }

    public int getYear() {
        return year;
    }

    public int getPhysicalSeats() {
        return physicalSeats;
    }


}
