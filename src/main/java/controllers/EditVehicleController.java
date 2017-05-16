package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import models.Driver;
import models.Vehicle;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static controllers.Validator.isAlphanumeric;
import static controllers.Validator.tryParseInt;

/**
 * Created 22/03/2017.
 */
public class EditVehicleController implements Initializable {

    private SessionManager session = SessionManager.getInstance();
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Vehicle vehicle = session.getFocusedVehicle();
        vehicleType.setText(vehicle.getType());
        vehicleModel.setText(vehicle.getModel());
        vehicleColour.setText(vehicle.getColour());
        vehicleLp.setText(vehicle.getLicensePlate());
        vehicleYear.setText(Integer.toString(vehicle.getYear()));
        vehicleNos.setText(Integer.toString(vehicle.getPhysicalSeats()));

        vehicleType.setDisable(true);
        vehicleModel.setDisable(true);
        vehicleColour.setDisable(true);
        vehicleLp.setDisable(true);
        vehicleYear.setDisable(true);
        vehicleNos.setDisable(true);
        // only performance, WOF and registration expiry can be updated

        vehiclePerformance.setPromptText(Integer.toString(vehicle.getPerformance()));
        regPicker.setPromptText(vehicle.getRegExpiry().toString());
        wofPicker.setPromptText(vehicle.getWofExpiry().toString());

    }

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
    protected void saveVehicle(ActionEvent event) throws Exception {

        // TODO change from create to save
        // TODO update ride price if performance changes and notify passengers

        String type = vehicleType.getText();
        String model = vehicleModel.getText();
        String colour = vehicleColour.getText();
        String licensePlate = vehicleLp.getText();
        String performanceString = vehiclePerformance.getText();
        String yearString = vehicleYear.getText();
        String numSeatsString = vehicleNos.getText();

        List<String> inputStrings = new ArrayList();
        inputStrings.add(type);
        inputStrings.add(model);
        inputStrings.add(colour);
        inputStrings.add(licensePlate);

        LocalDate regExpiry = regPicker.getValue();
        LocalDate wofExpiry = wofPicker.getValue();

        boolean isValid = validateUserInput(inputStrings);

        int year = tryParseInt(yearString);
        int numSeats = tryParseInt(numSeatsString);
        int performance = tryParseInt(performanceString);

        if (isValid && year > -1 && numSeats > -1 && performance > -1) {
            Vehicle vehicle = new Vehicle(type, model, colour, licensePlate, performance, year, numSeats, regExpiry, wofExpiry);

            Driver driver = session.getCurrentDriver();
            driver.addVehicle(vehicle);

            fxml.backToDashboard(event);
        } else {
            System.out.println("Validation Failed");
        }
    }


}
