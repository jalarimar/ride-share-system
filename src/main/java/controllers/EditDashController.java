package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import models.Vehicle;
import utilities.Navigator;
import utilities.SessionManager;

import java.net.URL;
import java.util.ResourceBundle;

import static utilities.Navigator.editLicence;
import static utilities.Navigator.editUser;
import static utilities.Navigator.editVehicle;

/**
 * Created 09/05/2017.
 */
public class EditDashController implements Initializable {

    private SessionManager session = SessionManager.getInstance();
    private Navigator fxml = new Navigator();

    @FXML
    ListView vehicleList;
    @FXML
    Button licenceButton;
    @FXML
    Button vehicleButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<Vehicle> vehicles = FXCollections.observableArrayList();
        if (session.getCurrentDriver() != null) {
            vehicles.addAll(session.getCurrentDriver().getVehicles());
            if (vehicles.size() > 0) {
                vehicleList.setItems(vehicles);
            }
        } else {
            licenceButton.setVisible(false);
            vehicleButton.setVisible(false);
            vehicleList.setVisible(false);
        }

    }

    @FXML
    protected void backToDashboard(ActionEvent event) throws Exception {
        fxml.backToDashboard(event);
    }

    @FXML
    protected void loadEditPersonal(ActionEvent event) throws Exception {
        fxml.loadScene(editUser);
    }

    @FXML
    protected void loadEditLicence(ActionEvent event) throws Exception {
        fxml.loadScene(editLicence);
    }

    @FXML
    protected void loadEditVehicle(ActionEvent event) throws Exception {
        Vehicle vehicle = (Vehicle) vehicleList.getSelectionModel().getSelectedItem();
        if (vehicle != null) {
            session.setFocusedVehicle(vehicle);
            fxml.loadScene(editVehicle);
        }
    }
}
