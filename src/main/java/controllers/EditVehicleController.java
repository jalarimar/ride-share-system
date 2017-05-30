package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import models.Vehicle;
import utilities.Navigator;
import utilities.SessionManager;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import static utilities.Validator.tryParseDouble;

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

    private Vehicle vehicle;
    private String performanceString;
    private LocalDate regExpiry;
    private LocalDate wofExpiry;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        vehicle = session.getFocusedVehicle();
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

        vehiclePerformance.setPromptText(Double.toString(vehicle.getPerformance()));
        regPicker.setPromptText(vehicle.getRegExpiry().toString());
        wofPicker.setPromptText(vehicle.getWofExpiry().toString());

    }

    @FXML
    protected void backToDashboard(ActionEvent event) throws Exception {
        fxml.backToDashboard(event);
    }

    private void collectInputFromFields() {

        performanceString = !vehiclePerformance.getText().equals("") ? vehiclePerformance.getText() : Double.toString(vehicle.getPerformance());
        regExpiry = regPicker.getValue() != null ? regPicker.getValue() : vehicle.getRegExpiry();
        wofExpiry = wofPicker.getValue() != null ? wofPicker.getValue() : vehicle.getWofExpiry();
    }

    @FXML
    protected void saveVehicle(ActionEvent event) throws Exception {

        collectInputFromFields();

        double performance = tryParseDouble(performanceString);

        if (performance > -1) {
            vehicle.setPerformance(performance);
            vehicle.setRegExpiry(regExpiry);
            vehicle.setWofExpiry(wofExpiry);

            fxml.backToDashboard(event);
        } else {
            System.out.println("Validation Failed");
        }
    }


}
