package models;


import controllers.SessionManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
    private StopPoint address;
    private String homeNumber;
    private String mobileNumber;
    private File photo;
    private List<UUID> bookedRideIds;

    // TODO remove only used by Driver
    public User(String firstName, String lastName, boolean isDriver) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.isDriver = isDriver;

        Rss.getInstance().addUser(this);
    }

    public User(String firstName, String lastName, boolean isDriver, String uniID, String password, String email, StopPoint address, String homeNumber, String mobileNumber, File photo) {
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
        this.bookedRideIds = new ArrayList<>();

        Rss.getInstance().addUser(this);
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
    public String getEmail() {
        return email;
    }
    public String getHomeNumber() {
        return homeNumber;
    }
    public StopPoint getAddress() {
        return address;
    }
    public String getMobileNumber() {
        return mobileNumber;
    }
    public File getPhoto() {
        return photo;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
        //rss.updateUser(this);
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public void setIsDriver(boolean driver) {
        isDriver = driver;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setHomeNumber(String homeNumber) {
        this.homeNumber = homeNumber;
    }
    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
    public void setAddress(StopPoint address) {
        this.address = address;
    }
    public void setPhoto(File photo) {
        this.photo = photo;
    }

    public void addBooking(Ride ride) {
        bookedRideIds.add(ride.getId());
    }
    public List<UUID> getBookedRideIds() {
        return bookedRideIds;
    }
    public void setBookedRideIds(List<UUID> bookedRides) {
        // used when user becomes driver
        for (UUID id : bookedRides) {
            bookedRideIds.add(id);
        }
    }
}
