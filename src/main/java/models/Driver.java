package models;

import javafx.scene.paint.Stop;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created 21/03/2017.
 */
public class Driver extends User implements AutoCloseable, Serializable {

    private static final long serialVersionUID = 1L;
    //private String photo;
    //private String grade;
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

    public void close() {
        this.close();
    }
}
