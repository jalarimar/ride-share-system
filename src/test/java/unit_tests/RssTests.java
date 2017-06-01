package unit_tests;

import enums.RideStatus;
import models.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import utilities.SessionManager;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ValueRange;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created 30/05/2017.
 */
public class RssTests {
    Vehicle vehicle;
    Driver driver;
    Ride ride;

    @Before
    public void setUp() {
        vehicle = new Vehicle("Ford", "Fiesta", "White", "JH007", 10, 2001, 3, LocalDate.now(), LocalDate.now());
        StopPoint stopPoint = new StopPoint(80, "Rattray Street", "Riccarton");
        driver = new Driver("J", "H", "jha53", "torchwood", "jha53@uclive.ac.nz", stopPoint, "0", "0", null);
        driver.addVehicle(vehicle);

        TripDetails tripDetails = new TripDetails("JH007", driver, true, false, new ArrayList<>(), LocalDate.now());
        RideStopPoint rideStopPoint = new RideStopPoint(stopPoint, LocalDateTime.now(), LocalDate.now());
        List rsps = new ArrayList();
        rsps.add(new RideStopPoint(Rss.getInstance().getUniversityStopPoint(), LocalDateTime.now().plusHours(1), LocalDate.now()));
        rsps.add(rideStopPoint);
        ride = new Ride(tripDetails, rsps);
        SessionManager.getInstance().setCurrentUser(driver);
    }

    @Test
    public void CanRetrieveVehicleByLicencePlate() {
        String licencePlate = vehicle.getLicensePlate();
        Vehicle v = Rss.getInstance().getVehicleByLicencePlate(licencePlate);
        Assert.assertEquals(vehicle, v);
    }

    @Test
    public void RideIncludedInAvailableIfStatusAvailable() {
        ride.setStatus(RideStatus.AVAILABLE);
        List<Ride> availableRides = Rss.getInstance().getAvailableRides();
        Assert.assertTrue(availableRides.contains(ride));
    }

    @Test
    public void RideNotIncludedInAvailableIfStatusFull() {
        ride.setStatus(RideStatus.FULL);
        List<Ride> availableRides = Rss.getInstance().getAvailableRides();
        Assert.assertFalse(availableRides.contains(ride));
    }

    @Test
    public void ModifyingAvailableRidesDoesNotModifyOriginalList() {
        List<Ride> modifiedAvailableRides = Rss.getInstance().getAvailableRides();
        modifiedAvailableRides.clear();
        List<Ride> newAvailableRides = Rss.getInstance().getAvailableRides();
        Assert.assertTrue(modifiedAvailableRides.size() != newAvailableRides.size() || newAvailableRides.size() == 0);
    }

    @Test
    public void DriverListSubsetOfUserList() {
        Collection<User> users = Rss.getInstance().getAllUsers();
        Collection<Driver> drivers = Rss.getInstance().getAllDrivers();
        Assert.assertTrue(drivers.size() <= users.size());
    }

    @Test
    public void RssNeverNull() {
        Assert.assertTrue(Rss.getInstance() != null);
    }

    @Test
    public void IncorrectPasswordFails() {
        String passwordAttempt = driver.getPassword() + "attempt";
        Assert.assertFalse(Rss.getInstance().hasCorrectPassword(driver.getUniID(), passwordAttempt));
    }

    @Test
    public void CorrectPasswordSucceeds() {
        String passwordAttempt = driver.getPassword();
        Assert.assertTrue(Rss.getInstance().hasCorrectPassword(driver.getUniID(), passwordAttempt));
    }
}
