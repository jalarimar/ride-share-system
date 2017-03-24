package models;

import java.io.Serializable;

/**
 * Created 21/03/2017.
 */
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    private String firstName;
    private String lastName;
    private boolean isDriver;

    public User(String firstName, String lastName, boolean isDriver) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.isDriver = isDriver;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isDriver() {
        return isDriver;
    }

    public void setDriver(boolean driver) {
        isDriver = driver;
    }
}
