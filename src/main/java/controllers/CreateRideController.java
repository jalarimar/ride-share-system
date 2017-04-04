package controllers;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import models.*;

import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Created 22/03/2017.
 */
public class CreateRideController implements Initializable {

    private MainController main = MainController.getInstance();
    private FXMLController fxml = new FXMLController();

    @FXML
    Button dashboardButton;
    @FXML
    Button createButton;
    @FXML
    ChoiceBox carChoice;
    @FXML
    ChoiceBox directionChoice;
    @FXML
    ChoiceBox routeChoice;
    @FXML
    TextField timesField;
    @FXML
    CheckBox mon;
    @FXML
    CheckBox tue;
    @FXML
    CheckBox wed;
    @FXML
    CheckBox thu;
    @FXML
    CheckBox fri;
    @FXML
    CheckBox sat;
    @FXML
    CheckBox sun;
    @FXML
    DatePicker startDatePicker;
    @FXML
    DatePicker endDatePicker;
    @FXML
    Label nStops;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<Vehicle> availableVehicles = FXCollections.observableArrayList();
        availableVehicles.addAll(main.getDriver().getVehicles());
        carChoice.setItems(availableVehicles);

        directionChoice.setItems(FXCollections.observableArrayList("To University", "From University"));

        ObservableList<Route> availableRoutes = FXCollections.observableArrayList();
        availableRoutes.addAll(main.getDriver().getRoutes());
        routeChoice.setItems(availableRoutes);

        routeChoice.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                Route route = (Route) newSelection;
                String nStopPoints = Integer.toString(route.getRoute().size());

                nStops.setText("Stop points: " + nStopPoints);
            }
        });
    }

    @FXML
    protected void backToDashboard(ActionEvent event) throws Exception {
        fxml.backToDashboard(event);
    }

    private List<DayOfWeek> getSelectedDays() {
        List<DayOfWeek> days = new ArrayList<>();
        if (mon.isSelected()) {
            days.add(DayOfWeek.MONDAY);
        }
        if (tue.isSelected()) {
            days.add(DayOfWeek.TUESDAY);
        }
        if (wed.isSelected()) {
            days.add(DayOfWeek.WEDNESDAY);
        }
        if (thu.isSelected()) {
            days.add(DayOfWeek.THURSDAY);
        }
        if (fri.isSelected()) {
            days.add(DayOfWeek.FRIDAY);
        }
        if (sat.isSelected()) {
            days.add(DayOfWeek.SATURDAY);
        }
        if (sun.isSelected()) {
            days.add(DayOfWeek.SUNDAY);
        }
        return days;
    }

    private List<RideStopPoint> mapTimesToStopPoints(Route route) {
        List<String> times = new ArrayList<>();
        String rawInput = timesField.getText();
        for (String time : rawInput.split(",")) {
            String trimmedTime = time.trim();
            if (main.tryParseInt(trimmedTime) > -1 && trimmedTime.length() == 4) {
                times.add(trimmedTime);
            }
        }

        List<StopPoint> stopPoints = route.getRoute();
        if (times.size() == stopPoints.size()) {

            List<RideStopPoint> rsps = new ArrayList<>();

            for (int i = 0; i < stopPoints.size(); i++) {
                StopPoint stopPoint = stopPoints.get(i);
                String time = times.get(i);

                // TODO find day
                String day = "Mon";

                RideStopPoint rideStopPoint = new RideStopPoint(stopPoint, time, day);
                rsps.add(rideStopPoint);
            }
            return rsps;
        } else {
            return null;
        }
    }

    @FXML
    protected void createRide(ActionEvent event) throws Exception {
        // TODO needs testing

        Vehicle vehicle = (Vehicle)carChoice.getSelectionModel().getSelectedItem();

        String direction = directionChoice.getSelectionModel().getSelectedItem().toString();
        boolean isFromUni;
        if (direction == "From University") {
            isFromUni = true;
        } else {
            isFromUni = false;
        }

        List<DayOfWeek> days = getSelectedDays();

        Route route = (Route)routeChoice.getSelectionModel().getSelectedItem();
        List<RideStopPoint> spWithTimes = mapTimesToStopPoints(route);

        if (spWithTimes != null) {

            LocalDate start = startDatePicker.getValue();
            LocalDate end = endDatePicker.getValue();
            boolean isRecurrent = start != end && start != null && end != null && days.size() > 0;

            Driver driver = main.getDriver();
            Ride ride = new Ride(vehicle, driver, spWithTimes, isFromUni, isRecurrent, days, start, end);
            driver.addRide(ride);

            fxml.backToDashboard(event);
        }

    }

}
