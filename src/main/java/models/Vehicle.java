package models;

import java.io.Serializable;
import java.time.LocalDate;

import static models.NotificationStatus.NONE;

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
    private LocalDate wofExpiry;
    private LocalDate regExpiry;
    private NotificationStatus lastSeenWofNotification;
    private NotificationStatus lastSeenRegNotification;

    public Vehicle(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public Vehicle(String type, String model, String colour, String licensePlate, String performance, int year, int physicalSeats, LocalDate regExpiry, LocalDate wofExpiry) {
        this.type = type;
        this.model = model;
        this.colour = colour;
        this.licensePlate = licensePlate;
        this.performance = performance;
        this.year = year;
        this.physicalSeats = physicalSeats;
        this.wofExpiry = wofExpiry;
        this.regExpiry = regExpiry;

        this.lastSeenWofNotification = NONE;
        this.lastSeenRegNotification = NONE;
    }

    @Override
    public String toString() {
        return type + " " + model + " " + licensePlate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Vehicle vehicle = (Vehicle) o;
        return licensePlate.equals(vehicle.licensePlate);
    }

    @Override
    public int hashCode() {
        return licensePlate.hashCode();
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

    public LocalDate getWofExpiry() {
        return wofExpiry;
    }

    public void setWofExpiry(LocalDate wofExpiry) {
        this.wofExpiry = wofExpiry;
        lastSeenWofNotification = NONE;
    }

    public LocalDate getRegExpiry() {
        return regExpiry;
    }

    public void setRegExpiry(LocalDate regExpiry) {
        this.regExpiry = regExpiry;
        lastSeenRegNotification = NONE;
    }

    public NotificationStatus getLastSeenWofNotification() {
        return lastSeenWofNotification;
    }

    public void setLastSeenWofNotification(NotificationStatus lastSeenWofNotification) {
        this.lastSeenWofNotification = lastSeenWofNotification;
    }

    public NotificationStatus getLastSeenRegNotification() {
        return lastSeenRegNotification;
    }

    public void setLastSeenRegNotification(NotificationStatus lastSeenRegNotification) {
        this.lastSeenRegNotification = lastSeenRegNotification;
    }
}
