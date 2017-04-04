package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Ride;
import models.StopPoint;

import java.net.URL;
import java.util.Collections;
import java.util.ResourceBundle;

/**
 * Created 22/03/2017.
 */
public class SearchRideController implements Initializable {

    private MainController main = MainController.getInstance();
    private FXMLController fxml = new FXMLController();

    private final String viewRide = "/viewride.fxml";

    private ObservableList<Ride> visibleRides = FXCollections.observableArrayList();

    @FXML
    Button dashboardButton;
    @FXML
    Button bookButton;
    @FXML
    Button viewButton;
    @FXML
    TableView rideTable;
    @FXML
    TableColumn dayCol;
    @FXML
    TableColumn timeCol;
    @FXML
    TableColumn directionCol;
    @FXML
    TableColumn seatCol;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        visibleRides.addAll(main.getSharedRides());

        // TODO sort by day and time
        //Collections.sort(visibleRides, (Ride r1, Ride r2) -> r1.?.compareTo(r2.?));

        rideTable.setItems(visibleRides);

        dayCol.setCellValueFactory(new PropertyValueFactory<Ride,String>("day"));
        timeCol.setCellValueFactory(new PropertyValueFactory<Ride,String>("time")); // StopPointOfRide ?
        directionCol.setCellValueFactory(new PropertyValueFactory<Ride,String>("direction"));
        seatCol.setCellValueFactory(new PropertyValueFactory<Ride,Integer>("availableSeats"));

        rideTable.getColumns().setAll(dayCol, timeCol, directionCol, seatCol);

    }

    @FXML
    protected void backToDashboard(ActionEvent event) throws Exception {
        fxml.backToDashboard(event);
    }

    @FXML
    protected void bookRide(ActionEvent event) throws Exception {
        Ride ride = (Ride)rideTable.getSelectionModel().getSelectedItem();
        // TODO
    }

    // TODO attach

    @FXML
    protected void viewRide(ActionEvent event) throws Exception {
        Ride ride = (Ride)rideTable.getSelectionModel().getSelectedItem();
        main.setFocusedRide(ride);
        try {
            fxml.loadScene(viewRide);
        } catch (Exception ex) {
            System.out.println("Load Scene Failed");
        }
    }

}
