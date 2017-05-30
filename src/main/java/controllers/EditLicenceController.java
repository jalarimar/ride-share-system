package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import models.Licence;
import utilities.Navigator;
import utilities.SessionManager;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import static utilities.Navigator.driverDashboard;

/**
 * Created 22/03/2017.
 */
public class EditLicenceController implements Initializable {

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

        fullRadio.setSelected(true);

        Licence licence = session.getCurrentDriver().getLicence();
        numberField.setPromptText(licence.getNumber());
        issuePicker.setPromptText(licence.getIssueDate().toString());
        expiryPicker.setPromptText(licence.getExpiryDate().toString());
    }

    @FXML
    protected void backToDashboard(ActionEvent event) throws Exception {
        fxml.backToDashboard(event);
    }

    private void collectInputFromFields() {
        Licence licence = SessionManager.getInstance().getCurrentDriver().getLicence();

        number = !numberField.getText().equals("") ? numberField.getText() : licence.getNumber();
        issueDate = issuePicker.getValue() != null ? issuePicker.getValue() : licence.getIssueDate();
        expiryDate = expiryPicker.getValue() != null ? expiryPicker.getValue() : licence.getExpiryDate();
    }

    private boolean isValidInput() {
        errorMessage = "Validation Failed"; // default message
        boolean isValid = true;

        // TODO more general validation - Validator class

        return isValid;
    }

    @FXML
    protected void updateUser(ActionEvent event) throws Exception {

        collectInputFromFields();

        if (isValidInput()) {
            if (fullRadio.isSelected()) {

                Licence licence = SessionManager.getInstance().getCurrentDriver().getLicence();
                licence.setNumber(number);
                licence.setIssueDate(issueDate);
                licence.setExpiryDate(expiryDate);

                fxml.loadScene(driverDashboard);
            } else {
                errorMessageLabel.setText("You can only be a driver if you have a full licence.");
            }
        } else {
            errorMessageLabel.setText(errorMessage);
        }
    }

}
