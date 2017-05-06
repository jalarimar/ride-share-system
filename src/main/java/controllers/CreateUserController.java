package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.Driver;
import models.User;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created 22/03/2017.
 */
public class CreateUserController implements Initializable {

    private MainController main = MainController.getInstance();
    private FXMLController fxml = new FXMLController();

    private final String createLicence = "/createlicence.fxml";
    private final String passengerDashboard = "/passengerdash.fxml";

    @FXML Button createButton;
    @FXML Button uploadButton;
    @FXML Label fileNameLabel;
    @FXML TextField firstNameField;
    @FXML TextField lastNameField;
    @FXML TextField idField;
    @FXML TextField emailField;
    @FXML TextField addressField;
    @FXML TextField homeNumberField;
    @FXML TextField mobileNumberField;
    @FXML TextField password1Field;
    @FXML TextField password2Field;
    @FXML RadioButton passengerRadio;
    @FXML RadioButton driverRadio;
    @FXML Label errorMessageLabel;

    private String firstName;
    private String lastName;
    private String id;
    private String email;
    private String address;
    private String homeNumber;
    private String mobileNumber;
    private String password1;
    private String password2;
    private String errorMessage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ToggleGroup accountType = new ToggleGroup();
        passengerRadio.setToggleGroup(accountType);
        driverRadio.setToggleGroup(accountType);

        passengerRadio.setSelected(true);
    }

    @FXML
    protected void backToDashboard(ActionEvent event) throws Exception {
        fxml.backToDashboard(event);
    }

    private void collectInputFromFields() {
        firstName = firstNameField.getText();
        lastName = lastNameField.getText();
        id = idField.getText();
        email = emailField.getText();
        address = addressField.getText();
        homeNumber = homeNumberField.getText();
        mobileNumber = mobileNumberField.getText();
        password1 = password1Field.getText();
        password2 = password2Field.getText();
    }

    private boolean isValidInput() {
        errorMessage = "Validation Failed";  // default message
        boolean isValid = true;

        // TODO more general validation - Validator class
        if (!main.isAlphanumeric(firstName) || !main.isAlphanumeric(lastName)) {
            isValid = false;
        }

        // check home and/or mobile number has been entered
        if (homeNumber.isEmpty() && mobileNumber.isEmpty()) {
            isValid = false;
            errorMessage = "Must enter a home or mobile number";
        }

        // check email address is correct format
        String studentEmailRegex = "^[a-zA-Z0-9]+@uclive.ac.nz$";
        String staffEmailRegex = "^[a-zA-Z0-9]+@canterbury.ac.nz$";
        if (!email.matches(studentEmailRegex) && !email.matches(staffEmailRegex)) {
            isValid = false;
            errorMessage = "Invalid email address";
        }

        // check password matches
        if (!password1.equals(password2)) {
            isValid = false;
            errorMessage = "Passwords do not match";
        }

        return isValid;
    }

    @FXML
    protected void createUser(ActionEvent event) throws Exception {

        collectInputFromFields();

        if (isValidInput()) {
            if (driverRadio.isSelected()) {
                Driver driver = new Driver(firstName, lastName);
                main.setDriver(driver);
                fxml.loadScene(createLicence);
            } else if (passengerRadio.isSelected()){
                User user = new User(firstName, lastName, false);
                main.setUser(user);
                fxml.loadScene(passengerDashboard);
            } else {
                errorMessageLabel.setText("Please select an account type.");
            }
        } else {
            errorMessageLabel.setText(errorMessage);
        }
    }

    @FXML
    protected void uploadPhoto() {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image File");
        Stage stage = main.getStage();
        File file = fileChooser.showOpenDialog(stage);

        String fileFormatRegex = "^.*\\.(jpg|JPG|jpeg|JPEG|png|PNG)$";
        if (file != null && file.getName().matches(fileFormatRegex)) {
            if (file.getName().length() > 30) {
                fileNameLabel.setText(file.getName().substring(0, 28) + "...");
            } else {
                fileNameLabel.setText(file.getName());
            }
        } else {
            fileNameLabel.setText(".jpg/.jpeg or .png");
        }
    }

}
