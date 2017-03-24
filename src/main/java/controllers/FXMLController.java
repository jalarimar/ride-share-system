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
    private boolean userIsDriver = false;


    @FXML
    Button driverButton;

    @FXML
    Button passengerButton;

    @FXML
    Button registerButton;

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
            Driver driver = new Driver("a", "B");
            serializer.serialize(driver, driverFilePath);
            loadScene("/driverdash.fxml", driverButton);
        } else {
            Passenger passenger = new Passenger("C", "D");
            serializer.serialize(passenger, driverFilePath);
            loadScene("/passengerdash.fxml", driverButton);
        }
    }

    @FXML
    protected void loadDriverDashboard(ActionEvent event) throws Exception {
        // check if driver is serialised and if so load otherwise ask for details
        userIsDriver = true;
        Serializer serializer = new Serializer();
        Driver driver = serializer.deserialize(driverFilePath);
        if (driver != null) {
            loadScene("/driverdash.fxml", driverButton);
        } else {
            loadScene("/userdetails.fxml", driverButton);
        }
    }

    @FXML
    protected void loadPassengerDashboard(ActionEvent event) throws Exception {
        // check if passenger is serialised and if so load otherwise ask for details
        Serializer serializer = new Serializer();
        Passenger passenger = serializer.deserialize(driverFilePath);
        if (passenger != null) {
            loadScene("/passengerdash.fxml", driverButton);
        } else {
            loadScene("/userdetails.fxml", driverButton);
        }
    }

}
