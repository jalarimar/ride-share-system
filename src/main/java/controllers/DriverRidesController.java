package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

/**
 * Created 22/03/2017.
 */
public class DriverRidesController {

    private MainController main = MainController.getInstance();
    private FXMLController fxml = new FXMLController();

    @FXML
    Button dashboardButton;
    @FXML
    Button shareButton;
    @FXML
    ListView rideList;
    @FXML
    ChoiceBox availableSeatsChoice;
    @FXML
    Label rideNameLabel;

    @FXML
    protected void backToDashboard(ActionEvent event) throws Exception {
        fxml.backToDashboard(event);
    }

    // TODO attach listview to ride list, attach label to name upon selection

    @FXML
    protected void shareRide(ActionEvent event) throws Exception {
        // TODO
        fxml.backToDashboard(event);
    }


}
