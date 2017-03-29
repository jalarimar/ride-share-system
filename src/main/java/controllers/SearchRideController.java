package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * Created 22/03/2017.
 */
public class SearchRideController {

    private MainController main = MainController.getInstance();
    private FXMLController fxml = new FXMLController();

    @FXML
    Button dashboardButton;
    @FXML
    Button bookButton;
    @FXML
    TableView rideTable;
    @FXML
    TableColumn dayCol;
    @FXML
    TableColumn timeCol;
    @FXML
    TableColumn directionCol;
    @FXML
    TableColumn seatCol;

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
