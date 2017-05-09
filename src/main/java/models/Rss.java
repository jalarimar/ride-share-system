package models;

import controllers.SessionManager;

import java.util.*;

import static controllers.Serializer.saveRss;
import static models.RideStatus.AVAILABLE;

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

        saveRss(this);
    }

    public void removeDriver(String id) {
        if (allDrivers.containsKey(id)) {
            allDrivers.remove(id);
        }

        saveRss(this);
    }

    public void addStopPoint(StopPoint stopPoint) {
        allStopPoints.add(stopPoint);
        saveRss(this);
    }
    public void addRide(Ride ride) {
        allRides.put(ride.getId(), ride);
        saveRss(this);
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

    public Ride getRideById(UUID id) {
        return allRides.get(id);
    }

    public void updateUser(User user) {
        String id = user.getUniID();
        allUsers.replace(id, user);

        if (user instanceof Driver) {
            allDrivers.replace(id, (Driver)user);
        }
        saveRss(this);
    }

    public void updateRide(Ride ride) {
        UUID id = ride.getId();
        allRides.replace(id, ride);

        saveRss(this);
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
