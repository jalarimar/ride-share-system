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
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

import static utilities.Validator.isAlphanumeric;
import static utilities.Validator.startsOrEndsWithUni;

/**
 * Created 22/03/2017.
 */
public class CreateRouteController implements Initializable {

    /*public void MockCreateRouteController() {
        CreateRouteController x = mock(CreateRouteController.class);
        when(x.getSelectedStopPoint()).thenReturn(new StopPoint(5, "Street", "Suburb"));

        int oldRouteSize = route.size();
        x.addStopPointToRoute();
        Assert.assertTrue(route.size() > oldRouteSize);

    }*/

    private Navigator fxml = new Navigator();
    private ObservableList<StopPoint> route = FXCollections.observableArrayList();
    private ObservableList<StopPoint> availablePoints = FXCollections.observableArrayList();

    @FXML TextField nameField;
    @FXML ListView<StopPoint> includedStopPointList;
    @FXML ListView<StopPoint> excludedStopPointList;
    @FXML Label errorMessage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Collection<StopPoint> stopPoints = Rss.getInstance().getAllStopPoints();
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

    @FXML
    protected void createRoute(ActionEvent event) throws Exception {
        String name = nameField.getText();
        if (isAlphanumeric(name) && route.size() > 0 && startsOrEndsWithUni(route)) {
            Driver driver = SessionManager.getInstance().getCurrentDriver();
            Route r = new Route(name, route);
            driver.addRoute(r);

            fxml.backToDashboard(event);
        } else {
            errorMessage.setText("Must start/end with Science Rd");
        }
    }
}
