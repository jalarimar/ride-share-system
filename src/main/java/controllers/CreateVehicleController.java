package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import models.Driver;
import models.Vehicle;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static controllers.Validator.isAlphanumeric;
import static controllers.Validator.tryParseInt;

/**
 * Created 22/03/2017.
 */
public class CreateVehicleController {

    private SessionManager main = SessionManager.getInstance();
    private FXMLNavigator fxml = new FXMLNavigator();

    private final String vehicleFilePath = "vehicle.ser"; // TODO make name based on license plate?

    @FXML Button dashboardButton;
    @FXML Button createButton;
    @FXML TextField vehicleType;
    @FXML TextField vehicleModel;
    @FXML TextField vehicleColour;
    @FXML TextField vehicleLp;
    @FXML TextField vehicleYear;
    @FXML TextField vehicleNos;
    @FXML TextField vehiclePerformance;
    @FXML DatePicker regPicker;
    @FXML DatePicker wofPicker;

    @FXML
    protected void backToDashboard(ActionEvent event) throws Exception {
        fxml.backToDashboard(event);
    }

    private boolean validateUserInput(List<String> userStrings) {
        boolean isValid = true;
        for (String string : userStrings) {
            isValid = isValid && isAlphanumeric(string);
        }
        return isValid;
    }

    @FXML
    protected void createVehicle(ActionEvent event) throws Exception {

        String type = vehicleType.getText();
        String model = vehicleModel.getText();
        String colour = vehicleColour.getText();
        String licensePlate = vehicleLp.getText();
        String performance = vehiclePerformance.getText();
        String yearString = vehicleYear.getText();
        String numSeatsString = vehicleNos.getText();

        List<String> inputStrings = new ArrayList();
        inputStrings.add(type);
        inputStrings.add(model);
        inputStrings.add(colour);
        inputStrings.add(licensePlate);
        inputStrings.add(performance);

        LocalDate regExpiry = regPicker.getValue();
        LocalDate wofExpiry = wofPicker.getValue();

        boolean isValid = validateUserInput(inputStrings);

        int year = tryParseInt(yearString);
        int numSeats = tryParseInt(numSeatsString);

        if (isValid && year > -1 && numSeats > -1) {
            Vehicle vehicle = new Vehicle(type, model, colour, licensePlate, performance, year, numSeats, regExpiry, wofExpiry);

            Driver driver = main.getCurrentDriver();
            driver.addVehicle(vehicle);

            fxml.backToDashboard(event);
        } else {
            System.out.println("Validation Failed");
        }
    }


}
