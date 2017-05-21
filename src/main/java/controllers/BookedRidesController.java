package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import models.*;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.*;

import static controllers.Navigator.bookedRides;
import static controllers.Navigator.viewRide;

/**
 * Created 22/03/2017.
 */
public class BookedRidesController implements Initializable {

    private SessionManager session = SessionManager.getInstance();
    private Navigator fxml = new Navigator();

    @FXML TableView<Ride> bookedRidesTable;
    @FXML TableColumn dateCol;
    @FXML TableColumn nameCol;
    @FXML TableColumn driverCol;
    @FXML TableColumn statusCol;
    @FXML Button detailsButton;
    @FXML Button cancelButton;
    @FXML Button backButton;
    @FXML Button confirmButton;
    @FXML Label reasonLabel;
    @FXML Label warningLabel;
    @FXML TextField reasonField;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        User user = session.getCurrentUser();
        ObservableList<Ride> bookedRides = FXCollections.observableArrayList();
        for (UUID rideId : user.getBookedRideIds()) {
            bookedRides.add(Rss.getInstance().getRideById(rideId));
        }

        Collections.sort(bookedRides, Comparator.comparing(Ride::getTime));
        bookedRidesTable.setItems(bookedRides);

        dateCol.setCellValueFactory(new PropertyValueFactory<Ride,String>("humanDate"));
        nameCol.setCellValueFactory(new PropertyValueFactory<Ride,String>("name"));
        driverCol.setCellValueFactory(new PropertyValueFactory<Ride,String>("driverId"));
        statusCol.setCellValueFactory(new PropertyValueFactory<Ride,String>("bookingStatus"));

        bookedRidesTable.getColumns().setAll(dateCol, nameCol, driverCol, statusCol);

        reasonLabel.setVisible(false);
        reasonField.setVisible(false);
        warningLabel.setVisible(false);
        confirmButton.setVisible(false);
        backButton.setVisible(false);
    }

    @FXML
    protected void backToDashboard(ActionEvent event) throws Exception {
        fxml.backToDashboard(event);
    }

    @FXML
    protected void askForReason(ActionEvent event) throws Exception {
        Ride ride = bookedRidesTable.getSelectionModel().getSelectedItem();
        if (ride == null) {
            warningLabel.setText("Please select a ride.");
            warningLabel.setVisible(true);
        } else {
            if (!ride.isCancellableByPassenger()) {
                warningLabel.setText("This booking cannot be cancelled.");
                warningLabel.setVisible(true);
            } else {
                detailsButton.setDisable(true);
                cancelButton.setDisable(true);
                reasonLabel.setVisible(true);
                reasonField.setVisible(true);
                confirmButton.setVisible(true);
                backButton.setVisible(true);

                if (ride.getTime().compareTo(LocalDateTime.now().plusHours(2)) < 0) {
                    warningLabel.setText("Less than 2 hours before ride. You may not be well evaluated.");
                    warningLabel.setVisible(true);
                } else {
                    warningLabel.setVisible(false);
                }
            }
        }
    }

    @FXML
    protected void cancelCancel(ActionEvent event) throws Exception {
        detailsButton.setDisable(false);
        cancelButton.setDisable(false);
        reasonLabel.setVisible(false);
        reasonField.setVisible(false);
        confirmButton.setVisible(false);
        warningLabel.setVisible(false);
        backButton.setVisible(false);
    }

    @FXML
    protected void cancelRide(ActionEvent event) throws Exception {
        Ride ride = bookedRidesTable.getSelectionModel().getSelectedItem();
        User user = session.getCurrentUser();
        if (ride.getPassengers().contains(user)) {
            ride.removePassenger(user);

            String notification = "A passenger has cancelled their booking for ride: " + ride.getName() + " with reason: " + reasonField.getText();
            ride.getDriver().setUnseenRideNotification(notification);
        }
        fxml.backToDashboard(event);
    }

    @FXML
    protected void viewRide(ActionEvent event) throws Exception {
        Ride ride = bookedRidesTable.getSelectionModel().getSelectedItem();
        if (ride != null) {
            session.setFocusedRide(ride);
            session.setPreviousScene(bookedRides);
            fxml.loadScene(viewRide);
        }
    }
}
