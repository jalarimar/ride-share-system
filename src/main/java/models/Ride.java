package models;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created 21/03/2017.
 */
public class Ride {

    private List<RideStopPoint> rideStopPoints;
    private boolean isFromUni;
    private String direction;
    private boolean isRecurrent;
    private List<DayOfWeek> days;
    private LocalDate startDate;
    private LocalDate expiryDate;
    private Vehicle vehicle;
    private Status status;
    private int availableSeats;
    private Driver driver;
    private List<Passenger> passengers;

    public Ride(Vehicle vehicle, Driver driver, List<RideStopPoint> rsp, boolean isFromUni, boolean isRecurrent, List<DayOfWeek> days, LocalDate startDate, LocalDate endDate) {
        this.vehicle = vehicle;
        this.driver = driver;
        this.rideStopPoints = rsp;
        this.isFromUni = isFromUni;
        this.isRecurrent = isRecurrent;
        this.days = days;
        this.startDate = startDate;
        this.expiryDate = endDate;

        if (isFromUni) {
            this.direction = "From University";
        } else {
            this.direction = "To University";
        }
        this.status = Status.UNSHARED;
        this.availableSeats = 0;
        this.passengers = new ArrayList<>();
    }

    @Override
    public String toString() {
        if (rideStopPoints.size() > 1) {
            return rideStopPoints.get(0) + " to " + rideStopPoints.get(rideStopPoints.size() - 1);
        } else {
            return "Short Ride";
        }
    }

    public boolean isFromUni() {
        return isFromUni;
    }

    public String getDirection() { return direction; }

    public boolean isRecurrent() {
        return isRecurrent;
    }

    public List<DayOfWeek> getDays() {
        return days;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    public Driver getDriver() {
        return driver;
    }

    public List<Passenger> getPassengers() {
        return passengers;
    }

    public int getRouteLength() {
        return 0;
    }

    public int getEstimatedDuration() {
        return 0;
    }

    public int getNumberOfStops() {
        return rideStopPoints.size();
    }

    public void addStopPoint(RideStopPoint stopPoint) {
        rideStopPoints.add(stopPoint);
    }

    public void addPassenger(Passenger passenger) {
        passengers.add(passenger);
        availableSeats -= 1;
    }

    public void removePassenger(Passenger passenger) {
        passengers.remove(passenger);
        availableSeats += 1;
    }

}
