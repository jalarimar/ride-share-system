package models;


import java.io.File;

/**
 * Created 21/03/2017.
 */
public class User {

    private String firstName;
    private String lastName;
    private boolean isDriver;
    private String uniID;
    private String password;
    private String email;
    private String address;
    private String homeNumber;
    private String mobileNumber;
    private File photo;

    public User(String firstName, String lastName, boolean isDriver) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.isDriver = isDriver;
    }

    public User(String firstName, String lastName, boolean isDriver, String uniID, String password, String email, String address, String homeNumber, String mobileNumber, File photo) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.isDriver = isDriver;
        this.uniID = uniID;
        this.password = password;
        this.email = email;
        this.address = address;
        this.homeNumber = homeNumber;
        this.mobileNumber = mobileNumber;
        this.photo = photo;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
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

    public void setIsDriver(boolean driver) {
        isDriver = driver;
    }
}
