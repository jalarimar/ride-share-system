package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import models.StopPoint;

import java.util.List;

import static controllers.FXMLNavigator.*;

/**
 * Created 22/03/2017.
 */
public class DriverDashController {

    private SessionManager main = SessionManager.getInstance();
    private FXMLNavigator fxml = new FXMLNavigator();

    @FXML
    Button registerCarButton;
    @FXML
    Button stopPointButton;
    @FXML
    Button routeButton;
    @FXML
    Button rideButton;
    @FXML
    Button myRidesButton;
    @FXML
    Button searchScreenButton;

    // for testing purposes only, should use initialise instead, see CreateRouteController
    public DriverDashController() {
        System.out.println(main.getCurrentDriver().getVehicles());
        List<StopPoint> stopPoints = main.getCurrentDriver().getStopPoints();
        for (StopPoint sp : stopPoints) {
            System.out.println(sp.getAddress());
        }
    }

    @FXML
    protected void loadCreateCar(ActionEvent event) throws Exception {
        fxml.loadScene(createCar);
    }

    @FXML
    protected void loadCreateSp(ActionEvent event) throws Exception {
        fxml.loadScene(createSp);
    }

    @FXML
    protected void loadCreateRoute(ActionEvent event) throws Exception {
        fxml.loadScene(createRoute);
    }

    @FXML
    protected void loadCreateRide(ActionEvent event) throws Exception {
        fxml.loadScene(createRide);
    }

    @FXML
    protected void loadDriverRides(ActionEvent event) throws Exception {
        fxml.loadScene(myRides);
    }

    @FXML
    protected void loadSearchScreen(ActionEvent event) throws Exception {
        fxml.loadScene(spSearch);
    }


}
