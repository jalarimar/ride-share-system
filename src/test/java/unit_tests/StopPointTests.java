package unit_tests;


import enums.RideStatus;
import models.*;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class StopPointTests {


    @Test
    public void CalculateDistFromUniOnInstantiation() {
        StopPoint stopPoint = new StopPoint(24, "Corfe St", "Ilam");
        Assert.assertTrue(stopPoint.getDistanceFromUni() > 0);
    }

    @Test
    public void ifStreetNumberNegativeUseAbsolute() {
        StopPoint stopPoint = new StopPoint(-2, "Mulberry Grove", "Warwickville");
        Assert.assertTrue(stopPoint.getStreetNumber().equals("2"));
    }

    @Test
    public void testToStringFormat() {
        StopPoint stopPoint = new StopPoint(2, "Mulberry Grove", "Warwickville");
        String expected = (stopPoint.getStreetNumber() + " " + stopPoint.getStreetName() + ", " + stopPoint.getSuburb());
        Assert.assertEquals(expected, stopPoint.toString());
    }

    @Test
    public void PriceDecreasesWhenPerformanceIncreases() {
        StopPoint stopPoint = new StopPoint(24, "Corfe St", "Ilam");
        RideStopPoint rideStopPoint = new RideStopPoint(stopPoint, LocalDateTime.now(), LocalDate.now());

        Vehicle vehicle = new Vehicle("Ford", "Fiesta", "White", "JH007", 10, 2001, 3, LocalDate.now(), LocalDate.now());
        Driver driver = new Driver("J", "H", "jha53", "torchwood", "jha53@uclive.ac.nz", stopPoint, "0", "0", null);
        driver.addVehicle(vehicle);

        TripDetails tripDetails = new TripDetails("JH007", driver, true, false, new ArrayList<>(), LocalDate.now());
        List rsps = new ArrayList();
        rsps.add(new RideStopPoint(Rss.getInstance().getUniversityStopPoint(), LocalDateTime.now().plusHours(1), LocalDate.now()));
        rsps.add(rideStopPoint);
        Ride ride = new Ride(tripDetails, rsps);
        ride.setStatus(RideStatus.AVAILABLE);

        rideStopPoint.calculatePrice(ride);
        double oldPrice = rideStopPoint.getPrice();
        vehicle.setPerformance(vehicle.getPerformance() + 10);
        double newPrice = rideStopPoint.getPrice();

        Assert.assertTrue(oldPrice > newPrice);
    }

}