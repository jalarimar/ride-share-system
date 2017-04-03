package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import models.Driver;
import models.Passenger;

/**
 * Created 22/03/2017.
 */
public class CreateUserController {

    private MainController main = MainController.getInstance();
    private FXMLController fxml = new FXMLController();

    private final String driverFilePath = "driver.ser";
    private final String passengerFilePath = "passenger.ser";

    private final String driverDashboard = "/driverdash.fxml";
    private final String passengerDashboard = "/passengerdash.fxml";

    @FXML
    Button createButton;
    @FXML
    TextField firstName;
    @FXML
    TextField lastName;

    @FXML
    protected void backToDashboard(ActionEvent event) throws Exception {
        fxml.backToDashboard(event);
    }

    @FXML
    protected void createUser(ActionEvent event) throws Exception {
        String first = firstName.getText();
        String last = lastName.getText();

        //main.getUser().setFirstName(first);
        //main.getUser().setLastName(last);

        if (main.isValidInputString(first) && main.isValidInputString(last)) {
            Serializer serializer = new Serializer();

            if (main.getUser().isDriver()) {
                Driver driver = new Driver(first, last);
                main.setDriver(driver);
                serializer.serialize(driver, driverFilePath);
                fxml.loadScene(driverDashboard);
            } else {
                Passenger passenger = new Passenger(first, last);
                main.setUser(passenger);
                serializer.serialize(passenger, passengerFilePath);
                fxml.loadScene(passengerDashboard);
            }
        } else {
            System.out.println("Validation Failed");
        }
    }


}
