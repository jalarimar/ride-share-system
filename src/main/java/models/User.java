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

    private transient Rss rss = SessionManager.getInstance().getRss();

    // TODO remove only used by Driver
    public User(String firstName, String lastName, boolean isDriver) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.isDriver = isDriver;

        rss.addUser(this);
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

        rss.addUser(this);
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
        rss.updateUser(this);
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
        rss.updateUser(this);
    }

    public void setIsDriver(boolean driver) {
        isDriver = driver;
        rss.updateUser(this);
    }

    public void setPassword(String password) {
        this.password = password;
        rss.updateUser(this);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHomeNumber() {
        return homeNumber;
    }

    public void setHomeNumber(String homeNumber) {
        this.homeNumber = homeNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public File getPhoto() {
        return photo;
    }

    public void setPhoto(File photo) {
        this.photo = photo;
    }
}
