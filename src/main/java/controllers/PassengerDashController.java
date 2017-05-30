package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import utilities.Navigator;

import static utilities.Navigator.*;

/**
 * Created 22/03/2017.
 */
public class PassengerDashController {

    private Navigator fxml = new Navigator();


    @FXML
    protected void loadEditPersonal(ActionEvent event) throws Exception {
        fxml.loadScene(editUser);
    }
    @FXML
    protected void loadSearchScreen(ActionEvent event) throws Exception {
        fxml.loadScene(spSearch);
    }

    @FXML
    protected void viewBookedRides(ActionEvent event) throws Exception {
        fxml.loadScene(bookedRides);
    }


}
