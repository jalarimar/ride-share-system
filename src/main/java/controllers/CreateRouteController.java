package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import models.Driver;
import models.Route;
import models.Rss;
import models.StopPoint;
import utilities.Navigator;
import utilities.SessionManager;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static utilities.Validator.isAlphanumeric;

/**
 * Created 22/03/2017.
 */
public class CreateRouteController implements Initializable {

    private SessionManager main = SessionManager.getInstance();
    private Navigator fxml = new Navigator();
    private ObservableList<StopPoint> route = FXCollections.observableArrayList();
    private ObservableList<StopPoint> availablePoints = FXCollections.observableArrayList();

    @FXML TextField nameField;
    @FXML ListView<StopPoint> includedStopPointList;
    @FXML ListView<StopPoint> excludedStopPointList;
    @FXML Label errorMessage;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<StopPoint> stopPoints = main.getCurrentDriver().getStopPoints();
        availablePoints.addAll(stopPoints);
        availablePoints.add(Rss.getInstance().getUniversityStopPoint());

        excludedStopPointList.setItems(availablePoints);
        includedStopPointList.setItems(route);
    }

    @FXML
    protected void backToDashboard(ActionEvent event) throws Exception {
        fxml.backToDashboard(event);
    }

    private StopPoint getSelectedStopPoint() {
        return excludedStopPointList.getSelectionModel().getSelectedItem();
    }

    @FXML
    protected void addStopPointToRoute(ActionEvent event) {
        StopPoint selectedPoint = getSelectedStopPoint();
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

    public boolean startsOrEndsWithUni() {
        StopPoint uni = Rss.getInstance().getUniversityStopPoint();
        return (route.get(0).equals(uni) || route.get(route.size()-1).equals(uni));
    }

    @FXML
    protected void createRoute(ActionEvent event) throws Exception {
        String name = nameField.getText();
        if (isAlphanumeric(name) && startsOrEndsWithUni()) {
            Driver driver = main.getCurrentDriver();
            Route r = new Route(name, route);
            driver.addRoute(r);

            fxml.backToDashboard(event);
        } else {
            errorMessage.setText("Validation Failed");
        }
    }


    /*public void MockCreateRouteController() {
        CreateRouteController x = mock(CreateRouteController.class);
        when(x.getSelectedStopPoint()).thenReturn(new StopPoint(5, "Street", "Suburb"));

        int oldRouteSize = route.size();
        x.addStopPointToRoute();
        Assert.assertTrue(route.size() > oldRouteSize);

    }*/

}
