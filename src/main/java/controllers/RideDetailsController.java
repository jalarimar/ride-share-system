package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import models.Driver;
import models.Ride;
import models.RideStopPoint;
import models.Vehicle;

import java.net.URL;
import java.util.ResourceBundle;

import static controllers.Navigator.rideSearch;
import static models.RideStatus.AVAILABLE;

/**
 * Created 22/03/2017.
 */
public class RideDetailsController implements Initializable {

    private SessionManager session = SessionManager.getInstance();
    private Navigator fxml = new Navigator();

    @FXML Label nameText;
    @FXML Label modelText;
    @FXML Label colourText;
    @FXML Label yearText;
    @FXML Label seatsText;
    @FXML Label lengthText;
    @FXML Label stopsText;
    @FXML Label priceText;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Ride ride = session.getFocusedRide();
        Driver driver = ride.getDriver();
        Vehicle vehicle = ride.getVehicle();
        RideStopPoint rideStopPoint = ride.getRideStopPoints().get(0); // initialise default
        for (RideStopPoint rsp : ride.getRideStopPoints()) {
            if (rsp.toString().equals(session.getFocusedStopPoint().toString())) {
                rideStopPoint = rsp;
            }
        }

        nameText.setText(driver.toString());
        modelText.setText(vehicle.getTypeAndModel());
        colourText.setText(vehicle.getColour());
        yearText.setText(Integer.toString(vehicle.getYear()));
        seatsText.setText(Integer.toString(ride.getAvailableSeats()));
        lengthText.setText(rideStopPoint.getStopPoint().getDistanceFromUni() + "m");
        stopsText.setText(Integer.toString(ride.getNumberOfStops()));
        priceText.setText(String.format("$%.2fNZD", rideStopPoint.getPrice()));
    }

    @FXML
    protected void backToDashboard(ActionEvent event) throws Exception {
        fxml.backToDashboard(event);
    }

    @FXML
    protected void backToRideSearch(ActionEvent event) throws Exception {
        fxml.loadScene(rideSearch);
    }

    @FXML
    protected void bookRide(ActionEvent event) throws Exception {
        Ride ride = session.getFocusedRide();
        if (ride.getStatus() == AVAILABLE) {
            ride.addPassenger(session.getCurrentUser());
            fxml.backToDashboard(event);
        } else {
            // TODO error message
        }
    }
}
