package models;

import controllers.SessionManager;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static controllers.Converter.getReadableDate;
import static models.RideStatus.AVAILABLE;
import static models.RideStatus.FULL;

/**
 * Created 21/03/2017.
 */
public class Ride {

    private UUID id;
    private TripDetails tripDetails;
    private List<RideStopPoint> rideStopPoints;
    private RideStatus status;
    private int availableSeats;
    private List<String> passengerIds;
    private LocalDate date;
    private LocalDateTime time; // first stop time

    public Ride(TripDetails trip, List<RideStopPoint> rsps) {
        this.id = UUID.randomUUID();
        this.tripDetails = trip;

        this.rideStopPoints = rsps;
        for (RideStopPoint rsp : rsps) {
            rsp.setRide(this);
        }
        this.status = RideStatus.UNSHARED;
        this.availableSeats = 0;
        this.passengerIds = new ArrayList<>();
        this.date = rsps.get(0).getDate();
        this.time = rsps.get(0).getRawTime();

        Rss.getInstance().addRide(this);
    }

    @Override
    public String toString() {
        if (rideStopPoints.size() > 0) {
            if (tripDetails.isFromUni()) {
                return "University to " + rideStopPoints.get(rideStopPoints.size() - 1);
            } else {
                return rideStopPoints.get(0) + " to University";
            }
        } else {
            return "Empty Ride";
        }
    }

    public UUID getId() { return id; }
    public List<RideStopPoint> getRideStopPoints() {
        return rideStopPoints;
    }
    public Vehicle getVehicle() {
        return tripDetails.getVehicle();
    }
    public RideStatus getStatus() {
        return status;
    }
    public int getAvailableSeats() {
        return availableSeats;
    }
    public LocalDate getDate() {return date; }
    public LocalDateTime getTime() { return time; }

    public String getDirection() {
        if (tripDetails.isFromUni()) {
            return "From University";
        } else {
            return "To University";
        }
    }

    public Driver getDriver() {
        return Rss.getInstance().getDriverById(tripDetails.getDriverId());
    }
    public List<User> getPassengers() {
        List<User> passengers = new ArrayList<>();
        for (String userId : passengerIds) {
            passengers.add(Rss.getInstance().getUserById(userId));
        }
        return passengers;
    }

    public int getNumberOfStops() {
        return rideStopPoints.size();
    }

    public void setStatus(RideStatus status) {
        this.status = status;
    }

    public void setAvailableSeats(int availableSeats) {
        int physicalSeats = tripDetails.getVehicle().getPhysicalSeats();
        if (availableSeats <= physicalSeats && availableSeats >= 0) {
            this.availableSeats = availableSeats;
        } else {
            this.availableSeats = physicalSeats;
        }
        if (availableSeats == 0) {
            status = FULL;
        }
    }

    public void addStopPoint(RideStopPoint stopPoint) {
        rideStopPoints.add(stopPoint);
    }

    public void addPassenger(User passenger) {
        passengerIds.add(passenger.getUniID());
        availableSeats -= 1;

        if (availableSeats < 1) {
            status = FULL;
            availableSeats = 0;
        }
    }

    public void removePassenger(User passenger) {
        passengerIds.remove(passenger.getUniID());
        availableSeats += 1;

        if (availableSeats == 1 && status == FULL) {
            status = AVAILABLE;
        }
    }

    public String getHumanDate() {
        // used by PropertyValueFactory
        return getReadableDate(date);
    }

    public String getName() {
        // used by PropertyValueFactory
        return this.toString();
    }

    public String getDriverId() {
        // used by PropertyValueFactory
        return getDriver().getUniID();
    }

    public String getBookingStatus() {
        // used by PropertyValueFactory
        User user = SessionManager.getInstance().getCurrentUser();
        if (LocalDateTime.now().compareTo(time) > 0) {
            return BookingStatus.DONE.toString();
        } else {
            if (passengerIds.contains(user.getUniID())) {
                return BookingStatus.BOOKED.toString();
            } else {
                return BookingStatus.CANCELLED.toString();
            }
        }
    }
}
