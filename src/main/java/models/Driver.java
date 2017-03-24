package models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created 21/03/2017.
 */
public class Driver extends User implements AutoCloseable, Serializable {

    private static final long serialVersionUID = 1L;
    //private String photo;
    //private String grade;
    private ArrayList<Vehicle> vehicles;

    public Driver(String firstName, String lastName) {
        super(firstName, lastName, true);
        this.vehicles = new ArrayList<>();
    }

    public void close() {
        this.close();
    }
}
