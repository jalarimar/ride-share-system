package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import models.*;

import java.time.LocalDate;
import java.util.List;

import static controllers.Navigator.*;
import static models.NotificationStatus.*;


/**
 * Created 22/03/2017.
 */
public class LoginController {

    private SessionManager session = SessionManager.getInstance();
    private Navigator fxml = new Navigator();

    @FXML TextField usernameField;
    @FXML PasswordField passwordField;
    @FXML Label errorMessageLabel;
    @FXML Button loginButton;
    @FXML Button registerButton;

    private NotificationStatus getTimeUntilExpiry(LocalDate expiryDate) {
        LocalDate now = LocalDate.now();

        if (now.isAfter(expiryDate.minusWeeks(1))) {
            return ONE_WEEK;
        } else if (now.isAfter(expiryDate.minusWeeks(2))) {
            return TWO_WEEKS;
        } else if (now.isAfter(expiryDate.minusMonths(1))) {
            return ONE_MONTH;
        } else {
            // more than one month until expiry
            return NONE;
        }
    }

    private void checkExpiry(Driver driver) {

        LocalDate licenceExpiry = driver.getLicence().getExpiryDate();
        NotificationStatus timeUntilLicenceExpiry = getTimeUntilExpiry(licenceExpiry);
        NotificationStatus lastSeenLicenceNotification = driver.getLicence().getLastSeenNotification();
        boolean notifyLicence = timeUntilLicenceExpiry.ordinal() < lastSeenLicenceNotification.ordinal();

        boolean notifyWof = false;
        boolean notifyReg = false;

        NotificationStatus timeUntilWofExpiry = NONE;
        NotificationStatus timeUntilRegExpiry = NONE;

        List<Vehicle> vehicles = driver.getVehicles();
        for (Vehicle vehicle : vehicles) {
            LocalDate wofExpiry = vehicle.getWofExpiry();
            LocalDate regExpiry = vehicle.getRegExpiry();

            NotificationStatus wofNotification = getTimeUntilExpiry(wofExpiry);
            NotificationStatus regNotification = getTimeUntilExpiry(regExpiry);

            if (wofNotification.ordinal() < timeUntilWofExpiry.ordinal()) {
                timeUntilWofExpiry = wofNotification;
            }
            if (regNotification.ordinal() < timeUntilRegExpiry.ordinal()) {
                timeUntilRegExpiry = regNotification;
            }

            NotificationStatus lastSeenWof = vehicle.getLastSeenWofNotification();
            NotificationStatus lastSeenReg = vehicle.getLastSeenRegNotification();

            notifyWof = notifyWof || timeUntilWofExpiry.ordinal() < lastSeenWof.ordinal();
            notifyReg = notifyReg || timeUntilRegExpiry.ordinal() < lastSeenReg.ordinal();
        }

        if (notifyWof || notifyReg || notifyLicence) {

            String warning = "";
            String content = "";

            if (notifyLicence) {
                warning += "Your driver's licence expires in less than " + timeUntilLicenceExpiry + "\n";
                content += "Update your driver's licence from the edit account screen.\n";
                driver.getLicence().setLastSeenNotification(timeUntilLicenceExpiry);
                // TODO make Licence etc observable so licence info is saved to rss when it is changed
            }
            if (notifyReg) {
                warning += "Your vehicle's registration expires in less than " + timeUntilRegExpiry + "\n";
                content += "Update your vehicle's licence from the edit vehicle screen.\n";
            }
            if (notifyWof) {
                warning += "Your vehicle's WOF expires in less than " + timeUntilWofExpiry + "\n";
                content += "Update your WOF from the edit vehicle screen.\n";
            }

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Notifications");
            alert.setHeaderText(warning);
            alert.setContentText(content);
            alert.showAndWait();
        }
    }

    @FXML
    protected void attemptLogin(ActionEvent event) throws Exception {
        String userId = usernameField.getText();
        String password = passwordField.getText();

        if (hasCorrectPassword(userId, password)) {

            User user = session.getRss().getUserById(userId);
            session.setCurrentUser(user);

            // load driver or passenger dashboard
            if (user instanceof Driver) {

                /*Driver oldDriver = (Driver)user;
                Driver newDriver = new Driver(oldDriver);
                Licence oldLicence = oldDriver.getLicence();
                Licence newLicence = new Licence(oldLicence.getType(), oldLicence.getNumber(), oldLicence.getIssueDate(), oldLicence.getExpiryDate());
                newDriver.setLicence(newLicence);
                newLicence.addObserver(newDriver);

                //TODO this fixes the other problem but ends up in infinite loop because "changed" and "obs" fields of Licence aren't transient

                checkExpiry(newDriver);*/



                fxml.loadScene(driverDashboard);
            } else {
                fxml.loadScene(passengerDashboard);
            }
        } else {
            errorMessageLabel.setText("Incorrect ID or password");
        }
    }

    @FXML
    protected void loadCreateUser(ActionEvent event) throws Exception {
        fxml.loadScene(createUser);
    }

    private boolean hasCorrectPassword(String userId, String password) {
        User rssUser = session.getRss().getUserById(userId);
        if (rssUser != null) {
            String rssPassword = rssUser.getPassword();
            return password.equals(rssPassword);
        } else {
            return false;
        }
    }
}
