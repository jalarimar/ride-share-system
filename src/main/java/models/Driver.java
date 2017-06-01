package models;

import enums.NotificationStatus;

import java.io.File;
import java.time.LocalDate;
import java.util.*;

import static enums.NotificationStatus.*;

/**
 * Created 21/03/2017.
 */
public class Driver extends User {

    private Licence licence;
    private List<Vehicle> vehicles;
    private List<StopPoint> stopPoints;
    private List<Route> routes;
    private List<UUID> myRideIds;

    public Driver(String firstName, String lastName, String uniID, String password, String email, StopPoint address, String homeNumber, String mobileNumber, File photo) {
        super(firstName, lastName, true, uniID, password, email, address, homeNumber, mobileNumber, photo);
        this.vehicles = new ArrayList<>();
        this.stopPoints = new ArrayList<>();
        this.routes = new ArrayList<>();
        this.myRideIds = new ArrayList<>();

        Rss.getInstance().addUser(this);
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }
    public List<StopPoint> getStopPoints() { return stopPoints; }
    public List<Route> getRoutes() {return routes; }
    public List<Ride> getMyRides() {
        List<Ride> rides = new ArrayList<>();
        for (UUID id : myRideIds) {
            rides.add(Rss.getInstance().getRideById(id));
        }
        return rides;
    }
    public Licence getLicence() {
        return licence;
    }

    public void setLicence(Licence licence) {
        this.licence = licence;
    }

    public void addVehicle(Vehicle v) {
        vehicles.add(v);
    }

    public void addStopPoint(StopPoint p) {
        stopPoints.add(p);
        Rss.getInstance().addStopPoint(p);
    }

    public void addRoute(Route route) {
        routes.add(route);
    }

    public void addRide(Ride ride) {
        myRideIds.add(ride.getId());
    }

    public Vehicle getVehicleById(String id) {
        for (Vehicle vehicle : vehicles) {
            if (vehicle.getLicensePlate().equals(id)) {
                return vehicle;
            }
        }
        return null;
    }

    public NotificationStatus getTimeUntilExpiry(LocalDate expiryDate) {
        LocalDate now = LocalDate.now();

        if (now.isAfter(expiryDate.minusWeeks(1))) {
            return ONE_WEEK;
        } else if (now.isAfter(expiryDate.minusWeeks(2))) {
            return TWO_WEEKS;
        } else if (now.isAfter(expiryDate.minusMonths(1))) {
            return ONE_MONTH;
        } else {
            // more than one month until expiry
            return NONE;
        }
    }
}
