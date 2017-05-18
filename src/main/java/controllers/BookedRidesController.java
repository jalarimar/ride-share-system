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
import java.util.Collections;
import java.util.Comparator;
import java.util.ResourceBundle;
import java.util.UUID;

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        User user = SessionManager.getInstance().getCurrentUser();
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

    }

    @FXML
    protected void backToDashboard(ActionEvent event) throws Exception {
        fxml.backToDashboard(event);
    }

    @FXML
    protected void cancelRide(ActionEvent event) throws Exception {
        /* TODO turn into cancel ride
        Ride ride = (Ride)unsharedRideList.getSelectionModel().getSelectedItem();
        ride.setAvailableSeats((int)availableSeatsChoice.getSelectionModel().getSelectedItem());
        ride.setStatus(RideStatus.AVAILABLE);

        fxml.backToDashboard(event);
        */
    }

    @FXML
    protected void viewRide(ActionEvent event) throws Exception {
        Ride ride = bookedRidesTable.getSelectionModel().getSelectedItem();
        if (ride != null) {
            session.setFocusedRide(ride);
            fxml.loadScene(viewRide);
        }
    }
}
