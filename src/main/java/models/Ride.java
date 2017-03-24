package models;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Created 21/03/2017.
 */
public class Ride {

    private ArrayList<StopPoint> route;
    private boolean isFromUni;
    private boolean isRecurrent;
    private ArrayList<String> days;
    private LocalDate startDate;
    private LocalDate expiryDate;
    private Vehicle vehicle;
    private Status status;
    private int availableSeats;
    private Driver driver;
    private ArrayList<Passenger> passengers;

    public Ride(Vehicle vehicle, Driver driver) {
        this.route = new ArrayList<>();
        this.isFromUni = false;
        this.isRecurrent = false;
        this.days = new ArrayList<>();
        this.expiryDate = null;
        this.vehicle = vehicle;
        this.status = Status.UNSHARED;
        this.availableSeats = vehicle.getPhysicalSeats();
        this.driver = driver;
        this.passengers = new ArrayList<>();
    }

    public ArrayList<StopPoint> getRoute() {
        return route;
    }

    public void setRoute(ArrayList<StopPoint> route) {
        this.route = route;
    }

    public boolean isFromUni() {
        return isFromUni;
    }

    public void setFromUni(boolean fromUni) {
        isFromUni = fromUni;
    }

    public boolean isRecurrent() {
        return isRecurrent;
    }

    public void setRecurrent(boolean recurrent) {
        isRecurrent = recurrent;
    }

    public ArrayList<String> getDays() {
        return days;
    }

    public void setDays(ArrayList<String> days) {
        this.days = days;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
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

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public ArrayList<Passenger> getPassengers() {
        return passengers;
    }

    public void setPassengers(ArrayList<Passenger> passengers) {
        this.passengers = passengers;
    }

    public int getRouteLength() {
        return 0;
    }

    public int getEstimatedDuration() {
        return 0;
    }

    public int getNumberOfStops() {
        return route.size();
    }

    public void addStopPoint(StopPoint stopPoint) {
        route.add(stopPoint);
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
