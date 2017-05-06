package unit_tests;


import models.Driver;
import models.Ride;
import models.Status;
import models.Vehicle;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;

public class RideTests {

    @Test
    public void testAvailableSeatsLessThanPhysical() {
        Vehicle v = new Vehicle("Ford", "Fiesta", "White", "ABC456", "", 2001, 3);
        Driver d = new Driver("Jack", "Harkness");
        Ride ride = new Ride(v, d, new ArrayList<>(), false, false, new ArrayList<>(), LocalDate.now(), LocalDate.now());
        ride.setAvailableSeats(7);
        Assert.assertTrue(ride.getAvailableSeats() <= v.getPhysicalSeats());
    }

    @Test
    public void testAvailableSeatsGreaterThanZero() {
        Vehicle v = new Vehicle("Ford", "Fiesta", "White", "ABC456", "", 2001, 3);
        Driver d = new Driver("Jack", "Harkness");
        Ride ride = new Ride(v, d, new ArrayList<>(), false, false, new ArrayList<>(), LocalDate.now(), LocalDate.now());
        ride.setAvailableSeats(-7);
        Assert.assertTrue(ride.getAvailableSeats() >= 0);
    }

    @Test
    public void testStatusIsFull() {
        Vehicle v = new Vehicle("Ford", "Fiesta", "White", "ABC456", "", 2001, 3);
        Driver d = new Driver("Jack", "Harkness");
        Ride ride = new Ride(v, d, new ArrayList<>(), false, false, new ArrayList<>(), LocalDate.now(), LocalDate.now());
        ride.setAvailableSeats(0);
        Assert.assertTrue(ride.getStatus() == Status.FULL);
    }

    @Test
    public void testStatusIsNotFull() {
        Vehicle v = new Vehicle("Ford", "Fiesta", "White", "ABC456", "", 2001, 3);
        Driver d = new Driver("Jack", "Harkness");
        Ride ride = new Ride(v, d, new ArrayList<>(), false, false, new ArrayList<>(), LocalDate.now(), LocalDate.now());
        ride.setAvailableSeats(1);
        Assert.assertFalse(ride.getStatus() == Status.FULL);
    }
}