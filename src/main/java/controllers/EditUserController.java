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

import static controllers.Navigator.createLicence;
import static controllers.Navigator.passengerDashboard;
import static controllers.Validator.isAlphanumeric;

/**
 * Created 09/05/2017.
 */
public class EditUserController implements Initializable {

    private SessionManager session = SessionManager.getInstance();
    private Navigator fxml = new Navigator();

    @FXML Label fileNameLabel;
    @FXML TextField firstNameField;
    @FXML TextField lastNameField;
    @FXML TextField idField;
    @FXML TextField emailField;
    @FXML TextField addressNumField;
    @FXML TextField addressStreetField;
    @FXML TextField addressSuburbField;
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
    private File photo;
    private String errorMessage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ToggleGroup accountType = new ToggleGroup();
        passengerRadio.setToggleGroup(accountType);
        driverRadio.setToggleGroup(accountType);

        populateWithExistingInfo();
    }

    @FXML
    protected void backToDashboard(ActionEvent event) throws Exception {
        fxml.backToDashboard(event);
    }

    private void populateWithExistingInfo() {
        User user = session.getCurrentUser();

        fileNameLabel.setText(user.getPhoto().getName());
        firstNameField.setPromptText(user.getFirstName());
        lastNameField.setPromptText(user.getLastName());
        emailField.setPromptText(user.getEmail());
        idField.setPromptText(user.getUniID());
        addressNumField.setPromptText(user.getFirstName()); // TODO address
        addressStreetField.setPromptText(user.getFirstName()); // TODO address
        addressSuburbField.setPromptText(user.getAddress()); // TODO address
        homeNumberField.setPromptText(user.getHomeNumber());
        mobileNumberField.setPromptText(user.getMobileNumber());

        if (user instanceof Driver) {
            driverRadio.setSelected(true);
        } else {
            passengerRadio.setSelected(true);
        }

        idField.setDisable(true);
    }

    private void collectInputFromFields() {
        firstName = firstNameField.getText();
        lastName = lastNameField.getText();
        id = idField.getText();
        email = emailField.getText();
        address = addressNumField.getText() + " " + addressStreetField.getText() + ", " + addressSuburbField.getText();
        // TODO change StopPoint to Address and use it here
        homeNumber = homeNumberField.getText();
        mobileNumber = mobileNumberField.getText();
        password1 = password1Field.getText();
        password2 = password2Field.getText();
    }

    private boolean isValidPhoto() {
        String fileFormatRegex = "^.*\\.(jpg|JPG|jpeg|JPEG|png|PNG)$";
        return photo != null && photo.getName().matches(fileFormatRegex);
    }

    private boolean isValidInput() {
        errorMessage = "Validation Failed";  // default message
        boolean isValid = true;

        // TODO more general validation - Validator class
        if (!isAlphanumeric(firstName) || !isAlphanumeric(lastName)) {
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

        if (!isValidPhoto()) {
            isValid = false;
            errorMessage = "Valid photo must be uploaded";
        }

        return isValid;
    }

    @FXML
    protected void saveDetails(ActionEvent event) throws Exception {

        collectInputFromFields();

        // TODO check this works with serialisation

        if (isValidInput()) {
            if (driverRadio.isSelected()) {
                Driver driver = new Driver(firstName, lastName, id, password1, email, address, homeNumber, mobileNumber, photo);
                session.setCurrentUser(driver);
                fxml.loadScene(createLicence);
            } else if (passengerRadio.isSelected()){
                User user = new User(firstName, lastName, false, id, password1, email, address, homeNumber, mobileNumber, photo);
                session.setCurrentUser(user);
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
        Stage stage = session.getStage();
        photo = fileChooser.showOpenDialog(stage);
        
        if (isValidPhoto()) {
            if (photo.getName().length() > 30) {
                fileNameLabel.setText(photo.getName().substring(0, 28) + "...");
            } else {
                fileNameLabel.setText(photo.getName());
            }
        } else {
            fileNameLabel.setText(".jpg/.jpeg or .png");
        }
    }

}
