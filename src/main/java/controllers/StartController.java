package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import models.Driver;
import models.Passenger;

/**
 * Created 22/03/2017.
 */
public class StartController {

    private MainController main = MainController.getInstance();
    private FXMLController fxml;

    private final String driverFilePath = "driver.ser";
    private final String passengerFilePath = "passenger.ser";
    private final String driverDashboard = "/driverdash.fxml";
    private final String passengerDashboard = "/passengerdash.fxml";
    private final String createUser = "/createuser.fxml";

    @FXML
    Button driverButton;
    @FXML
    Button passengerButton;

    @FXML
    protected void loadDriverDashboard(ActionEvent event) throws Exception {
        // check if driver is serialised and if so load otherwise ask for details
        main.initialiseUser(true);
        main.setStage((Stage)driverButton.getScene().getWindow());
        fxml = new FXMLController();

        Serializer serializer = new Serializer();
        Driver driver = serializer.deserialize(driverFilePath);
        if (driver != null) {
            fxml.loadScene(driverDashboard);
        } else {
            fxml.loadScene(createUser);
        }
    }

    @FXML
    protected void loadPassengerDashboard(ActionEvent event) throws Exception {
        // check if passenger is serialised and if so load otherwise ask for details
        main.initialiseUser(false);
        main.setStage((Stage)passengerButton.getScene().getWindow());
        fxml = new FXMLController();

        Serializer serializer = new Serializer();
        Passenger passenger = serializer.deserialize(passengerFilePath);
        if (passenger != null) {
            fxml.loadScene(passengerDashboard);
        } else {
            fxml.loadScene(createUser);
        }
    }

}
