package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import models.Ride;
import models.Status;

import java.net.URL;
import java.util.ResourceBundle;

import static controllers.Serializer.saveRss;

/**
 * Created 22/03/2017.
 */
public class DriverRidesController implements Initializable {

    private SessionManager session = SessionManager.getInstance();
    private FXMLNavigator fxml = new FXMLNavigator();
    ObservableList<Integer> availableSeats;

    @FXML
    Button dashboardButton;
    @FXML
    Button shareButton;
    @FXML
    ListView unsharedRideList;
    @FXML
    ChoiceBox availableSeatsChoice;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<Ride> unsharedRides = FXCollections.observableArrayList();
        for (Ride ride : session.getCurrentDriver().getRides()) {
            if (ride.getStatus() == Status.UNSHARED) {
                unsharedRides.add(ride);
            }
        }
        unsharedRideList.setItems(unsharedRides);

        unsharedRideList.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                Ride ride = (Ride) newSelection;
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
    protected void shareRide(ActionEvent event) throws Exception {
        // TODO
        Ride ride = (Ride)unsharedRideList.getSelectionModel().getSelectedItem();
        ride.setAvailableSeats((int)availableSeatsChoice.getSelectionModel().getSelectedItem());
        ride.setStatus(Status.AVAILABLE);
        session.getRss().saveModifiedRide(ride);
        saveRss(session.getRss());

        fxml.backToDashboard(event);
    }


}
