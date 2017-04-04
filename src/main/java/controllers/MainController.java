package controllers;

import javafx.fxml.Initializable;
import javafx.stage.Stage;
import models.*;

import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by samschofield on 15/03/17.
 */
public class MainController {

    private static MainController instance = null;

    private MainController() {
        // Exists to defeat instantiation
    }

    public static MainController getInstance() {
        if(instance == null) {
            instance = new MainController();
        }
        return instance;
    }

    private User user;
    private Driver driver;
    private Stage stage;

    private List<StopPoint> allStopPoints = new ArrayList<>();
    private List<Ride> sharedRides = new ArrayList<>();
    private StopPoint focusedStopPoint;
    private Ride focusedRide;


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Driver getDriver() {
        return driver;
    }

    public List<StopPoint> getAllStopPoints() { return allStopPoints; }

    public List<Ride> getSharedRides() { return sharedRides; }

    public void addSharedRide(Ride r) {
        sharedRides.add(r);
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
        setUser(driver);
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public StopPoint getFocusedStopPoint() {
        return focusedStopPoint;
    }
    public void setFocusedStopPoint(StopPoint sp) {
        this.focusedStopPoint = sp;
    }

    public Ride getFocusedRide() {
        return focusedRide;
    }
    public void setFocusedRide(Ride r) {
        this.focusedRide = r;
    }

    public void addStopPoint(StopPoint sp) {
        allStopPoints.add(sp);
    }

    public void initialiseUser(boolean isDriver) {
        this.user = new User("", "", isDriver);
        if (isDriver) {
            this.driver = new Driver("", "");
        }
    }

    public boolean tryAddPassenger(Ride ride, Passenger passenger) {
        Vehicle vehicle = ride.getVehicle();
        int nSeats = vehicle.getPhysicalSeats();
        if (nSeats > 0 && !ride.getPassengers().contains(passenger)) {
            ride.addPassenger(passenger);
            return true;
        } else {
            return false;
        }
    }

    public String getDayOfDate(LocalDate date) {
        DayOfWeek day = date.getDayOfWeek();
        String n = day.name();
        System.out.println(n);
        return "today";
    }

    public Integer tryParseInt(String text) {
        if (text != null && !text.isEmpty()) {
            if (text.trim().matches("[0-9]+")) {
                return Integer.valueOf(text.trim());
            }
        }
        return -1;
    }

    public boolean isValidInputString(String text) {
        if (text != null && !text.isEmpty()) {
            if (text.matches("[a-zA-Z0-9 ]+")) {
                return true;
            }
        }
        return false;
    }


}
