package models;

import controllers.SessionManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static controllers.Serializer.saveRss;

/**
 * Created 21/03/2017.
 */
public class Driver extends User {

    private Licence licence;
    private List<Vehicle> vehicles;
    private List<StopPoint> stopPoints;
    private List<Route> routes;
    private List<UUID> myRideIds;

    public Driver(String firstName, String lastName) {
        super(firstName, lastName, true);
        this.vehicles = new ArrayList<>();
        this.stopPoints = new ArrayList<>();
        this.routes = new ArrayList<>();
        this.myRideIds = new ArrayList<>();

        SessionManager.getInstance().getRss().addUser(this);
        saveRss(SessionManager.getInstance().getRss());
    }

    public Driver(String firstName, String lastName, String uniID, String password, String email, String address, String homeNumber, String mobileNumber, File photo) {
        super(firstName, lastName, true, uniID, password, email, address, homeNumber, mobileNumber, photo);
        this.vehicles = new ArrayList<>();
        this.stopPoints = new ArrayList<>();
        this.routes = new ArrayList<>();
        this.myRideIds = new ArrayList<>();

        SessionManager.getInstance().getRss().addUser(this);
        saveRss(SessionManager.getInstance().getRss());
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }
    public List<StopPoint> getStopPoints() { return stopPoints; }
    public List<Route> getRoutes() {return routes; }
    public List<Ride> getMyRides() {
        List<Ride> rides = new ArrayList<>();
        for (UUID id : myRideIds) {
            rides.add(SessionManager.getInstance().getRss().getRideById(id));
        }
        return rides;
    }
    public Licence getLicence() {
        return licence;
    }

    public void setLicence(Licence licence) {
        this.licence = licence;
        SessionManager.getInstance().getRss().updateUser(this);
        saveRss(SessionManager.getInstance().getRss());
    }

    public void addVehicle(Vehicle v) {
        vehicles.add(v);
        SessionManager.getInstance().getRss().updateUser(this);
        saveRss(SessionManager.getInstance().getRss());
    }

    public void addStopPoint(StopPoint p) {
        stopPoints.add(p);
        SessionManager.getInstance().getRss().updateUser(this);
        SessionManager.getInstance().getRss().addStopPoint(p);
        saveRss(SessionManager.getInstance().getRss());
    }

    public void addRoute(Route route) {
        routes.add(route);
        SessionManager.getInstance().getRss().updateUser(this);
        saveRss(SessionManager.getInstance().getRss());
    }

    public void addRide(Ride ride) {
        myRideIds.add(ride.getId());
        SessionManager.getInstance().getRss().updateUser(this);
        saveRss(SessionManager.getInstance().getRss());
    }
}
