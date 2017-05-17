package models;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static models.RideStatus.AVAILABLE;
import static models.RideStatus.FULL;

/**
 * Created 21/03/2017.
 */
public class TripDetails {

    // common to all rides generated from this trip
    private boolean isFromUni;
    private boolean isRecurrent;
    private List<DayOfWeek> days;
    private LocalDate expiryDate;
    private Vehicle vehicle;
    private String driverId;

    public TripDetails(Vehicle vehicle, Driver driver, boolean isFromUni, boolean isRecurrent, List<DayOfWeek> days, LocalDate endDate) {
        this.vehicle = vehicle;
        this.driverId = driver.getUniID();
        this.isFromUni = isFromUni;
        this.isRecurrent = isRecurrent;
        this.days = days;
        this.expiryDate = endDate;
    }

    public boolean isFromUni() {
        return isFromUni;
    }
    public boolean isRecurrent() {
        return isRecurrent;
    }
    public List<DayOfWeek> getDays() {
        return days;
    }
    public LocalDate getExpiryDate() {
        return expiryDate;
    }
    public Vehicle getVehicle() {
        return vehicle;
    }
    public String getDriverId() {
        return driverId;
    }
}
