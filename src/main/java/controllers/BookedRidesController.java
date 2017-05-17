package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import models.Ride;
import models.RideStatus;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created 22/03/2017.
 */
public class BookedRidesController implements Initializable {

    private SessionManager session = SessionManager.getInstance();
    private Navigator fxml = new Navigator();
    ObservableList<Integer> availableSeats;

    @FXML TableView<Ride> bookedRidesTable;
    @FXML ChoiceBox availableSeatsChoice;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<Ride> unsharedRides = FXCollections.observableArrayList();
        for (Ride ride : session.getCurrentDriver().getMyRides()) {
            if (ride.getStatus() == RideStatus.UNSHARED) {
                unsharedRides.add(ride);
            }
        }
        bookedRidesTable.setItems(unsharedRides);

        bookedRidesTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
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


}
