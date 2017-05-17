package models;


import controllers.SessionManager;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Created 04/04/2017.
 */
public class RideStopPoint {
    private UUID rideId;
    private StopPoint stopPoint;
    private LocalDateTime time;
    private LocalDate date;
    private double price;

    public RideStopPoint(StopPoint sp, LocalDateTime time, LocalDate date) {
        this.rideId = null;
        this.stopPoint = sp;
        this.time = time;
        this.date = date;
    }

    @Override
    public String toString() {
        return stopPoint.toString();
    }

    public void setRide(Ride ride) {
        this.rideId = ride.getId();
        calculatePrice(ride);
    }
    public Ride getRide() {
        return Rss.getInstance().getRideById(rideId);
    }

    public StopPoint getStopPoint() {
        return stopPoint;
    }
    public void setStopPoint(StopPoint stopPoint) {
        this.stopPoint = stopPoint;
    }

    public LocalDateTime getTime() {return time; }
    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public LocalDate getDate() {return date; }
    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getPrice() {return price;}

    private void calculatePrice(Ride ride) {
        double performance = ride.getVehicle().getPerformance(); // L/100km
        double distance = this.stopPoint.getDistanceFromUni(); // km
        double fuelPrice = 2.15; // $/L

        this.price = fuelPrice * performance * (distance / 100); // $
    }
}
