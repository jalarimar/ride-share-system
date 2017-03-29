package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * Created 22/03/2017.
 */
public class CreateSPController {

    private MainController main = MainController.getInstance();
    private FXMLController fxml = new FXMLController();

    @FXML
    Button dashboardButton;
    @FXML
    Button createButton;
    @FXML
    TextField numberField;
    @FXML
    TextField nameField;
    @FXML
    TextField suburbField;

    @FXML
    protected void backToDashboard(ActionEvent event) throws Exception {
        fxml.backToDashboard(event);
    }

    @FXML
    protected void createStopPoint(ActionEvent event) throws Exception {
        String number = numberField.getText();
        String name = nameField.getText();
        String suburb = suburbField.getText();

        // TODO

        fxml.backToDashboard(event);
    }


}
