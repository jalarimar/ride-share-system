package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import models.Driver;
import models.StopPoint;
import models.Vehicle;

import java.util.List;

/**
 * Created 22/03/2017.
 */
public class DriverDashController {

    private MainController main = MainController.getInstance();
    private FXMLController fxml = new FXMLController();

    private final String createCar = "/createvehicle.fxml";
    private final String createSp = "/createstoppoint.fxml";
    private final String createRoute = "/createroute.fxml";
    private final String createRide = "/createride.fxml";
    private final String myRides = "/driversrides.fxml";
    private final String spSearch = "/stoppointsearch.fxml";

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
        System.out.println(main.getDriver().getVehicles());
        List<StopPoint> stopPoints = main.getDriver().getStopPoints();
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
