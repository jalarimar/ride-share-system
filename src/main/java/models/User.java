package models;


import controllers.SessionManager;

import java.io.File;

import static controllers.Serializer.saveRss;

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

        SessionManager.getInstance().getRss().addUser(this);
        saveRss(SessionManager.getInstance().getRss());
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

        SessionManager.getInstance().getRss().addUser(this);
        saveRss(SessionManager.getInstance().getRss());
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }

    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public boolean isDriver() {
        return isDriver;
    }
    public String getUniID() {
        return uniID;
    }
    public String getPassword() {
        return password;
    }


    public void setFirstName(String firstName) {
        this.firstName = firstName;
        SessionManager.getInstance().getRss().updateUser(this);
        saveRss(SessionManager.getInstance().getRss());
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
        SessionManager.getInstance().getRss().updateUser(this);
        saveRss(SessionManager.getInstance().getRss());
    }

    public void setIsDriver(boolean driver) {
        isDriver = driver;
        SessionManager.getInstance().getRss().updateUser(this);
        saveRss(SessionManager.getInstance().getRss());
    }

    public void setPassword(String password) {
        this.password = password;
        SessionManager.getInstance().getRss().updateUser(this);
        saveRss(SessionManager.getInstance().getRss());
    }
}
