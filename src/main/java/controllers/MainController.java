package controllers;

import javafx.fxml.Initializable;
import models.Passenger;
import models.Ride;
import models.Vehicle;

import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 * Created by samschofield on 15/03/17.
 */
public class MainController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {

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
