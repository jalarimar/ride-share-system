package models;


import controllers.SessionManager;

import java.util.UUID;

/**
 * Created 04/04/2017.
 */
public class RideStopPoint {
    private UUID rideId;
    private StopPoint stopPoint;
    private String time;
    private String day;
    private double price;

    public RideStopPoint(StopPoint sp, String time, String day) {
        this.rideId = null;
        this.stopPoint = sp;
        this.time = time;
        this.day = day;
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
        return SessionManager.getInstance().getRss().getRideById(rideId);
    }

    public StopPoint getStopPoint() {
        return stopPoint;
    }
    public void setStopPoint(StopPoint stopPoint) {
        this.stopPoint = stopPoint;
    }

    public String getTime() {return time; }
    public void setTime(String time) {
        this.time = time;
    }

    public String getDay() {return day; }
    public void setDay(String day) {
        this.day = day;
    }

    public double getPrice() {return price;}

    private void calculatePrice(Ride ride) {
        double performance = ride.getVehicle().getPerformance(); // L/100km
        double distance = this.stopPoint.getDistanceFromUni(); // km
        double fuelPrice = 2.15; // $/L

        this.price = fuelPrice * performance * (distance / 100); // $
    }
}
