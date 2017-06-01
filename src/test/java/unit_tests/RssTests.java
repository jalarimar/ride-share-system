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

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created 30/05/2017.
 */
public class RssTests {
    Vehicle vehicle;
    Driver driver;
    Ride ride;

    @Before
    public void setUp() {
        // mock a vehicle
        vehicle = mock(Vehicle.class);
        when(vehicle.getLicensePlate()).thenReturn("JH007");
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(vehicle);

        // mock a driver
        driver = mock(Driver.class);
        when(driver.getVehicles()).thenReturn(vehicles);
        when(driver.getUniID()).thenReturn("jha53");
        when(driver.getPassword()).thenReturn("correcthorsebatterystaple");
        Rss.getInstance().addUser(driver);
        SessionManager.getInstance().setCurrentUser(driver);

        // mock a ride
        ride = mock(Ride.class);
        Rss.getInstance().addRide(ride);
    }

    @Test
    public void CanRetrieveVehicleByLicencePlate() {
        String licencePlate = vehicle.getLicensePlate();
        Vehicle v = Rss.getInstance().getVehicleByLicencePlate(licencePlate);
        Assert.assertEquals(vehicle, v);
    }

    @Test
    public void RideIncludedInAvailableIfStatusAvailable() {
        when(ride.getStatus()).thenReturn(RideStatus.AVAILABLE);
        List<Ride> availableRides = Rss.getInstance().getAvailableRides();
        Assert.assertTrue(availableRides.contains(ride));
    }

    @Test
    public void RideNotIncludedInAvailableIfStatusFull() {
        when(ride.getStatus()).thenReturn(RideStatus.FULL);
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
