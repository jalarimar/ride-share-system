package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.paint.Stop;
import models.Driver;
import models.StopPoint;

import java.util.ArrayList;
import java.util.List;

/**
 * Created 22/03/2017.
 */
public class CreateSPController {

    private MainController main = MainController.getInstance();
    private FXMLController fxml = new FXMLController();

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
        for (StopPoint sp : main.getDriver().getStopPoints()) {
            existing.add(sp.getAddress());
        }
        return existing;
    }

    @FXML
    protected void createStopPoint(ActionEvent event) throws Exception {
        String numberInput = numberField.getText();
        String name = nameField.getText();
        String suburb = suburbField.getText();

        int number = main.tryParseInt(numberInput);

        if (number > -1 && main.isAlphanumeric(name) && main.isAlphanumeric(suburb)) {
            StopPoint stopPoint = new StopPoint(number, name, suburb);
            Driver driver = main.getDriver();

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
