package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import static controllers.Navigator.*;

/**
 * Created 22/03/2017.
 */
public class DriverDashController {

    private Navigator fxml = new Navigator();

    @FXML
    protected void loadCreateCar(ActionEvent event) throws Exception {
        fxml.loadScene(createCar);
    }

    @FXML
    protected void loadCreateSp(ActionEvent event) throws Exception {
        fxml.loadScene(createSp);
    }

    @FXML
    protected void loadCreateRoute(ActionEvent event) throws Exception {
        fxml.loadScene(createRoute);
    }

    @FXML
    protected void loadCreateRide(ActionEvent event) throws Exception {
        fxml.loadScene(createRide);
    }

    @FXML
    protected void loadDriverRides(ActionEvent event) throws Exception {
        fxml.loadScene(myRides);
    }

    @FXML
    protected void loadSearchScreen(ActionEvent event) throws Exception {
        fxml.loadScene(spSearch);
    }

    @FXML
    protected void loadEditDashboard(ActionEvent event) throws Exception {
        fxml.loadScene(editDashboard);
    }

    @FXML
    protected void viewBookedRides(ActionEvent event) throws Exception {
        fxml.loadScene(bookedRides);
    }

}
