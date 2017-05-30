package unit_tests;

import enums.RideStatus;
import models.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RideTests {
    Vehicle vehicle;
    Driver driver;
    Ride ride;
    Rss rss;

    @Before
    public void setUp() {
        vehicle = new Vehicle("Ford", "Fiesta", "White", "JH007", 10, 2001, 3, LocalDate.now(), LocalDate.now());
        StopPoint stopPoint = new StopPoint(80, "Rattray Street", "Riccarton");
        driver = new Driver("J", "H", "jha53", "torchwood", "jha53@uclive.ac.nz", stopPoint, "0", "0", null);
        driver.addVehicle(vehicle);
        TripDetails tripDetails = new TripDetails("JH007", driver, true, false, new ArrayList<>(), LocalDate.now());
        RideStopPoint rideStopPoint = new RideStopPoint(stopPoint, LocalDateTime.now(), LocalDate.now());
        List rsps = new ArrayList();
        rsps.add(rideStopPoint);
        ride = new Ride(tripDetails, rsps);

        rss = Rss.getInstance();
    }

    @After
    public void tearDown() {
        vehicle = null;
        driver = null;
        ride = null;
    }

    @Test
    public void availableSeatsLessThanPhysical() {
        ride.setAvailableSeats(7);
        Assert.assertTrue(ride.getAvailableSeats() <= vehicle.getPhysicalSeats());
    }

    @Test
    public void availableSeatsGreaterThanZero() {
        ride.setAvailableSeats(-7);
        Assert.assertTrue(ride.getAvailableSeats() >= 0);
    }

    @Test
    public void statusIsFullWhenNoAvailableSeats() {
        ride.setAvailableSeats(0);
        Assert.assertTrue(ride.getStatus() == RideStatus.FULL);
    }

    @Test
    public void statusIsNotFullWhenAvailableSeats() {
        ride.setAvailableSeats(1);
        Assert.assertFalse(ride.getStatus() == RideStatus.FULL);
    }
}