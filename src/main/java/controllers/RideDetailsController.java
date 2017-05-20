package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import models.*;

import java.net.URL;
import java.util.ResourceBundle;

import static controllers.Navigator.bookedRides;
import static controllers.Navigator.rideSearch;
import static models.RideStatus.AVAILABLE;

/**
 * Created 22/03/2017.
 */
public class RideDetailsController implements Initializable {

    private SessionManager session = SessionManager.getInstance();
    private Navigator fxml = new Navigator();
    private boolean cameFromBookedRidesPage;

    @FXML Label nameText;
    @FXML Label modelText;
    @FXML Label colourText;
    @FXML Label yearText;
    @FXML Label seatsText;
    @FXML Label lengthText;
    @FXML Label stopsText;
    @FXML Label priceText;
    @FXML Label errorMessage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Ride ride = session.getFocusedRide();
        Driver driver = ride.getDriver();
        Vehicle vehicle = ride.getVehicle();

        RideStopPoint rideStopPoint = ride.getRideStopPoints().get(0); // initialise default
        for (RideStopPoint rsp : ride.getRideStopPoints()) {
            if (session.getFocusedStopPoint() != null && rsp.toString().equals(session.getFocusedStopPoint().toString())) {
                rideStopPoint = rsp;
            } else if (session.getFocusedRide() != null) {
                // TODO should probably be the ridestoppoint the user has booked for price
                rideStopPoint = session.getFocusedRide().getRideStopPoints().get(0);
                cameFromBookedRidesPage = true;
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
        if (cameFromBookedRidesPage) {
            fxml.loadScene(bookedRides);
        } else {
            fxml.loadScene(rideSearch);
        }
    }

    // TODO should be in the model
    private boolean userIsAllowedToBookRide(User user, Ride ride) {
        boolean hasSeatsAvailable = ride.getStatus() == AVAILABLE;
        boolean isAlreadyBooked = ride.getBookingStatus().equals(BookingStatus.BOOKED.toString());
        boolean isFinished = ride.getBookingStatus().equals(BookingStatus.DONE.toString());
        boolean isDriver = ride.getDriver().getUniID().equals(user.getUniID());
        return hasSeatsAvailable && !isAlreadyBooked && !isFinished && !isDriver;
    }

    @FXML
    protected void bookRide(ActionEvent event) throws Exception {
        Ride ride = session.getFocusedRide();
        User user = session.getCurrentUser();
        if (userIsAllowedToBookRide(user, ride)) {
            if (!user.getBookedRideIds().contains(ride.getId())) {
                user.addBooking(ride);
            }
            ride.addPassenger(user);
            fxml.backToDashboard(event);
        } else {
            errorMessage.setText("You cannot book this ride.");
        }
    }
}
