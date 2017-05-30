package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import models.Driver;
import models.StopPoint;
import utilities.Navigator;
import utilities.SessionManager;

import java.util.ArrayList;
import java.util.List;

import static utilities.Validator.isAlphanumeric;
import static utilities.Validator.tryParseInt;

/**
 * Created 22/03/2017.
 */
public class CreateSPController {

    private SessionManager session = SessionManager.getInstance();
    private Navigator fxml = new Navigator();

    @FXML TextField numberField;
    @FXML TextField nameField;
    @FXML TextField suburbField;
    @FXML Label errorMessage;

    @FXML
    protected void backToDashboard(ActionEvent event) throws Exception {
        fxml.backToDashboard(event);
    }

    private List<String> getExistingAddresses() {
        List<String> existing = new ArrayList<>();
        for (StopPoint sp : session.getCurrentDriver().getStopPoints()) {
            existing.add(sp.toString());
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
            Driver driver = session.getCurrentDriver();

            // same address cannot be added more than once
            List<String> existingAddresses = getExistingAddresses();
            if (!existingAddresses.contains(stopPoint.toString())) {
                driver.addStopPoint(stopPoint);
                fxml.backToDashboard(event);
            } else {
                errorMessage.setText("This stop point has already been added.");
            }

        } else {
            System.out.println("Validation Failed");
        }
    }


}
