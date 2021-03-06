package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import models.Licence;
import models.Rss;
import models.User;
import utilities.Navigator;
import utilities.SessionManager;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import static utilities.Navigator.driverDashboard;
import static utilities.Navigator.passengerDashboard;
import static utilities.Validator.isAlphanumeric;

/**
 * Created 22/03/2017.
 */
public class CreateLicenceController implements Initializable {

    private SessionManager session = SessionManager.getInstance();
    private Navigator fxml = new Navigator();

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
        User user = session.getCurrentUser();
        Rss.getInstance().removeDriver(user.getUniID());
        user.setIsDriver(false);

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

        if (!isAlphanumeric(number)) {
            isValid = false;
            errorMessage = "Licence number must consist of numbers/characters";
        }

        if (expiryDate == null) {
            isValid = false;
            errorMessage = "Expiry date must be selected";
        }

        if (issueDate == null) {
            isValid = false;
            errorMessage = "Issue date must be selected";
        }

        if (!fullRadio.isSelected()) {
            isValid = false;
            errorMessage = "You can only be a driver if you have a full licence.";
        }
        return isValid;
    }

    @FXML
    protected void createUser(ActionEvent event) throws Exception {

        collectInputFromFields();

        if (isValidInput()) {
            Licence licence = new Licence("Full", number, issueDate, expiryDate);
            session.getCurrentDriver().setLicence(licence);
            fxml.loadScene(driverDashboard);
        } else {
            errorMessageLabel.setText(errorMessage);
        }
    }
}
