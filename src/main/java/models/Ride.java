package models;

import enums.BookingStatus;
import enums.RideStatus;
import utilities.SessionManager;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static enums.RideStatus.DONE;
import static utilities.Converter.getReadableDate;
import static enums.RideStatus.AVAILABLE;
import static enums.RideStatus.FULL;

/**
 * Created 21/03/2017.
 */
public class Ride {

    private UUID id;
    private TripDetails tripDetails;
    private List<RideStopPoint> rideStopPoints;
    private RideStatus status;
    private int availableSeats;
    private Map<String, RideStopPoint> passengerIds;
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
        this.passengerIds = new HashMap<>();
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
        return Rss.getInstance().getVehicleByLicencePlate(tripDetails.getLicenceNumber());
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
        for (String userId : passengerIds.keySet()) {
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
        int physicalSeats = getVehicle().getPhysicalSeats();
        if (availableSeats <= physicalSeats && availableSeats >= 0) {
            this.availableSeats = availableSeats;
        } else {
            this.availableSeats = physicalSeats;
        }
        if (availableSeats == 0) {
            status = FULL;
        }
    }

    public void share(int availableSeats) {
        setAvailableSeats(availableSeats);
        setStatus(RideStatus.AVAILABLE);
    }

    public void addStopPoint(RideStopPoint stopPoint) {
        rideStopPoints.add(stopPoint);
    }

    public void addPassenger(User passenger, RideStopPoint rideStopPoint) {
        passengerIds.put(passenger.getUniID(), rideStopPoint);
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

    public RideStopPoint getRspOfPassenger(User passenger) {
        return passengerIds.get(passenger.getUniID());
    }

    public void updatePrices() {
        for (RideStopPoint rideStopPoint : rideStopPoints) {
            rideStopPoint.calculatePrice(this);
            for (String id : passengerIds.keySet()) {
                if (rideStopPoint.equals(passengerIds.get(id))) {
                    User passenger = Rss.getInstance().getUserById(id);
                    if (isWatchedByPassenger(passenger)) {
                        String notification = "The price of ride: " + rideStopPoint + " " + getDirection() + " has been updated to: " + rideStopPoint.getPriceNZD();
                        passenger.setUnseenRideNotification(notification);
                    }
                }
            }
        }
    }

    public boolean isAllowedToBookRide(User user) {
        boolean hasSeatsAvailable = getStatus() == AVAILABLE;
        boolean isAlreadyBooked = getBookingStatus().equals(BookingStatus.BOOKED.toString());
        boolean isFinished = getBookingStatus().equals(BookingStatus.DONE.toString());
        boolean isDriver = getDriver().getUniID().equals(user.getUniID());
        return hasSeatsAvailable && !isAlreadyBooked && !isFinished && !isDriver;
    }

    private boolean isWatchedByPassenger(User passenger) {
        return (LocalDateTime.now().compareTo(time) <= 0) && passengerIds.keySet().contains(passenger.getUniID()) && isCancellableByDriver();
    }

    public boolean isCancellableByPassenger() {
        boolean isDone = getBookingStatus().equals(BookingStatus.DONE.toString());
        boolean isCancelled = getBookingStatus().equals(BookingStatus.CANCELLED.toString());
        return !isDone && !isCancelled;
    }

    public boolean isCancellableByDriver() {
        boolean isDone = status.equals(RideStatus.DONE);
        boolean isCancelled = status.equals(RideStatus.CANCELLED);
        boolean isUnshared = status.equals(RideStatus.UNSHARED);
        return !isDone && !isCancelled && !isUnshared;
    }

    public boolean isShareable() {
        return status == RideStatus.UNSHARED;
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
        if (LocalDateTime.now().compareTo(time) > 0 || status.equals(DONE)) {
            return BookingStatus.DONE.toString();
        } else {
            if (passengerIds.keySet().contains(user.getUniID()) && isCancellableByDriver()) {
                return BookingStatus.BOOKED.toString();
            } else {
                return BookingStatus.CANCELLED.toString();
            }
        }
    }
}
