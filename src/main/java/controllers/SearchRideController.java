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
import models.RideStopPoint;
import models.Rss;
import models.StopPoint;

import java.net.URL;
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
    @FXML TableColumn dayCol;
    @FXML TableColumn timeCol;
    @FXML TableColumn directionCol;
    @FXML TableColumn seatCol;

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

        // TODO sort by day and time
        //Collections.sort(visibleRides, (Ride r1, Ride r2) -> r1.?.compareTo(r2.?));

        rideTable.setItems(visibleRidesForThisStopPoint);

        dayCol.setCellValueFactory(new PropertyValueFactory<RideStopPoint,String>("day"));
        timeCol.setCellValueFactory(new PropertyValueFactory<RideStopPoint,String>("time"));
        directionCol.setCellValueFactory(new PropertyValueFactory<RideStopPoint,String>("direction"));
        seatCol.setCellValueFactory(new PropertyValueFactory<RideStopPoint,Integer>("availableSeats"));

        rideTable.getColumns().setAll(dayCol, timeCol, directionCol, seatCol);

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
    protected void bookRide(ActionEvent event) throws Exception {
        RideStopPoint rideStopPoint = (RideStopPoint)rideTable.getSelectionModel().getSelectedItem();
        if (rideStopPoint != null) {
            Ride ride = rideStopPoint.getRide();
            if (ride.getStatus() == AVAILABLE) {
                ride.addPassenger(session.getCurrentUser());
                fxml.backToDashboard(event);
            } else {
                // TODO error message
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
