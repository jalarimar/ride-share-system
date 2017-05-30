package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import models.*;
import utilities.Navigator;
import utilities.SessionManager;

import java.net.URL;
import java.util.*;

import static utilities.Navigator.rideSearch;
import static utilities.Navigator.spSearch;
import static utilities.Navigator.viewRide;

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
    @FXML RadioButton toUni;
    @FXML RadioButton fromUni;
    @FXML RadioButton both;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ToggleGroup direction = new ToggleGroup();
        toUni.setToggleGroup(direction);
        fromUni.setToggleGroup(direction);
        both.setToggleGroup(direction);
        both.setSelected(true);

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

    @FXML
    protected void showFrom(ActionEvent event) throws Exception {
        visibleRidesForThisStopPoint.clear();
        List<Ride> allRides = Rss.getInstance().getAvailableRides();
        StopPoint here = session.getFocusedStopPoint();

        for (Ride ride : allRides) {
            for (RideStopPoint r : ride.getRideStopPoints()) {
                if (r.getStopPoint().equals(here) && ride.getDirection().equals("From University")) {
                    visibleRidesForThisStopPoint.add(r);
                }
            }
        }
        Collections.sort(visibleRidesForThisStopPoint, Comparator.comparing(RideStopPoint::getRawTime));
    }

    @FXML
    protected void showTo(ActionEvent event) throws Exception {
        visibleRidesForThisStopPoint.clear();
        List<Ride> allRides = Rss.getInstance().getAvailableRides();
        StopPoint here = session.getFocusedStopPoint();

        for (Ride ride : allRides) {
            for (RideStopPoint r : ride.getRideStopPoints()) {
                if (r.getStopPoint().equals(here) && ride.getDirection().equals("To University")) {
                    visibleRidesForThisStopPoint.add(r);
                }
            }
        }
        Collections.sort(visibleRidesForThisStopPoint, Comparator.comparing(RideStopPoint::getRawTime));
    }

    @FXML
    protected void showBoth(ActionEvent event) throws Exception {
        visibleRidesForThisStopPoint.clear();
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
    }

    @FXML
    protected void bookRide(ActionEvent event) throws Exception {
        RideStopPoint rideStopPoint = (RideStopPoint)rideTable.getSelectionModel().getSelectedItem();
        User user = session.getCurrentUser();
        if (rideStopPoint != null) {
            Ride ride = rideStopPoint.getRide();
            if (ride.isAllowedToBookRide(user)) {
                if (user.getTrackedRideIds() == null) {
                    user.addBooking(ride);
                } else if (!user.getTrackedRideIds().contains(ride.getId())) {
                    user.addBooking(ride);
                }
                ride.addPassenger(user, rideStopPoint);
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
            session.setPreviousScene(rideSearch);
            fxml.loadScene(viewRide);
        }
    }

}
