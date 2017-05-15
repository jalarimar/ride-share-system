package models;

import controllers.SessionManager;
import javafx.fxml.Initializable;

import java.io.File;
import java.net.URL;
import java.util.*;

import static controllers.Serializer.saveRss;

/**
 * Created 21/03/2017.
 */
public class Driver extends User implements Observer {

    private Licence licence;
    private List<Vehicle> vehicles;
    private List<StopPoint> stopPoints;
    private List<Route> routes;
    private List<UUID> myRideIds;

    private transient Rss rss = SessionManager.getInstance().getRss(); // TODO this doesn't work

    public Driver(Driver driver) {
        super(driver.getFirstName(), driver.getLastName(), true, driver.getUniID(), driver.getPassword(), driver.getEmail(), driver.getAddress(), driver.getHomeNumber(), driver.getMobileNumber(), driver.getPhoto());
        this.vehicles = driver.getVehicles();
        this.stopPoints = driver.getStopPoints();
        this.routes = driver.getRoutes();
        this.myRideIds = driver.myRideIds;
        this.rss = SessionManager.getInstance().getRss();
    }

    // TODO remove this constructor is only used in tests
    public Driver(String firstName, String lastName) {
        super(firstName, lastName, true);
        this.vehicles = new ArrayList<>();
        this.stopPoints = new ArrayList<>();
        this.routes = new ArrayList<>();
        this.myRideIds = new ArrayList<>();

        rss.addUser(this);
    }

    public Driver(String firstName, String lastName, String uniID, String password, String email, String address, String homeNumber, String mobileNumber, File photo) {
        super(firstName, lastName, true, uniID, password, email, address, homeNumber, mobileNumber, photo);
        this.vehicles = new ArrayList<>();
        this.stopPoints = new ArrayList<>();
        this.routes = new ArrayList<>();
        this.myRideIds = new ArrayList<>();

        rss.addUser(this);
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }
    public List<StopPoint> getStopPoints() { return stopPoints; }
    public List<Route> getRoutes() {return routes; }
    public List<Ride> getMyRides() {
        List<Ride> rides = new ArrayList<>();
        for (UUID id : myRideIds) {
            rides.add(rss.getRideById(id));
        }
        return rides;
    }
    public Licence getLicence() {
        return licence;
    }

    public void update(Observable obs, Object obj)
    {
        rss.updateUser(this);
        // TODO find a different way to add observer
    }

    public void setLicence(Licence licence) {
        this.licence = licence;
        rss.updateUser(this);
    }

    public void addVehicle(Vehicle v) {
        vehicles.add(v);
        rss.updateUser(this);
    }

    public void addStopPoint(StopPoint p) {
        stopPoints.add(p);
        rss.updateUser(this);
        rss.addStopPoint(p);
    }

    public void addRoute(Route route) {
        routes.add(route);
        rss.updateUser(this);
    }

    public void addRide(Ride ride) {
        myRideIds.add(ride.getId());
        System.out.println("RSS " + rss); // TODO rss is null
        SessionManager.getInstance().getRss().updateUser(this);
        saveRss(SessionManager.getInstance().getRss());
    }
}
