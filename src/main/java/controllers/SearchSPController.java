package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.awt.*;

/**
 * Created 22/03/2017.
 */
public class SearchSPController {

    private MainController main = MainController.getInstance();
    private FXMLController fxml = new FXMLController();

    @FXML
    Button dashboardButton;
    @FXML
    ChoiceBox searchType;
    @FXML
    TextField searchText;
    @FXML
    Button searchButton;
    @FXML
    TableView spTable;
    @FXML
    TableColumn numberCol;
    @FXML
    TableColumn nameCol;
    @FXML
    TableColumn suburbCol;

    @FXML
    protected void backToDashboard(ActionEvent event) throws Exception {
        fxml.backToDashboard(event);
    }

    @FXML
    protected void searchStopPoints(ActionEvent event) throws Exception {
        // TODO
        fxml.backToDashboard(event);
    }

    // TODO attach

}
