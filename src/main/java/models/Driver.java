package models;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created 21/03/2017.
 */
public class Driver extends User {

    private Licence licence;
    private List<Vehicle> vehicles;
    private List<StopPoint> stopPoints;
    private List<Route> routes;
    private List<Ride> rides;

    public Driver(String firstName, String lastName) {
        super(firstName, lastName, true);
        this.vehicles = new ArrayList<>();
        this.stopPoints = new ArrayList<>();
        this.routes = new ArrayList<>();
        this.rides = new ArrayList<>();
    }

    public Driver(String firstName, String lastName, String uniID, String password, String email, String address, String homeNumber, String mobileNumber, File photo) {
        super(firstName, lastName, true, uniID, password, email, address, homeNumber, mobileNumber, photo);
        this.vehicles = new ArrayList<>();
        this.stopPoints = new ArrayList<>();
        this.routes = new ArrayList<>();
        this.rides = new ArrayList<>();
    }

    public void setLicence(Licence licence) { this.licence = licence; }

    public void addVehicle(Vehicle v) {
        vehicles.add(v);
    }

    public void addStopPoint(StopPoint p) {
        stopPoints.add(p);
    }

    public void addRoute(Route route) {
        routes.add(route);
    }

    public void addRide(Ride r) {
        rides.add(r);
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public List<StopPoint> getStopPoints() { return stopPoints; }

    public List<Route> getRoutes() {return routes; }

    public List<Ride> getRides() {return rides; }

    public Licence getLicence() {
        return licence;
    }
}
