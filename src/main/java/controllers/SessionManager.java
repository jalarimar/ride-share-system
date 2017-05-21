package controllers;

import javafx.stage.Stage;
import models.*;

import java.util.ArrayList;
import java.util.List;

import static controllers.Serializer.loadRss;
import static controllers.Serializer.saveRss;

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
    private User currentUser;

    // what the user is currently looking at
    private StopPoint focusedStopPoint;
    private Ride focusedRide;
    private Vehicle focusedVehicle;
    private String previousScene;

    ////// Getters and Setters

    public Stage getStage() {
        return stage;
    }
    public void setStage(Stage stage) { this.stage = stage; }

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

    public Vehicle getFocusedVehicle() { return focusedVehicle; }
    public void setFocusedVehicle(Vehicle v) { this.focusedVehicle = v; }

    public String getPreviousScene() { return previousScene;}
    public void setPreviousScene(String previousScene) { this.previousScene = previousScene; }

    public User getCurrentUser() {
        return currentUser;
    }
    public Driver getCurrentDriver() {
        if (currentUser instanceof Driver) {
            return (Driver)currentUser;
        } else {
            return null;
        }
    }
    public void setCurrentUser(User user) {
        this.currentUser = user;

        String id = user.getUniID();
        if (Rss.getInstance().getUserById(id) == null) {
            Rss.getInstance().addUser(user);
        }
    }


/*
    // TODO move or remove this function
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
