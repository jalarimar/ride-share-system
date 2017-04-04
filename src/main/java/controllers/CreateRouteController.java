package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import models.Driver;
import models.Route;
import models.StopPoint;
import models.Vehicle;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created 22/03/2017.
 */
public class CreateRouteController implements Initializable {

    private MainController main = MainController.getInstance();
    private FXMLController fxml = new FXMLController();
    private ObservableList<StopPoint> route = FXCollections.observableArrayList();
    private ObservableList<StopPoint> availablePoints = FXCollections.observableArrayList();

    @FXML
    Button dashboardButton;
    @FXML
    Button addButton;
    @FXML
    Button removeButton;
    @FXML
    Button createButton;
    @FXML
    TextField nameField;
    @FXML
    ListView<StopPoint> includedStopPointList;
    @FXML
    ListView<StopPoint> excludedStopPointList;

    //TODO REMOVE
    public CreateRouteController() {
        Driver d = main.getDriver();
        d.addVehicle(new Vehicle("Ford", "Taurus", "Blue", "EXM566", "", 2003, 5));
        d.addVehicle(new Vehicle("Toyota", "Hilux", "Red", "ALB80", "", 2008, 7));

        StopPoint w = new StopPoint(42, "Wallaby Way", "Sydney");
        StopPoint s = new StopPoint(12, "Grimmauld Place", "London");
        StopPoint a = new StopPoint(2, "Weld St", "Wadestown");
        StopPoint f = new StopPoint(20, "Kirkwood Ave", "Upper Riccarton");

        d.addStopPoint(w);
        d.addStopPoint(s);
        d.addStopPoint(a);
        d.addStopPoint(f);

        main.addStopPoint(w);
        main.addStopPoint(s);
        main.addStopPoint(a);
        main.addStopPoint(f);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<StopPoint> stopPoints = main.getDriver().getStopPoints();
        availablePoints.addAll(stopPoints);

        excludedStopPointList.setItems(availablePoints);
        includedStopPointList.setItems(route);
    }

    @FXML
    protected void backToDashboard(ActionEvent event) throws Exception {
        fxml.backToDashboard(event);
    }

    @FXML
    protected void addStopPointToRoute(ActionEvent event) {
        StopPoint selectedPoint = excludedStopPointList.getSelectionModel().getSelectedItem();
        if (selectedPoint != null) {
            route.add(selectedPoint);
            availablePoints.remove(selectedPoint);
        }
    }

    @FXML
    protected void removeStopPointFromRoute(ActionEvent event) {
        StopPoint selectedPoint = includedStopPointList.getSelectionModel().getSelectedItem();
        if (selectedPoint != null) {
            route.remove(selectedPoint);
            availablePoints.add(selectedPoint);
        }
    }

    @FXML
    protected void createRoute(ActionEvent event) throws Exception {
        String name = nameField.getText();
        if (main.isValidInputString(name)) {
            Driver driver = main.getDriver();
            Route r = new Route(name, route);
            driver.addRoute(r);

            fxml.backToDashboard(event);
        } else {
            System.out.println("Validation Failed");
        }
    }


}
