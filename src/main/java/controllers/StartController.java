package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import models.Driver;
import models.User;

import java.net.URL;
import java.util.ResourceBundle;

import static controllers.FXMLNavigator.*;


/**
 * Created 22/03/2017.
 */
public class StartController {

    private SessionManager session = SessionManager.getInstance();
    private FXMLNavigator fxml = new FXMLNavigator();

    @FXML TextField usernameField;
    @FXML PasswordField passwordField;
    @FXML Label errorMessageLabel;
    @FXML Button loginButton;
    @FXML Button registerButton;

    @FXML
    protected void attemptLogin(ActionEvent event) throws Exception {
        String userId = usernameField.getText();
        String password = passwordField.getText();

        if (hasCorrectPassword(userId, password)) {

            User user = session.getRss().getUserById(userId);
            session.setCurrentUser(user);

            // load driver or passenger dashboard
            if (user instanceof Driver) {
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
