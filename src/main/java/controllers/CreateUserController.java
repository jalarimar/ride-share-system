package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.Driver;
import models.StopPoint;
import models.User;
import utilities.Navigator;
import utilities.SessionManager;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import static utilities.Navigator.*;
import static utilities.Validator.isAlphanumeric;

/**
 * Created 22/03/2017.
 */
public class CreateUserController implements Initializable {

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
    @FXML ImageView img;

    private String firstName;
    private String lastName;
    private String id;
    private String email;
    private StopPoint address;
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

        passengerRadio.setSelected(true);
    }

    private void collectInputFromFields() {
        firstName = firstNameField.getText();
        lastName = lastNameField.getText();
        id = idField.getText();
        email = emailField.getText();
        address = new StopPoint(addressNumField.getText(),addressStreetField.getText(), addressSuburbField.getText());
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
    protected void createUser(ActionEvent event) throws Exception {

        collectInputFromFields();

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
            Image image = new Image("file:" + photo.getAbsolutePath());
            img.setImage(image);
        } else {
            fileNameLabel.setText(".jpg/.jpeg or .png");
        }
    }

}
