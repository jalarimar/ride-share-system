package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import models.Driver;
import models.Rss;
import models.StopPoint;
import utilities.Navigator;
import utilities.SessionManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static utilities.Validator.isAlphabetic;
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

    @FXML
    protected void createStopPoint(ActionEvent event) throws Exception {
        String number = numberField.getText();
        String street = nameField.getText();
        String suburb = suburbField.getText();

        if ((isAlphanumeric(number)||number.isEmpty()) && isAlphabetic(street) && isAlphabetic(suburb)) {
            StopPoint stopPoint = new StopPoint(number, street, suburb);
            Driver driver = session.getCurrentDriver();

            // same address cannot be added more than once
            Collection<StopPoint> existingAddresses = Rss.getInstance().getAllStopPoints();
            if (!existingAddresses.contains(stopPoint)) {
                driver.addStopPoint(stopPoint);
                fxml.backToDashboard(event);
            } else {
                errorMessage.setText("This stop point has already been added");
            }
        } else {
            errorMessage.setText("Invalid address");
        }
    }
}
