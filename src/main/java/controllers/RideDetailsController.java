package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import models.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static controllers.Navigator.*;

/**
 * Created 22/03/2017.
 */
public class RideDetailsController implements Initializable {

    private SessionManager session = SessionManager.getInstance();
    private Navigator fxml = new Navigator();

    @FXML Button bookButton;
    @FXML Label nameText;
    @FXML Label modelText;
    @FXML Label colourText;
    @FXML Label yearText;
    @FXML Label performanceText;
    @FXML Label seatsText;
    @FXML Label lengthText;
    @FXML Label stopsText;
    @FXML Label priceText;
    @FXML Label errorMessage;
    @FXML ImageView img;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Ride ride = session.getFocusedRide();
        Driver driver = ride.getDriver();
        Vehicle vehicle = ride.getVehicle();

        RideStopPoint rideStopPoint = ride.getRideStopPoints().get(0); // default
        for (RideStopPoint rsp : ride.getRideStopPoints()) {
            RideStopPoint r = ride.getRspOfPassenger(session.getCurrentUser());
            if (r != null && rsp.toString().equals(r.toString())) {
                rideStopPoint = rsp;
            }
        }

        nameText.setText(driver.toString());
        modelText.setText(vehicle.getTypeAndModel());
        colourText.setText(vehicle.getColour());
        yearText.setText(Integer.toString(vehicle.getYear()));
        performanceText.setText(Double.toString(vehicle.getPerformance()) + "L/100km");
        seatsText.setText(Integer.toString(ride.getAvailableSeats()));
        lengthText.setText(rideStopPoint.getStopPoint().getDistanceFromUni() * 1000 + "m");
        stopsText.setText(Integer.toString(ride.getNumberOfStops() - ride.getRideStopPoints().indexOf(rideStopPoint)));
        priceText.setText(rideStopPoint.getPriceNZD());

        Image image = new Image("file:" + driver.getPhoto());
        img.setImage(image);

        String previousScene = session.getPreviousScene();
        if (previousScene.equals(myRides) || previousScene.equals(bookedRides)) {
            bookButton.setVisible(false);
        }
        if (previousScene.equals(createRide)) {
            bookButton.setVisible(false);
            errorMessage.setText("Ride has been created.");
        }
    }

    @FXML
    protected void backToDashboard(ActionEvent event) throws Exception {
        fxml.backToDashboard(event);
    }

    @FXML
    protected void backToRideSearch(ActionEvent event) throws Exception {
        if (session.getPreviousScene().equals(bookedRides)) {
            fxml.loadScene(bookedRides);
        } else if (session.getPreviousScene().equals(myRides)) {
            fxml.loadScene(myRides);
        } else if (session.getPreviousScene().equals(rideSearch)) {
            fxml.loadScene(rideSearch);
        } else {
            fxml.backToDashboard(event);
        }
    }

    public boolean tryBookRide() {
        Ride ride = session.getFocusedRide();
        User user = session.getCurrentUser();
        StopPoint stopPoint = session.getFocusedStopPoint();
        if (ride.isAllowedToBookRide(user)) {
            if (user.getTrackedRideIds() == null) {
                user.addBooking(ride);
            } else if (!user.getTrackedRideIds().contains(ride.getId())) {
                user.addBooking(ride);
            }
            RideStopPoint rideStopPoint = ride.getRideStopPoints().get(0);
            for (RideStopPoint rsp : ride.getRideStopPoints()) {
                if (stopPoint != null && rsp.toString().equals(stopPoint.toString())) {
                    rideStopPoint = rsp;
                }
            }
            ride.addPassenger(user, rideStopPoint);
            return true;
        } else {
            return false;
        }
    }

    @FXML
    protected void bookRide(ActionEvent event) throws Exception {

        boolean isAllowed = tryBookRide();
        if (isAllowed) {
            fxml.backToDashboard(event);
        } else {
            errorMessage.setText("You cannot book this ride.");
        }
    }
}
