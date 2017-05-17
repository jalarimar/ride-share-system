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
public class Navigator {

    private SessionManager session = SessionManager.getInstance();

    public static final String driverDashboard = "/driverdash.fxml";
    public static final String passengerDashboard = "/passengerdash.fxml";
    public static final String editDashboard = "/editdash.fxml";
    public static final String editUser = "/edituser.fxml";
    public static final String editLicence = "/editlicence.fxml";
    public static final String editVehicle = "/editvehicle.fxml";
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
    public static final String bookedRides = "/bookedrides.fxml";

    public Navigator() {
    }

    public void loadScene(String path) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            Parent root = loader.load();
            Scene scene = new Scene(root, 600, 500);
            Stage stage = session.getStage();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (Exception e) {
            System.out.println("Load Scene Failed");
        }
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
