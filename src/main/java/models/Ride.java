package models;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created 21/03/2017.
 */
public class Ride {

    private UUID id;
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
    private List<User> passengers;

    public Ride(Vehicle vehicle, Driver driver, List<RideStopPoint> rsps, boolean isFromUni, boolean isRecurrent, List<DayOfWeek> days, LocalDate startDate, LocalDate endDate) {
        this.id = UUID.randomUUID();
        this.vehicle = vehicle;
        this.driver = driver;
        this.rideStopPoints = rsps;
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
        for (RideStopPoint rsp : rideStopPoints) {
            rsp.setRide(this);
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

    public UUID getId() { return id; }

    public List<RideStopPoint> getRideStopPoints() {
        return rideStopPoints;
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
        if (availableSeats <= vehicle.getPhysicalSeats() && availableSeats >= 0) {
            this.availableSeats = availableSeats;
        } else {
            this.availableSeats = vehicle.getPhysicalSeats();
        }
        if (availableSeats == 0) {
            status = Status.FULL;
        }
    }

    public Driver getDriver() {
        return driver;
    }

    public List<User> getPassengers() {
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

    private void checkAvailableSeats() {
        if (availableSeats < 1) {
            status = Status.FULL;
        }
    }

    public void addPassenger(User passenger) {
        passengers.add(passenger);
        availableSeats -= 1;

        checkAvailableSeats();
    }

    public void removePassenger(User passenger) {
        passengers.remove(passenger);
        availableSeats += 1;
    }

}
