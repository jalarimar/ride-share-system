package controllers;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import models.Route;
import models.StopPoint;
import models.Vehicle;

import java.net.URL;
import java.util.*;

/**
 * Created 22/03/2017.
 */
public class SearchSPController implements Initializable {

    private MainController main = MainController.getInstance();
    private FXMLController fxml = new FXMLController();

    private final String rideSearch = "/ridesearch.fxml";

    private java.util.List<StopPoint> allStopPoints = new ArrayList<>();
    private ObservableList<StopPoint> visibleStopPoints = FXCollections.observableArrayList();

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


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        searchType.setItems(FXCollections.observableArrayList("", "Street", "Suburb"));

        // TODO remove this block, only for testing
        main.addStopPoint(new StopPoint(150, "Molesworth Street", "Thorndon"));
        main.addStopPoint(new StopPoint(24, "Kayell Ave", "Tawa"));
        main.addStopPoint(new StopPoint(93, "Milo Drive", "Kensington"));
        for (StopPoint sp : main.getDriver().getStopPoints()) {
            main.addStopPoint(sp);
        }

        allStopPoints.addAll(main.getAllStopPoints());
        visibleStopPoints.addAll(allStopPoints);

        Collections.sort(visibleStopPoints, (StopPoint sp1, StopPoint sp2) -> sp1.getStreetNumAsInt().compareTo(sp2.getStreetNumAsInt()));

        spTable.setItems(visibleStopPoints);

        numberCol.setCellValueFactory(new PropertyValueFactory<StopPoint,Integer>("streetNumber"));
        nameCol.setCellValueFactory(new PropertyValueFactory<StopPoint,String>("streetName"));
        suburbCol.setCellValueFactory(new PropertyValueFactory<StopPoint,String>("suburb"));

        spTable.getColumns().setAll(numberCol, nameCol, suburbCol);


        waitForSelection();
    }

    private void waitForSelection() {
        spTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                main.setFocusedStopPoint((StopPoint)newSelection);
                try {
                    fxml.loadScene(rideSearch);
                } catch (Exception ex) {
                    System.out.println("Load Scene Failed");
                }

            }
        });
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
        Collections.sort(visibleStopPoints, (StopPoint sp1, StopPoint sp2) -> sp1.getStreetNumAsInt().compareTo(sp2.getStreetNumAsInt()));
    }
}
