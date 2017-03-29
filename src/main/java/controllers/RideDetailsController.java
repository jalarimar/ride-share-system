package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * Created 22/03/2017.
 */
public class RideDetailsController {

    private MainController main = MainController.getInstance();
    private FXMLController fxml = new FXMLController();

    @FXML
    Button dashboardButton;
    @FXML
    Button bookButton;
    @FXML
    Label nameText;
    @FXML
    Label modelText;
    @FXML
    Label colourText;
    @FXML
    Label yearText;
    @FXML
    Label seatsText;
    @FXML
    Label lengthText;
    @FXML
    Label stopsText;

    @FXML
    protected void backToDashboard(ActionEvent event) throws Exception {
        fxml.backToDashboard(event);
    }

    @FXML
    protected void bookRide(ActionEvent event) throws Exception {
        // TODO
        fxml.backToDashboard(event);
    }

    // TODO attach

}
