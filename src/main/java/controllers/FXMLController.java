package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.Window;
import models.Driver;
import models.Passenger;
import models.User;

import java.io.Serializable;

/**
 * Created 22/03/2017.
 */
public class FXMLController {

    private final String driverFilePath = "driver.ser";
    private final String passengerFilePath = "passenger.ser";
    private final String vehicleFilePath = "vehicle.ser";
    private final String driverDashboard = "/driverdash.fxml";
    private final String passengerDashboard = "/passengerdash.fxml";
    private final String userDetails = "/userdetails.fxml";

    private boolean userIsDriver;


    @FXML
    Button driverButton;

    @FXML
    Button passengerButton;

    @FXML
    Button registerButton;

    @FXML
    Button dashboardButton;

    @FXML
    Button searchScreenButton;

    private void loadScene(String path, Button eventSource) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        Parent root = loader.load();
        Scene scene = new Scene(root, 500, 400);
        Stage stage = (Stage) eventSource.getScene().getWindow();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    protected void createUser(ActionEvent event) throws Exception {
        Serializer serializer = new Serializer();
        if (userIsDriver) {
            // TODO get name from text fields
            Driver driver = new Driver("Anna", "Burnes");
            serializer.serialize(driver, driverFilePath);
            loadScene(driverDashboard, registerButton);
        } else {
            Passenger passenger = new Passenger("Carrie", "Delaware");
            serializer.serialize(passenger, passengerFilePath);
            loadScene(passengerDashboard, registerButton);
        }
    }

    @FXML
    protected void backToDashboard(ActionEvent event) throws Exception {
        if (userIsDriver) {
            loadScene(driverDashboard, dashboardButton);
        } else {
            loadScene(passengerDashboard, dashboardButton);
        }
    }

    @FXML
    protected void loadSearchScreen(ActionEvent event) throws Exception {
        // check if driver is serialised and if so load otherwise ask for details
        loadScene("/ridesearch.fxml", searchScreenButton);
    }

    @FXML
    protected void loadDriverDashboard(ActionEvent event) throws Exception {
        // check if driver is serialised and if so load otherwise ask for details
        userIsDriver = true;
        Serializer serializer = new Serializer();
        Driver driver = serializer.deserialize(driverFilePath);
        if (driver != null) {
            loadScene(driverDashboard, driverButton);
        } else {
            loadScene(userDetails, driverButton);
        }
    }

    @FXML
    protected void loadPassengerDashboard(ActionEvent event) throws Exception {
        // check if passenger is serialised and if so load otherwise ask for details
        userIsDriver = false;
        Serializer serializer = new Serializer();
        Passenger passenger = serializer.deserialize(passengerFilePath);
        if (passenger != null) {
            loadScene(passengerDashboard, passengerButton);
        } else {
            loadScene(userDetails, passengerButton);
        }
    }

}
