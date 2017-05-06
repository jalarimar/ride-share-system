package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import models.Driver;
import models.StopPoint;

import java.util.ArrayList;
import java.util.List;

import static controllers.Validator.isAlphanumeric;
import static controllers.Validator.tryParseInt;

/**
 * Created 22/03/2017.
 */
public class CreateSPController {

    private SessionManager main = SessionManager.getInstance();
    private FXMLNavigator fxml = new FXMLNavigator();

    @FXML
    Button dashboardButton;
    @FXML
    Button createButton;
    @FXML
    TextField numberField;
    @FXML
    TextField nameField;
    @FXML
    TextField suburbField;

    @FXML
    protected void backToDashboard(ActionEvent event) throws Exception {
        fxml.backToDashboard(event);
    }

    private List<String> getExistingAdresses() {
        List<String> existing = new ArrayList<>();
        for (StopPoint sp : main.getCurrentDriver().getStopPoints()) {
            existing.add(sp.getAddress());
        }
        return existing;
    }

    @FXML
    protected void createStopPoint(ActionEvent event) throws Exception {
        String numberInput = numberField.getText();
        String name = nameField.getText();
        String suburb = suburbField.getText();

        int number = tryParseInt(numberInput);

        if (number > -1 && isAlphanumeric(name) && isAlphanumeric(suburb)) {
            StopPoint stopPoint = new StopPoint(number, name, suburb);
            Driver driver = main.getCurrentDriver();

            // same address cannot be added more than once
            List<String> existingAdresses = getExistingAdresses();
            if (!existingAdresses.contains(stopPoint.getAddress())) {
                driver.addStopPoint(stopPoint);
                main.addStopPoint(stopPoint);
            }
            fxml.backToDashboard(event);

        } else {
            System.out.println("Validation Failed");
        }
    }


}
