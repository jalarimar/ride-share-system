package models;

import controllers.SessionManager;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static controllers.Serializer.saveRss;
import static models.Status.AVAILABLE;
import static models.Status.FULL;

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
    private String driverId;
    private List<String> passengerIds;

    public Ride(Vehicle vehicle, Driver driver, List<RideStopPoint> rsps, boolean isFromUni, boolean isRecurrent, List<DayOfWeek> days, LocalDate startDate, LocalDate endDate) {
        this.id = UUID.randomUUID();
        this.vehicle = vehicle;
        this.driverId = driver.getUniID();
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
        for (RideStopPoint rsp : rsps) {
            rsp.setRide(this);
        }
        this.status = Status.UNSHARED;
        this.availableSeats = 0;
        this.passengerIds = new ArrayList<>();

        SessionManager.getInstance().getRss().addRide(this);
        saveRss(SessionManager.getInstance().getRss());
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
    public int getAvailableSeats() {
        return availableSeats;
    }

    public Driver getDriver() {
        return (Driver)SessionManager.getInstance().getRss().getUserById(driverId);
    }
    public List<User> getPassengers() {
        List<User> passengers = new ArrayList<>();
        for (String userId : passengerIds) {
            passengers.add(SessionManager.getInstance().getRss().getUserById(userId));
        }
        return passengers;
    }
    public int getRouteLength() {
        // TODO for story 2.8
        return 0;
    }
    public int getNumberOfStops() {
        return rideStopPoints.size();
    }

    public void setStatus(Status status) {
        this.status = status;
        SessionManager.getInstance().getRss().updateRide(this);
        saveRss(SessionManager.getInstance().getRss());
    }

    public void setAvailableSeats(int availableSeats) {
        if (availableSeats <= vehicle.getPhysicalSeats() && availableSeats >= 0) {
            this.availableSeats = availableSeats;
        } else {
            this.availableSeats = vehicle.getPhysicalSeats();
        }
        if (availableSeats == 0) {
            status = FULL;
        }

        SessionManager.getInstance().getRss().updateRide(this);
        saveRss(SessionManager.getInstance().getRss());
    }

    public void addStopPoint(RideStopPoint stopPoint) {
        rideStopPoints.add(stopPoint);
        SessionManager.getInstance().getRss().updateRide(this);
        saveRss(SessionManager.getInstance().getRss());
    }

    public void addPassenger(User passenger) {
        passengerIds.add(passenger.getUniID());
        availableSeats -= 1;

        if (availableSeats < 1) {
            status = FULL;
        }

        SessionManager.getInstance().getRss().updateRide(this);
        saveRss(SessionManager.getInstance().getRss());
    }

    public void removePassenger(User passenger) {
        passengerIds.remove(passenger.getUniID());
        availableSeats += 1;

        if (availableSeats == 1 && status == FULL) {
            status = AVAILABLE;
        }

        SessionManager.getInstance().getRss().updateRide(this);
        saveRss(SessionManager.getInstance().getRss());
    }

}
