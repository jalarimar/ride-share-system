package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import models.*;
import utilities.Navigator;
import utilities.SessionManager;

import java.net.URL;
import java.util.ResourceBundle;

import static utilities.Navigator.*;

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

        RideStopPoint rideStopPoint = ride.getRideStopPointOfPassenger(session.getCurrentUser());

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
        switch (session.getPreviousScene()) {
            case bookedRides:
                fxml.loadScene(bookedRides);
                break;
            case myRides:
                fxml.loadScene(myRides);
                break;
            case rideSearch:
                fxml.loadScene(rideSearch);
                break;
            default:
                fxml.backToDashboard(event);
                break;
        }
    }

    @FXML
    protected void bookRide(ActionEvent event) throws Exception {
        boolean isAllowed = session.getFocusedRide().tryBookRide();
        if (isAllowed) {
            fxml.backToDashboard(event);
        } else {
            errorMessage.setText("You cannot book this ride.");
        }
    }
}
