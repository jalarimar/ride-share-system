package unit_tests;

import enums.BookingStatus;
import enums.RideStatus;
import models.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import utilities.SessionManager;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RideTests {
    Vehicle vehicle;
    Driver driver;
    Ride ride;

    @Before
    public void setUp() {
        vehicle = new Vehicle("Ford", "Fiesta", "White", "JH007", 10, 2001, 3, LocalDate.now(), LocalDate.now());
        StopPoint stopPoint = new StopPoint(80, "Rattray Street", "Riccarton");
        driver = new Driver("J", "H", "jha5", "torchwood", "jha53@uclive.ac.nz", stopPoint, "0", "0", null);
        driver.addVehicle(vehicle);

        TripDetails tripDetails = new TripDetails(vehicle.getLicensePlate(), driver, true, false, new ArrayList<>(), LocalDate.now());
        RideStopPoint rideStopPoint = new RideStopPoint(stopPoint, LocalDateTime.MAX, LocalDate.MAX);
        List rsps = new ArrayList();
        rsps.add(new RideStopPoint(Rss.getInstance().getUniversityStopPoint(), LocalDateTime.MAX, LocalDate.MAX));
        rsps.add(rideStopPoint);
        ride = new Ride(tripDetails, rsps);
        SessionManager.getInstance().setCurrentUser(driver);
    }

    @Test
    public void StatusBecomesAvailableWhenRideShared() {
        ride.share(3);
        RideStatus expected = RideStatus.AVAILABLE;
        Assert.assertEquals(expected, ride.getStatus());
    }

    @Test
    public void AvailableSeatsDecreasesWhenPassengerAdded() {
        ride.share(3);
        int expected = 2;
        ride.addPassenger(driver, ride.getRideStopPoints().get(0));
        Assert.assertEquals(expected, ride.getAvailableSeats());
    }

    @Test
    public void AvailableSeatsIncreasesWhenPassengerRemoved() {
        ride.share(3);
        ride.addPassenger(driver, ride.getRideStopPoints().get(0));
        int expected = 3;
        ride.removePassenger(driver);
        Assert.assertEquals(expected, ride.getAvailableSeats());
    }

    @Test
    public void StatusBecomesFullWhenNoAvailableSeatsSet() {
        ride.setAvailableSeats(0);
        RideStatus expected = RideStatus.FULL;
        Assert.assertEquals(expected, ride.getStatus());
    }

    @Test
    public void StatusBecomesFullWhenNoAvailableSeatsLeft() {
        ride.setAvailableSeats(1);
        ride.addPassenger(driver, ride.getRideStopPoints().get(0));
        RideStatus expected = RideStatus.FULL;
        Assert.assertEquals(expected, ride.getStatus());
    }

    @Test
    public void CanFindRideStopPointOfGivenPassenger() {
        RideStopPoint rsp = ride.getRideStopPoints().get(0);
        ride.addPassenger(driver, rsp);
        Assert.assertEquals(rsp, ride.getRideStopPointOfPassenger(driver));
    }

    @Test
    public void RideNotCancellableByDriverIfDone() {
        ride.setStatus(RideStatus.DONE);
        Assert.assertFalse(ride.isCancellableByDriver());
    }

    @Test
    public void RideNotCancellableByPassengerIfDone() {
        ride.setStatus(RideStatus.DONE);
        Assert.assertFalse(ride.isCancellableByPassenger());
    }

    @Test
    public void BookingStatusIsBookedAfterPassengerAdded() {
        RideStopPoint rsp = ride.getRideStopPoints().get(0);
        ride.addPassenger(driver, rsp);
        Assert.assertEquals(BookingStatus.BOOKED.toString(), ride.getBookingStatus());
    }

    @Test
    public void BookingStatusIsDoneIfRideStatusDone() {
        ride.setStatus(RideStatus.DONE);
        Assert.assertEquals(BookingStatus.DONE.toString(), ride.getBookingStatus());
    }

    @Test
    public void NotAllowedToBookRideIfAlreadyBooked() {
        RideStopPoint rsp = ride.getRideStopPoints().get(0);
        ride.addPassenger(driver, rsp);
        Assert.assertFalse(ride.isAllowedToBookRide(driver));
    }

    @Test
    public void BookingStatusIsCancelledIfPassengerHasBeenRemoved() {
        ride.share(3);
        RideStopPoint rsp = ride.getRideStopPoints().get(0);
        ride.addPassenger(driver, rsp);
        ride.removePassenger(driver);
        Assert.assertEquals(BookingStatus.CANCELLED.toString(), ride.getBookingStatus());
    }

    @Test
    public void RideIsCancellableByPassengerIfBooked() {
        ride.setStatus(RideStatus.FULL);
        RideStopPoint rsp = ride.getRideStopPoints().get(0);
        ride.addPassenger(driver, rsp);
        Assert.assertTrue(ride.isCancellableByPassenger());
    }

    @Test
    public void RideIsCancellableByDriverIfFull() {
        ride.setStatus(RideStatus.FULL);
        Assert.assertTrue(ride.isCancellableByDriver());
    }

    @Test
    public void RideIsShareableIfUnshared() {
        ride.setStatus(RideStatus.UNSHARED);
        Assert.assertTrue(ride.isShareable());
    }

    @Test
    public void testToStringFormat() {
        String expected = "University to 80 Rattray Street, Riccarton";
        Assert.assertEquals(expected, ride.toString());
    }

    @Test
    public void availableSeatsNeverMoreThanPhysical() {
        ride.setAvailableSeats(100);
        Assert.assertTrue(ride.getAvailableSeats() <= vehicle.getPhysicalSeats());
    }

    @Test
    public void availableSeatsNeverLessThanZero() {
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

    @Test
    public void passengerGetsNotificationIfPriceUpdated() {
        ride.setStatus(RideStatus.AVAILABLE);
        ride.addPassenger(driver, ride.getRideStopPoints().get(0));
        int passengerNotifications = driver.getUnseenRideNotifications().size();
        vehicle.setPerformance(vehicle.getPerformance() + 10);
        Assert.assertTrue(driver.getUnseenRideNotifications().size() > passengerNotifications);
    }
}