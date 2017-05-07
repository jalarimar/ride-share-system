package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.User;

/**
 * Created 22/03/2017.
 */
public class FXMLNavigator {

    private SessionManager session = SessionManager.getInstance();

    public static final String driverDashboard = "/driverdash.fxml";
    public static final String passengerDashboard = "/passengerdash.fxml";
    public static final String createUser = "/createuser.fxml";
    public static final String createLicence = "/createlicence.fxml";
    public static final String createCar = "/createvehicle.fxml";
    public static final String createSp = "/createstoppoint.fxml";
    public static final String createRoute = "/createroute.fxml";
    public static final String createRide = "/createride.fxml";
    public static final String myRides = "/driversrides.fxml";
    public static final String spSearch = "/stoppointsearch.fxml";
    public static final String rideSearch = "/ridesearch.fxml";
    public static final String viewRide = "/viewride.fxml";

    public FXMLNavigator() {
    }

    public void loadScene(String path) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        Parent root = loader.load();
        Scene scene = new Scene(root, 600, 500);
        Stage stage = session.getStage();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public void backToDashboard(ActionEvent event) throws Exception {
        User user = session.getCurrentUser();
        if (user.isDriver()) {
            loadScene(driverDashboard);
        } else {
            loadScene(passengerDashboard);
        }
    }
}
