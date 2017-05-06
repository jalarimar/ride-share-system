package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import models.Rss;
import models.User;


/**
 * Created 22/03/2017.
 */
public class StartController {

    private MainController main = MainController.getInstance();
    private FXMLController fxml = new FXMLController();

    private final String driverDashboard = "/driverdash.fxml";
    private final String passengerDashboard = "/passengerdash.fxml";
    private final String createUser = "/createuser.fxml";

    @FXML TextField usernameField;
    @FXML PasswordField passwordField;
    @FXML Label errorMessageLabel;
    @FXML Button loginButton;
    @FXML Button registerButton;


    @FXML
    protected void attemptLogin(ActionEvent event) throws Exception {
        // find user and check password
        String userId = usernameField.getText();
        String password = passwordField.getText();
        if (hasCorrectPassword(userId, password)) {

            //User user = Rss.getUser();
            //main.setUser(user);

            // load driver or passenger dashboard
            if (main.getUser().isDriver()) {
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
        // TODO check password matches user password in json
        return false;
    }

}
