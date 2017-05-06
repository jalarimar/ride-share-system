package controllers;

import javafx.stage.Stage;
import models.*;

import java.util.ArrayList;
import java.util.List;

import static controllers.Serializer.saveRss;
import static controllers.Serializer.loadRss;

/**
 * Created by jar156 on 6/05/17.
 */
public class SessionManager {

    private static SessionManager instance = null;

    private SessionManager() {
    }

    public static SessionManager getInstance() {
        if(instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    private Stage stage;
    private Rss rss;
    private User currentUser;

    // TODO replace these with RSS
    private List<StopPoint> allStopPoints = new ArrayList<>();
    private List<Ride> sharedRides = new ArrayList<>();

    // what the user is currently looking at
    private StopPoint focusedStopPoint;
    private Ride focusedRide;

    ////// Getters and Setters

    public Stage getStage() {
        return stage;
    }
    public void setStage(Stage stage) { this.stage = stage; }

    public Rss getRss() { return rss; }
    public void setRss(Rss rss) { this.rss = rss; }

    public StopPoint getFocusedStopPoint() {
        return focusedStopPoint;
    }
    public void setFocusedStopPoint(StopPoint sp) {
        this.focusedStopPoint = sp;
    }

    public Ride getFocusedRide() { return focusedRide; }
    public void setFocusedRide(Ride r) {
        this.focusedRide = r;
    }

    public User getCurrentUser() {
        return currentUser;
    }
    public Driver getCurrentDriver() {
        if (currentUser.isDriver()) {
            return (Driver)currentUser;
        } else {
            return null;
        }
    }
    public void setCurrentUser(User user) {
        this.currentUser = user;
        // TODO check if user is in RSS if not add them
        saveRss(rss);
    }

    //////

    public List<StopPoint> getAllStopPoints() { return allStopPoints; }

    public List<Ride> getSharedRides() { return sharedRides; }

    public void addStopPoint(StopPoint sp) {
        allStopPoints.add(sp);
    }

    public void addSharedRide(Ride r) {
        sharedRides.add(r);
    }

/*
    // TODO move this function
    public boolean tryAddPassenger(Ride ride, User passenger) {
        Vehicle vehicle = ride.getVehicle();
        int nSeats = vehicle.getPhysicalSeats();
        if (nSeats > 0 && !ride.getPassengers().contains(passenger)) {
            ride.addPassenger(passenger);
            return true;
        } else {
            return false;
        }
    }
    */
}
