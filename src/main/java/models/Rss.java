package models;

import controllers.SessionManager;

import java.util.*;

import static controllers.Serializer.saveRss;
import static models.Status.AVAILABLE;

/**
 * Created 26/04/2017.
 */
public class Rss {

    private HashMap<String, User> allUsers;
    private HashMap<String, Driver> allDrivers;
    private Collection<StopPoint> allStopPoints;
    private HashMap<UUID, Ride> allRides;

    public Rss() {
        allUsers = new HashMap<>();
        allDrivers = new HashMap<>();
        allStopPoints = new HashSet<>();
        allRides = new HashMap<>();
    }

    public Collection<User> getAllUsers() {
        return allUsers.values();
    }
    public Collection<Driver> getAllDrivers() {
        return allDrivers.values();
    }
    public Collection<StopPoint> getAllStopPoints() {
        return allStopPoints;
    }
    public Collection<Ride> getAllRides() {
        return allRides.values();
    }

    public void addUser(User user) {
        allUsers.put(user.getUniID(), user);

        if (user instanceof Driver) {
            allDrivers.put(user.getUniID(), (Driver)user);
        }
    }

    public void removeDriver(String id) {
        if (allDrivers.containsKey(id)) {
            allDrivers.remove(id);
        }
    }

    public void addStopPoint(StopPoint stopPoint) {
        allStopPoints.add(stopPoint);
    }
    public void addRide(Ride ride) {
        allRides.put(ride.getId(), ride);
    }

    public User getUserById(String id) {
        Driver driver = allDrivers.get(id);
        if (driver != null) {
            return driver;
        } else {
            return allUsers.get(id);
        }
    }
    public Driver getDriverById(String id) {
        return allDrivers.get(id);
    }

    public void saveModifiedUser(User user) {
        String id = user.getUniID();
        allUsers.replace(id, user);
    }

    public void saveModifiedDriver(Driver driver) {
        String id = driver.getUniID();
        allDrivers.replace(id, driver);
    }

    public void saveModifiedRide(Ride ride) {
        UUID id = ride.getId();
        allRides.replace(id, ride);
    }

    public List<Ride> getAvailableRides() {
        List<Ride> availableRides = new ArrayList<>();
        for (Ride ride : allRides.values()) {
            if (ride.getStatus() == AVAILABLE) {
                availableRides.add(ride);
            }
        }
        return availableRides;
    }
}
