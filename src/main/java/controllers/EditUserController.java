package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.Driver;
import models.Rss;
import models.StopPoint;
import models.User;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import static controllers.Navigator.createLicence;
import static controllers.Navigator.driverDashboard;
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
        addressNumField.setPromptText(user.getAddress().getStreetNumber());
        addressStreetField.setPromptText(user.getAddress().getStreetName());
        addressSuburbField.setPromptText(user.getAddress().getSuburb());
        homeNumberField.setPromptText(user.getHomeNumber());
        mobileNumberField.setPromptText(user.getMobileNumber());
        fileNameLabel.setText(user.getPhoto().getName());

        if (user instanceof Driver) {
            driverRadio.setSelected(true);
        } else {
            passengerRadio.setSelected(true);
        }

        idField.setDisable(true);
    }

    private void collectInputFromFields() {
        User user = session.getCurrentUser();

        firstName = !firstNameField.getText().equals("") ? firstNameField.getText() : user.getFirstName();
        lastName = !lastNameField.getText().equals("") ? lastNameField.getText() : user.getLastName();
        id = user.getUniID();
        email = !emailField.getText().equals("") ? emailField.getText() : user.getEmail();
        address = user.getAddress();
        if (!addressNumField.getText().equals("") && !addressStreetField.getText().equals("") && !addressSuburbField.getText().equals("")) {
            address = new StopPoint(addressNumField.getText(), addressStreetField.getText(), addressSuburbField.getText());
        }
        System.out.println(address);
        System.out.println(user.getAddress());
        homeNumber = !homeNumberField.getText().equals("") ? homeNumberField.getText() : user.getHomeNumber();
        mobileNumber = !mobileNumberField.getText().equals("") ? mobileNumberField.getText() : user.getMobileNumber();
        password1 = !password1Field.getText().equals("") ? password1Field.getText() : user.getPassword();
        password2 = !password2Field.getText().equals("") ? password2Field.getText() : user.getPassword();
        photo = user.getPhoto();
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

        if (isValidInput()) {
            if (driverRadio.isSelected()) {

                Driver driver = SessionManager.getInstance().getCurrentDriver();
                if (driver == null) {
                    // user used to be a passenger
                    driver = new Driver(firstName, lastName, id, password1, email, address, homeNumber, mobileNumber, photo);
                    driver.setBookedRideIds(SessionManager.getInstance().getCurrentUser().getBookedRideIds());
                    Rss.getInstance().addDriver(driver);
                    SessionManager.getInstance().setCurrentUser(driver);
                    fxml.loadScene(createLicence);

                } else {
                    driver.setFirstName(firstName);
                    driver.setLastName(lastName);
                    driver.setPassword(password1);
                    driver.setEmail(email);
                    driver.setAddress(address);
                    driver.setHomeNumber(homeNumber);
                    driver.setMobileNumber(mobileNumber);
                    driver.setPhoto(photo);
                    fxml.loadScene(driverDashboard);
                }

            } else if (passengerRadio.isSelected()){

                User user = SessionManager.getInstance().getCurrentUser();
                if (user.isDriver()) {
                    // user used to be a driver
                    Rss.getInstance().removeDriver(user.getUniID());
                    user.setIsDriver(false);
                }
                user.setFirstName(firstName);
                user.setLastName(lastName);
                user.setPassword(password1);
                user.setEmail(email);
                user.setAddress(address);
                user.setHomeNumber(homeNumber);
                user.setMobileNumber(mobileNumber);
                user.setPhoto(photo);

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
