package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.*;

import java.net.URL;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

import static controllers.Navigator.spSearch;
import static controllers.Navigator.viewRide;
import static models.RideStatus.AVAILABLE;

/**
 * Created 22/03/2017.
 */
public class SearchRideController implements Initializable {

    private SessionManager session = SessionManager.getInstance();
    private Navigator fxml = new Navigator();

    private ObservableList<RideStopPoint> visibleRidesForThisStopPoint = FXCollections.observableArrayList();

    @FXML TableView rideTable;
    @FXML TableColumn dateCol;
    @FXML TableColumn dayCol;
    @FXML TableColumn timeCol;
    @FXML TableColumn directionCol;
    @FXML TableColumn seatCol;
    @FXML Label errorMessage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        List<Ride> allRides = Rss.getInstance().getAvailableRides();
        StopPoint here = session.getFocusedStopPoint();

        for (Ride ride : allRides) {
            for (RideStopPoint r : ride.getRideStopPoints()) {
                if (r.getStopPoint().equals(here)) {
                    visibleRidesForThisStopPoint.add(r);
                }
            }
        }

        Collections.sort(visibleRidesForThisStopPoint, Comparator.comparing(RideStopPoint::getRawTime));

        rideTable.setItems(visibleRidesForThisStopPoint);

        dateCol.setCellValueFactory(new PropertyValueFactory<RideStopPoint,String>("humanDate"));
        dayCol.setCellValueFactory(new PropertyValueFactory<RideStopPoint,String>("day"));
        timeCol.setCellValueFactory(new PropertyValueFactory<RideStopPoint,String>("time"));
        directionCol.setCellValueFactory(new PropertyValueFactory<RideStopPoint,String>("direction"));
        seatCol.setCellValueFactory(new PropertyValueFactory<RideStopPoint,Integer>("availableSeats"));

        rideTable.getColumns().setAll(dateCol, dayCol, timeCol, directionCol, seatCol);

    }

    @FXML
    protected void backToDashboard(ActionEvent event) throws Exception {
        fxml.backToDashboard(event);
    }

    @FXML
    protected void backToSpSearch(ActionEvent event) throws Exception {
        fxml.loadScene(spSearch);
    }

    private boolean userIsAllowedToBookRide(User user, Ride ride) {
        boolean hasSeatsAvailable = ride.getStatus() == AVAILABLE;
        boolean isAlreadyBooked = ride.getBookingStatus().equals(BookingStatus.BOOKED.toString());
        boolean isFinished = ride.getBookingStatus().equals(BookingStatus.DONE.toString());
        boolean isDriver = ride.getDriver().getUniID().equals(user.getUniID());
        return hasSeatsAvailable && !isAlreadyBooked && !isFinished && !isDriver;
    }

    @FXML
    protected void bookRide(ActionEvent event) throws Exception {
        RideStopPoint rideStopPoint = (RideStopPoint)rideTable.getSelectionModel().getSelectedItem();
        User user = session.getCurrentUser();
        if (rideStopPoint != null) {
            Ride ride = rideStopPoint.getRide();
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

    @FXML
    protected void viewRide(ActionEvent event) throws Exception {
        RideStopPoint rideStopPoint = (RideStopPoint)rideTable.getSelectionModel().getSelectedItem();
        if (rideStopPoint != null) {
            session.setFocusedRide(rideStopPoint.getRide());
            try {
                fxml.loadScene(viewRide);
            } catch (Exception ex) {
                System.out.println("Load Scene Failed");
            }
        }
    }

}
