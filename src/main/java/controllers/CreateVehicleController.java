package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import models.Vehicle;

import java.util.ArrayList;

/**
 * Created 22/03/2017.
 */
public class CreateVehicleController {

    private MainController main = MainController.getInstance();
    private FXMLController fxml = new FXMLController();

    private final String vehicleFilePath = "vehicle.ser"; // TODO make name based on license plate?

    @FXML
    Button dashboardButton;
    @FXML
    Button createButton;
    @FXML
    TextField vehicleType;
    @FXML
    TextField vehicleModel;
    @FXML
    TextField vehicleColour;
    @FXML
    TextField vehicleLp;
    @FXML
    TextField vehicleYear;
    @FXML
    TextField vehicleNos;

    @FXML
    protected void backToDashboard(ActionEvent event) throws Exception {
        fxml.backToDashboard(event);
    }

    @FXML
    protected void createVehicle(ActionEvent event) throws Exception {

        // TODO validate user input

        String type = vehicleType.getText();
        String model = vehicleModel.getText();
        String colour = vehicleColour.getText();
        String licensePlate = vehicleLp.getText();
        String performance = "";
        int year = Integer.parseInt(vehicleYear.getText());
        int numSeats = Integer.parseInt(vehicleNos.getText());

        Vehicle vehicle = new Vehicle(type, model, colour, licensePlate, performance, year, numSeats);

        // TODO need to make this the driver's vehicle
        ArrayList<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(vehicle);

        // TODO deserialise vehicles as part of "login" function
        Serializer serializer = new Serializer();
        serializer.serialize(vehicles, vehicleFilePath);

        fxml.backToDashboard(event);
    }


}
