package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Driver;
import models.Ride;
import enums.RideStatus;
import models.User;
import utilities.Navigator;
import utilities.SessionManager;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.ResourceBundle;

import static utilities.Navigator.myRides;
import static utilities.Navigator.viewRide;

/**
 * Created 22/03/2017.
 */
public class DriverRidesController implements Initializable {

    private SessionManager session = SessionManager.getInstance();
    private Navigator fxml = new Navigator();
    ObservableList<Integer> availableSeats;

    @FXML TableView<Ride> myRidesTable;
    @FXML TableColumn dateCol;
    @FXML TableColumn nameCol;
    @FXML TableColumn statusCol;
    @FXML Label availableLabel;
    @FXML ChoiceBox availableSeatsChoice;
    @FXML Button cancelButton;
    @FXML Button shareButton;
    @FXML Button backButton;
    @FXML Button confirmShare;
    @FXML Button confirmCancel;
    @FXML Label reasonLabel;
    @FXML Label warningLabel;
    @FXML TextField reasonField;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<Ride> rides = FXCollections.observableArrayList();
        rides.addAll(session.getCurrentDriver().getMyRides());

        Collections.sort(rides, Comparator.comparing(Ride::getTime));
        myRidesTable.setItems(rides);

        dateCol.setCellValueFactory(new PropertyValueFactory<Ride,String>("humanDate"));
        nameCol.setCellValueFactory(new PropertyValueFactory<Ride,String>("name"));
        statusCol.setCellValueFactory(new PropertyValueFactory<Ride,String>("status"));

        myRidesTable.getColumns().setAll(dateCol, nameCol, statusCol);

        myRidesTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                Ride ride = newSelection;
                int maxSeats = ride.getVehicle().getPhysicalSeats();

                availableSeats = FXCollections.observableArrayList();
                for (int i = 1; i <= maxSeats; i++) {
                    availableSeats.add(i);
                }
                availableSeatsChoice.setItems(availableSeats);
            }
        });

        reasonLabel.setVisible(false);
        reasonField.setVisible(false);
        warningLabel.setVisible(false);
        confirmCancel.setVisible(false);
        backButton.setVisible(false);

        availableLabel.setVisible(false);
        availableSeatsChoice.setVisible(false);
        confirmShare.setVisible(false);
    }

    @FXML
    protected void backToDashboard(ActionEvent event) throws Exception {
        fxml.backToDashboard(event);
    }

    @FXML
    protected void askForSeats(ActionEvent event) throws Exception {
        Ride ride = myRidesTable.getSelectionModel().getSelectedItem();
        if (ride == null) {
            warningLabel.setText("Please select a ride.");
            warningLabel.setVisible(true);
        } else {
            if (ride.isShareable()) {
                cancelButton.setDisable(true);
                shareButton.setDisable(true);

                reasonLabel.setVisible(false);
                reasonField.setVisible(false);
                warningLabel.setVisible(false);
                confirmCancel.setVisible(false);
                backButton.setVisible(true);

                availableLabel.setVisible(true);
                availableSeatsChoice.setVisible(true);
                confirmShare.setVisible(true);
            } else {
                warningLabel.setText("This ride cannot be shared.");
                warningLabel.setVisible(true);
            }
        }
    }

    @FXML
    protected void shareRide(ActionEvent event) throws Exception {
        Ride ride = myRidesTable.getSelectionModel().getSelectedItem();
        ride.setAvailableSeats((int)availableSeatsChoice.getSelectionModel().getSelectedItem());
        ride.setStatus(RideStatus.AVAILABLE);

        fxml.backToDashboard(event);
    }

    @FXML
    protected void askForReason(ActionEvent event) throws Exception {
        Ride ride = myRidesTable.getSelectionModel().getSelectedItem();
        if (ride == null) {
            warningLabel.setText("Please select a ride.");
            warningLabel.setVisible(true);
        } else {
            if (!ride.isCancellableByDriver()) {
                warningLabel.setText("This ride cannot be cancelled.");
                warningLabel.setVisible(true);
            } else {
                cancelButton.setDisable(true);
                shareButton.setDisable(true);
                availableLabel.setVisible(false);
                availableSeatsChoice.setVisible(false);
                confirmShare.setVisible(false);

                reasonLabel.setVisible(true);
                reasonField.setVisible(true);
                confirmCancel.setVisible(true);
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
    protected void cancelRide(ActionEvent event) throws Exception {
        Ride ride = myRidesTable.getSelectionModel().getSelectedItem();
        Driver driver = session.getCurrentDriver();
        if (driver != null && ride.isCancellableByDriver()) {
            ride.setStatus(RideStatus.CANCELLED);

            for (User passenger : ride.getPassengers()) {
                String notification = "Ride: " + ride.getName() + " has been cancelled with reason: " + reasonField.getText();
                passenger.setUnseenRideNotification(notification);
            }
        }
        fxml.backToDashboard(event);
    }

    @FXML
    protected void cancelCancel(ActionEvent event) throws Exception {
        cancelButton.setDisable(false);
        shareButton.setDisable(false);

        reasonLabel.setVisible(false);
        reasonField.setVisible(false);
        warningLabel.setVisible(false);
        confirmCancel.setVisible(false);
        backButton.setVisible(false);

        availableLabel.setVisible(false);
        availableSeatsChoice.setVisible(false);
        confirmShare.setVisible(false);
    }

    @FXML
    protected void viewRide(ActionEvent event) throws Exception {
        Ride ride = myRidesTable.getSelectionModel().getSelectedItem();
        if (ride != null) {
            session.setFocusedRide(ride);
            session.setPreviousScene(myRides);
            fxml.loadScene(viewRide);
        }
    }
}
