package models;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.UUID;

import static utilities.Converter.*;

/**
 * Created 04/04/2017.
 */
public class RideStopPoint {
    private UUID rideId;
    private StopPoint stopPoint;
    private ZonedDateTime time;
    private LocalDate date;
    private double price;

    public RideStopPoint(StopPoint sp, LocalDateTime time, LocalDate date) {
        this.rideId = null;
        this.stopPoint = sp;
        this.time = toZonedTime(time);
        this.date = date;
    }

    @Override
    public String toString() {
        return stopPoint.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof RideStopPoint)) {
            return false;
        }
        RideStopPoint rideStopPoint = (RideStopPoint) obj;
        return this.toString().equals(rideStopPoint.toString())
                && time.compareTo(toZonedTime(rideStopPoint.getRawTime())) == 0
                && rideId.equals(rideStopPoint.rideId);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + this.toString().hashCode();
        return result;
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

    public ZonedDateTime getZonedTime() {return time;}
    public LocalDateTime getRawTime() {
        return time.toLocalDateTime();
    }
    public String getTime() {return getReadableTime(time.toLocalDateTime()); }
    public void setTime(LocalDateTime time) {
        this.time = toZonedTime(time);
    }

    public LocalDate getDate() {return date; }
    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void calculatePrice(Ride ride) {
        if (ride.getVehicle() != null) {
            double performance = ride.getVehicle().getPerformance(); // L/100km
            double distance = this.stopPoint.getDistanceFromUni(); // km
            double fuelPrice = 2.15; // $/L

            this.price = (fuelPrice * distance * 100)/ performance; // $
        }
    }
    public double getPrice() {return price; }
    public String getPriceNZD() {return String.format("$%.2fNZD", price); }

    public String getDay() {
        // used by PropertyValueFactory
        return getLongDayOfDate(date);
    }

    public String getHumanDate() {
        // used by PropertyValueFactory
        return getReadableDate(date);
    }

    public String getDirection() {
        // used by PropertyValueFactory
        return getRide().getDirection();
    }

    public String getAvailableSeats() {
        // used by PropertyValueFactory
        return Integer.toString(getRide().getAvailableSeats());
    }

}
