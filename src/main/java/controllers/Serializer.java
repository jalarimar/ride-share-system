package controllers;

import models.Driver;
import models.Passenger;
import models.Vehicle;

import java.io.*;

/**
 * Created 22/03/2017.
 */
public class Serializer {

    private final String vehicleFilePath = "/tmp/vehicle.ser";
    private final String driverFilePath = "driver.ser";
    private final String passengerFilePath = "/tmp/passenger.ser";

    public void serialize(Object obj, String filePath) {
        try {
            FileOutputStream fileOut =
                    new FileOutputStream(filePath);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(obj);
            out.close();
            fileOut.close();
            System.out.printf("Serialized data is saved in " + filePath);
        }catch(IOException i) {
            i.printStackTrace();
        }
    }

    public <T> T deserialize(String filePath) {
        try {
            FileInputStream fileIn = new FileInputStream(filePath);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            T obj = (T) in.readObject();
            in.close();
            fileIn.close();
            return obj;
        } catch (FileNotFoundException f) {
            return null;
        } catch(IOException i) {
            i.printStackTrace();
            return null;
        }catch(ClassNotFoundException c) {
            System.out.println("Class not found");
            c.printStackTrace();
            return null;
        }
    }



/////////////////////

    public void serializeVehicle(Vehicle vehicle) {
        try {
            FileOutputStream fileOut =
                    new FileOutputStream(vehicleFilePath);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(vehicle);
            out.close();
            fileOut.close();
            System.out.printf("Serialized data is saved in " + vehicleFilePath);
        }catch(IOException i) {
            i.printStackTrace();
        }
    }

    public Vehicle deserializeVehicle() {
        try {
            FileInputStream fileIn = new FileInputStream(vehicleFilePath);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            Vehicle vehicle = (Vehicle) in.readObject();
            in.close();
            fileIn.close();
            return vehicle;
        }catch(IOException i) {
            i.printStackTrace();
            return null;
        }catch(ClassNotFoundException c) {
            System.out.println("Vehicle class not found");
            c.printStackTrace();
            return null;
        }
    }

    public void serializeDriver(Driver driver) {
        try {
            FileOutputStream fileOut =
                    new FileOutputStream(driverFilePath);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(driver);
            out.close();
            fileOut.close();
            System.out.printf("Serialized data is saved in " + driverFilePath);
        }catch(IOException i) {
            i.printStackTrace();
        }
    }

    public Driver deserializeDriver() {
        try {
            FileInputStream fileIn = new FileInputStream(driverFilePath);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            Driver driver = (Driver) in.readObject();
            in.close();
            fileIn.close();
            return driver;
        } catch (FileNotFoundException f) {
            return null;
        } catch(IOException i) {
            i.printStackTrace();
            return null;
        }catch(ClassNotFoundException c) {
            System.out.println("Driver class not found");
            c.printStackTrace();
            return null;
        }
    }

    public void serializePassenger(Vehicle vehicle) {
        try {
            FileOutputStream fileOut =
                    new FileOutputStream(vehicleFilePath);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(vehicle);
            out.close();
            fileOut.close();
            System.out.printf("Serialized data is saved in " + vehicleFilePath);
        }catch(IOException i) {
            i.printStackTrace();
        }
    }

    public Passenger deserializePassenger() {
        try {
            FileInputStream fileIn = new FileInputStream(passengerFilePath);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            Passenger passenger = (Passenger) in.readObject();
            in.close();
            fileIn.close();
            return passenger;
        } catch (FileNotFoundException f) {
            return null;
        } catch(IOException i) {
            i.printStackTrace();
            return null;
        }catch(ClassNotFoundException c) {
            System.out.println("Passenger class not found");
            c.printStackTrace();
            return null;
        }
    }
}
