package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.Driver;
import models.Licence;
import models.User;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 * Created 22/03/2017.
 */
public class CreateLicenceController implements Initializable {

    private MainController main = MainController.getInstance();
    private FXMLController fxml = new FXMLController();

    private final String driverDashboard = "/driverdash.fxml";
    private final String passengerDashboard = "/passengerdash.fxml";

    @FXML Button createButton;
    @FXML Button cancelButton;
    @FXML TextField numberField;
    @FXML DatePicker issuePicker;
    @FXML DatePicker expiryPicker;
    @FXML RadioButton learnerRadio;
    @FXML RadioButton restrictedRadio;
    @FXML RadioButton fullRadio;
    @FXML Label errorMessageLabel;

    private String number;
    private LocalDate issueDate;
    private LocalDate expiryDate;
    private String errorMessage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ToggleGroup licenceType = new ToggleGroup();
        learnerRadio.setToggleGroup(licenceType);
        restrictedRadio.setToggleGroup(licenceType);
        fullRadio.setToggleGroup(licenceType);

        learnerRadio.setSelected(true);
    }

    @FXML
    protected void backToDashboard(ActionEvent event) throws Exception {
        // TODO check user is now a passenger not a driver
        main.getUser().setDriver(false);
        fxml.loadScene(passengerDashboard);
    }

    private void collectInputFromFields() {
        number = numberField.getText();
        issueDate = issuePicker.getValue();
        expiryDate = expiryPicker.getValue();
    }

    private boolean isValidInput() {
        errorMessage = "Validation Failed"; // default message
        boolean isValid = true;

        // TODO more general validation - Validator class

        return isValid;
    }

    @FXML
    protected void createUser(ActionEvent event) throws Exception {

        collectInputFromFields();

        if (isValidInput()) {
            if (fullRadio.isSelected()) {
                Licence licence = new Licence("Full", number, issueDate, expiryDate);
                main.getDriver().setLicence(licence);
                fxml.loadScene(driverDashboard);
            } else {
                errorMessageLabel.setText("You can only be a driver if you have a full licence.");
            }
        } else {
            errorMessageLabel.setText(errorMessage);
        }
    }

}
