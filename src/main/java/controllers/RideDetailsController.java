package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import models.Driver;
import models.Ride;
import models.Status;
import models.Vehicle;

import java.net.URL;
import java.util.ResourceBundle;

import static controllers.FXMLNavigator.rideSearch;

/**
 * Created 22/03/2017.
 */
public class RideDetailsController implements Initializable {

    private SessionManager main = SessionManager.getInstance();
    private FXMLNavigator fxml = new FXMLNavigator();

    @FXML
    Button dashboardButton;
    @FXML
    Button bookButton;
    @FXML
    Button backButton;
    @FXML
    Label nameText;
    @FXML
    Label modelText;
    @FXML
    Label colourText;
    @FXML
    Label yearText;
    @FXML
    Label seatsText;
    @FXML
    Label lengthText;
    @FXML
    Label stopsText;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Ride ride = main.getFocusedRide();
        Driver driver = ride.getDriver();
        Vehicle vehicle = ride.getVehicle();

        nameText.setText(driver.toString());
        modelText.setText(vehicle.getTypeAndModel());
        colourText.setText(vehicle.getColour());
        yearText.setText(Integer.toString(vehicle.getYear()));
        seatsText.setText(Integer.toString(ride.getAvailableSeats()));
        lengthText.setText("Unknown");
        stopsText.setText(Integer.toString(ride.getNumberOfStops()));
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
        Ride ride = main.getFocusedRide();
        ride.addPassenger(main.getCurrentUser());
        if (ride.getStatus() == Status.FULL) {
            main.getSharedRides().remove(ride);
        }

        fxml.backToDashboard(event);
    }
}
