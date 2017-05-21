package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import models.Rss;
import models.StopPoint;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;

import static controllers.Navigator.rideSearch;

/**
 * Created 22/03/2017.
 */
public class SearchSPController implements Initializable {

    private SessionManager session = SessionManager.getInstance();
    private Navigator fxml = new Navigator();

    private java.util.List<StopPoint> allStopPoints = new ArrayList<>();
    private ObservableList<StopPoint> visibleStopPoints = FXCollections.observableArrayList();

    @FXML ChoiceBox searchType;
    @FXML TextField searchText;
    @FXML TableView spTable;
    @FXML TableColumn numberCol;
    @FXML TableColumn nameCol;
    @FXML TableColumn suburbCol;
    @FXML TableColumn distanceCol;
    @FXML ImageView mapView;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        searchType.setItems(FXCollections.observableArrayList("", "Street", "Suburb"));

        allStopPoints.addAll(Rss.getInstance().getAllStopPoints());
        visibleStopPoints.addAll(allStopPoints);

        Collections.sort(visibleStopPoints, Comparator.comparing(StopPoint::getStreetNumber));

        spTable.setItems(visibleStopPoints);

        numberCol.setCellValueFactory(new PropertyValueFactory<StopPoint,String>("streetNumber"));
        nameCol.setCellValueFactory(new PropertyValueFactory<StopPoint,String>("streetName"));
        suburbCol.setCellValueFactory(new PropertyValueFactory<StopPoint,String>("suburb"));
        distanceCol.setCellValueFactory(new PropertyValueFactory<StopPoint,Double>("distanceFromUni"));

        spTable.getColumns().setAll(numberCol, nameCol, suburbCol, distanceCol);

        String defaultAddress = "University Dr, Ilam";
        loadMap(defaultAddress);

        waitForSelection();
    }

    private void loadMap(String address) {

        String imageUrl = "";
        String baseUrl = "https://maps.googleapis.com/maps/api/staticmap?";

        try {
            // Set parameters
            String encodedAddress = URLEncoder.encode(address, "UTF-8");
            String center = "&center=" + encodedAddress;
            String zoom = "&zoom=15";
            String size = "&size=300x300";
            String marker = "&markers=color:red%7Clabel:S%7C" + encodedAddress;
            String apikey = "&key=AIzaSyANsJXqXNXXfi_8CywO2TcW05SWtmyD9d0";
            String parameters = center + zoom + size + marker + apikey;

            imageUrl = baseUrl + parameters;

        } catch (Exception e) {
            System.out.println("Could not connect.");
        }

        if (!imageUrl.equals("")) {
            Image map = new Image(imageUrl);
            mapView.setImage(map);
        }
    }

    private void waitForSelection() {
        spTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                loadMap(newSelection.toString());
            }
        });
    }

    @FXML
    protected void findRides() {
        StopPoint stopPoint = (StopPoint)spTable.getSelectionModel().getSelectedItem();
        if (stopPoint != null) {
            session.setFocusedStopPoint(stopPoint);
            fxml.loadScene(rideSearch);
        }
    }

    @FXML
    protected void backToDashboard(ActionEvent event) throws Exception {
        fxml.backToDashboard(event);
    }

    @FXML
    protected void searchStopPoints(ActionEvent event) throws Exception {
        String type = (String)searchType.getSelectionModel().getSelectedItem();
        String query = searchText.getText();

        visibleStopPoints.clear();

        for (StopPoint sp : allStopPoints) {
            String street = sp.getStreetName();
            String suburb = sp.getSuburb();

            if (type != null && !type.isEmpty()) {
                switch (type) {
                    case "Street":
                        if (street.toLowerCase().contains(query.toLowerCase())) {
                            visibleStopPoints.add(sp);
                        }
                        break;
                    case "Suburb":
                        if (suburb.toLowerCase().contains(query.toLowerCase())) {
                            visibleStopPoints.add(sp);
                        }
                        break;
                    default:
                        break;
                }
            } else {
                if (street.toLowerCase().contains(query.toLowerCase())) {
                    visibleStopPoints.add(sp);
                } else if (suburb.toLowerCase().contains(query.toLowerCase())) {
                    visibleStopPoints.add(sp);
                }
            }
        }
        Collections.sort(visibleStopPoints, Comparator.comparing(StopPoint::getStreetNumber));
    }
}
