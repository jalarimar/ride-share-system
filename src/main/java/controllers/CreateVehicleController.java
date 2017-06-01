package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import models.Driver;
import models.Rss;
import models.Vehicle;
import utilities.Navigator;
import utilities.SessionManager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static utilities.Validator.*;

/**
 * Created 22/03/2017.
 */
public class CreateVehicleController {

    private SessionManager main = SessionManager.getInstance();
    private Navigator fxml = new Navigator();

    @FXML TextField vehicleType;
    @FXML TextField vehicleModel;
    @FXML TextField vehicleColour;
    @FXML TextField vehicleLp;
    @FXML TextField vehicleYear;
    @FXML TextField vehicleNos;
    @FXML TextField vehiclePerformance;
    @FXML DatePicker regPicker;
    @FXML DatePicker wofPicker;
    @FXML Label errorLabel;

    private String type;
    private String model;
    private String colour;
    private String licencePlate;
    private String performanceStr;
    private String yearStr;
    private String numSeatsStr;
    private String errorMessage;

    @FXML
    protected void backToDashboard(ActionEvent event) throws Exception {
        fxml.backToDashboard(event);
    }

    private void collectInputFromFields() {
        type = vehicleType.getText();
        model = vehicleModel.getText();
        colour = vehicleColour.getText();
        licencePlate = vehicleLp.getText();
        performanceStr = vehiclePerformance.getText();
        yearStr = vehicleYear.getText();
        numSeatsStr = vehicleNos.getText();
    }

    private boolean isValidInput() {
        errorMessage = "Validation Failed"; // default message
        boolean isValid = true;

        if (!isAlphanumeric(type)) {
            isValid = false;
            errorMessage = "Type must consist of numbers/characters";
        }

        if (!isAlphanumeric(model)) {
            isValid = false;
            errorMessage = "Model must consist of numbers/characters";
        }

        if (!isAlphabetic(colour)) {
            isValid = false;
            errorMessage = "Colour must consist of characters";
        }

        if (!isAlphanumeric(licencePlate)) {
            isValid = false;
            errorMessage = "Licence plate must consist of numbers/characters";
        }

        if (Rss.getInstance().getVehicleByLicencePlate(licencePlate) != null) {
            isValid = false;
            errorMessage = "A vehicle with this licence plate is already registered";
        }

        if (regPicker.getValue() == null) {
            isValid = false;
            errorMessage = "Registration expiry date must be selected";
        }

        if (wofPicker.getValue() == null) {
            isValid = false;
            errorMessage = "WOF expiry date must be selected";
        }

        if (tryParseInt(yearStr) == -1) {
            isValid = false;
            errorMessage = "Year must be positive integer";
        }

        if (tryParseInt(numSeatsStr) == -1) {
            isValid = false;
            errorMessage = "Number of seats must be positive integer";
        }
        if (tryParseDouble(performanceStr) == -1) {
            isValid = false;
            errorMessage = "Performance must be positive number";
        }

        return isValid;
    }

    @FXML
    protected void createVehicle(ActionEvent event) throws Exception {

        collectInputFromFields();

        if (isValidInput()) {
            LocalDate regExpiry = regPicker.getValue();
            LocalDate wofExpiry = wofPicker.getValue();
            int year = tryParseInt(yearStr);
            int numSeats = tryParseInt(numSeatsStr);
            double performance = tryParseDouble(performanceStr);

            Vehicle vehicle = new Vehicle(type, model, colour, licencePlate, performance, year, numSeats, regExpiry, wofExpiry);

            Driver driver = main.getCurrentDriver();
            driver.addVehicle(vehicle);

            fxml.backToDashboard(event);
        } else {
            errorLabel.setText(errorMessage);
        }
    }
}
