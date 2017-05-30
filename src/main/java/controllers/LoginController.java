package controllers;

import enums.NotificationStatus;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import models.*;
import utilities.Navigator;
import utilities.SessionManager;

import java.time.LocalDate;
import java.util.List;

import static utilities.Navigator.*;
import static enums.NotificationStatus.*;


/**
 * Created 22/03/2017.
 */
public class LoginController {

    private SessionManager session = SessionManager.getInstance();
    private Navigator fxml = new Navigator();

    @FXML TextField usernameField;
    @FXML PasswordField passwordField;
    @FXML Label errorMessageLabel;

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

    private void checkExpiries(Driver driver) {

        LocalDate licenceExpiry = driver.getLicence().getExpiryDate();
        NotificationStatus timeUntilLicenceExpiry = getTimeUntilExpiry(licenceExpiry);
        NotificationStatus lastSeenLicenceNotification = driver.getLicence().getLastSeenNotification();
        boolean notifyLicence = timeUntilLicenceExpiry.ordinal() < lastSeenLicenceNotification.ordinal();

        boolean notifyWof = false;
        boolean notifyReg = false;

        NotificationStatus timeUntilWofExpiry = NONE;
        NotificationStatus timeUntilRegExpiry = NONE;

        String wofVehicleId = null;
        String regVehicleId = null;

        List<Vehicle> vehicles = driver.getVehicles();
        for (Vehicle vehicle : vehicles) {

            NotificationStatus wofNotification = getTimeUntilExpiry(vehicle.getWofExpiry());
            NotificationStatus regNotification = getTimeUntilExpiry(vehicle.getRegExpiry());

            // find vehicle with most urgent expiry
            if (wofNotification.ordinal() < timeUntilWofExpiry.ordinal()) {
                timeUntilWofExpiry = wofNotification;
                wofVehicleId = vehicle.getLicensePlate();
            }
            if (regNotification.ordinal() < timeUntilRegExpiry.ordinal()) {
                timeUntilRegExpiry = regNotification;
                regVehicleId = vehicle.getLicensePlate();
            }

            // determine if the notification for this vehicle has already been seen
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
            }
            if (notifyReg) {
                warning += "Your vehicle's registration expires in less than " + timeUntilRegExpiry + "\n";
                content += "Update your vehicle's licence from the edit vehicle screen.\n";
                driver.getVehicleById(regVehicleId).setLastSeenRegNotification(timeUntilRegExpiry);
            }
            if (notifyWof) {
                warning += "Your vehicle's WOF expires in less than " + timeUntilWofExpiry + "\n";
                content += "Update your WOF from the edit vehicle screen.\n";
                driver.getVehicleById(wofVehicleId).setLastSeenWofNotification(timeUntilWofExpiry);
            }

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Notifications");
            alert.setHeaderText(warning);
            alert.setContentText(content);
            alert.showAndWait();
        }
    }

    private void checkRideNotifications(User user) {
        List<String> notifications = user.getUnseenRideNotifications();
        if (notifications != null && notifications.size() > 0) {
            for (String notification : notifications) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Ride Notification");
                alert.setHeaderText(notification);
                alert.showAndWait();
            }
            notifications.clear();
        }
    }

    @FXML
    protected void attemptLogin(ActionEvent event) throws Exception {
        String userId = usernameField.getText();
        String password = passwordField.getText();

        if (hasCorrectPassword(userId, password)) {

            User user = Rss.getInstance().getUserById(userId);
            session.setCurrentUser(user);

            checkRideNotifications(user);

            // load driver or passenger dashboard
            if (user instanceof Driver) {

                checkExpiries((Driver)user);

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
        User rssUser = Rss.getInstance().getUserById(userId);
        if (rssUser != null) {
            String rssPassword = rssUser.getPassword();
            return password.equals(rssPassword);
        } else {
            return false;
        }
    }
}
