package controllers;

import javafx.fxml.Initializable;
import javafx.stage.Stage;
import models.Passenger;
import models.Ride;
import models.User;
import models.Vehicle;

import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
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
    private Stage stage;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void initialiseUser(boolean isDriver) {
        this.user = new User("", "", isDriver);
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


}
