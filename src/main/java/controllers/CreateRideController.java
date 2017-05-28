package controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import models.*;

import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static controllers.Converter.getReadableDate;
import static controllers.Converter.getTimeFromString;
import static controllers.Navigator.createRide;
import static controllers.Navigator.viewRide;
import static controllers.Validator.tryParseInt;
import static controllers.Validator.validTimes;

/**
 * Created 22/03/2017.
 */
public class CreateRideController implements Initializable {

    private SessionManager session = SessionManager.getInstance();
    private Navigator fxml = new Navigator();

    @FXML ChoiceBox carChoice;
    @FXML ChoiceBox directionChoice;
    @FXML ChoiceBox routeChoice;
    @FXML TextField timesField;
    @FXML CheckBox recurrentCheck;
    @FXML CheckBox mon;
    @FXML CheckBox tue;
    @FXML CheckBox wed;
    @FXML CheckBox thu;
    @FXML CheckBox fri;
    @FXML CheckBox sat;
    @FXML CheckBox sun;
    @FXML DatePicker startDatePicker;
    @FXML DatePicker endDatePicker;
    @FXML Label endLabel;
    @FXML Label nStops;
    @FXML Label errorMessage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<Vehicle> availableVehicles = FXCollections.observableArrayList();
        availableVehicles.addAll(session.getCurrentDriver().getVehicles());
        carChoice.setItems(availableVehicles);

        directionChoice.setItems(FXCollections.observableArrayList("To University", "From University"));

        ObservableList<Route> availableRoutes = FXCollections.observableArrayList();
        availableRoutes.addAll(session.getCurrentDriver().getRoutes());
        routeChoice.setItems(availableRoutes);

        routeChoice.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                Route route = (Route) newSelection;
                String nStopPoints = Integer.toString(route.getRoute().size());

                nStops.setText("Stop points: " + nStopPoints);
            }
        });
        mon.setVisible(false);
        tue.setVisible(false);
        wed.setVisible(false);
        thu.setVisible(false);
        fri.setVisible(false);
        sat.setVisible(false);
        sun.setVisible(false);
        endDatePicker.setVisible(false);
        endLabel.setVisible(false);

        recurrentCheck.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                mon.setVisible(newValue);
                tue.setVisible(newValue);
                wed.setVisible(newValue);
                thu.setVisible(newValue);
                fri.setVisible(newValue);
                sat.setVisible(newValue);
                sun.setVisible(newValue);
                endDatePicker.setVisible(newValue);
                endLabel.setVisible(newValue);
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

    private boolean moreThan5MinsApart(List<LocalDateTime> times) {
        LocalDateTime lastTime = LocalDateTime.now();
        for (LocalDateTime time : times) {
            if (time.compareTo(lastTime.plusMinutes(5)) < 0) {
                return false;
            }
            lastTime = time;
        }
        return true;
    }

    private boolean dateAfterExpiry(LocalDate date) {
        Driver driver = SessionManager.getInstance().getCurrentDriver();
        Vehicle vehicle = (Vehicle)carChoice.getSelectionModel().getSelectedItem();
        LocalDate licenceExpiry = driver.getLicence().getExpiryDate();
        LocalDate regExpiry = vehicle.getRegExpiry();
        LocalDate wofExpiry = vehicle.getWofExpiry();

        return (date.compareTo(licenceExpiry) > 0 || date.compareTo(regExpiry) > 0 || date.compareTo(wofExpiry) > 0);
    }

    public List<RideStopPoint> mapTimesToStopPoints(Route route, LocalDate date, String rawInput) {
        List<StopPoint> stopPoints = route.getRoute();
        LocalDateTime earliestTime = LocalDateTime.now().plusHours(1);

        if (!validTimes(date, rawInput)) {
            return null;
        }
        List<LocalDateTime> times = new ArrayList<>();
        for (String time : rawInput.split(",")) {
            LocalDateTime thyme = getTimeFromString(getReadableDate(date) + time.trim());
            if (date.compareTo(LocalDate.now()) > 0 || thyme.compareTo(earliestTime) > 0) {
                times.add(thyme);
            }
        }
        if (!moreThan5MinsApart(times)) {
            return null;
        }
        if (dateAfterExpiry(date)) {
            return null;
        }
        if (times.size() != stopPoints.size()) {
            return null;
        }

        List<RideStopPoint> rsps = new ArrayList<>();
        for (int i = 0; i < stopPoints.size(); i++) {
            StopPoint stopPoint = stopPoints.get(i);
            LocalDateTime time = times.get(i);

            RideStopPoint rideStopPoint = new RideStopPoint(stopPoint, time, date);
            rsps.add(rideStopPoint);
        }
        return rsps;
    }

    @FXML
    protected void createRide(ActionEvent event) throws Exception {
        Driver driver = session.getCurrentDriver();
        Vehicle vehicle = (Vehicle)carChoice.getSelectionModel().getSelectedItem();
        String direction = directionChoice.getSelectionModel().getSelectedItem().toString();
        Route route = (Route)routeChoice.getSelectionModel().getSelectedItem();
        LocalDate startDate = startDatePicker.getValue() != null ? startDatePicker.getValue() : LocalDate.now();
        LocalDate endDate = endDatePicker.getValue();
        List<DayOfWeek> days = getSelectedDays();
        boolean isRecurrent = recurrentCheck.isSelected() && endDate != null && startDate.compareTo(endDate) < 0 && days.size() > 0;

        boolean isFromUni;
        if (direction.equals("From University")) {
            isFromUni = true;
        } else {
            isFromUni = false;
        }
        TripDetails trip = new TripDetails(vehicle.getLicensePlate(), driver, isFromUni, isRecurrent, days, endDate);

        boolean failed = false;
        Ride ride = null;
        if (isRecurrent) {

            // generate rides
            while (startDate.compareTo(endDate) <= 0) {
                if (days.contains(startDate.getDayOfWeek())) {
                    List<RideStopPoint> spWithTimes = mapTimesToStopPoints(route, startDate, timesField.getText());
                    if (spWithTimes != null) {
                        ride = new Ride(trip, spWithTimes);
                        driver.addRide(ride);
                    } else {
                        errorMessage.setText("Check your stop point times and dates");
                        failed = true;
                    }
                }
                startDate = startDate.plusDays(1);
            }
        } else {
            List<RideStopPoint> spWithTimes = mapTimesToStopPoints(route, startDate, timesField.getText());
            if (spWithTimes != null) {
                ride = new Ride(trip, spWithTimes);
                driver.addRide(ride);
            } else {
                errorMessage.setText("Check your stop point times and dates");
                failed = true;
            }
        }
        if (!failed) {
            SessionManager.getInstance().setFocusedRide(ride);
            SessionManager.getInstance().setPreviousScene(createRide);
            fxml.loadScene(viewRide);
        }
    }
}
