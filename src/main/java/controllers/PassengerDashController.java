package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * Created 22/03/2017.
 */
public class PassengerDashController {

    private FXMLController fxml = new FXMLController();

    private final String spSearch = "/stoppointsearch.fxml";

    @FXML
    Button searchScreenButton;

    @FXML
    protected void loadSearchScreen(ActionEvent event) throws Exception {
        fxml.loadScene(spSearch);
    }


}
