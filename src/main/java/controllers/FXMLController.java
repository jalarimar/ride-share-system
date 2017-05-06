package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * Created 22/03/2017.
 */
public class FXMLController {

    private MainController main = MainController.getInstance();

    private final String driverDashboard = "/driverdash.fxml";
    private final String passengerDashboard = "/passengerdash.fxml";

    private boolean userIsDriver;

    public FXMLController() {
        if (main.getUser() != null) {
            this.userIsDriver = main.getUser().isDriver();
        } else {
            this.userIsDriver = false;
        }
    }

    public void loadScene(String path) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        Parent root = loader.load();
        Scene scene = new Scene(root, 600, 500);
        Stage stage = main.getStage();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public void backToDashboard(ActionEvent event) throws Exception {
        if (userIsDriver) {
            loadScene(driverDashboard);
        } else {
            loadScene(passengerDashboard);
        }
    }
}
