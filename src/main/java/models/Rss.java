package models;

import java.util.*;

import static utilities.Serializer.loadRss;
import static enums.RideStatus.AVAILABLE;

/**
 * Created 26/04/2017.
 */
public class Rss {

    private static Rss instance = null;

    public static Rss getInstance() {
        if(instance == null) {
            instance = loadRss();
            if (instance == null) {
                instance = new Rss();
            }
        }
        return instance;
    }

    private HashMap<String, User> allUsers;
    private HashMap<String, Driver> allDrivers;
    private Collection<StopPoint> allStopPoints;
    private HashMap<UUID, Ride> allRides;

    private Rss() {
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

    public void addDriver(Driver driver) {
        allUsers.put(driver.getUniID(), driver);
        allDrivers.put(driver.getUniID(), driver);
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

    public Ride getRideById(UUID id) {
        return allRides.get(id);
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

    public Vehicle getVehicleByLicencePlate(String licenceNumber) {
        Vehicle vehicle = null;
        for (Driver driver : getAllDrivers()) {
            for (Vehicle v : driver.getVehicles()) {
                if (v.getLicensePlate().equals(licenceNumber)) {
                    vehicle = v;
                }
            }
        }
        return vehicle;
    }

    public StopPoint getUniversityStopPoint() {
        return new StopPoint("", "Science Rd", "Ilam");
    }
}
