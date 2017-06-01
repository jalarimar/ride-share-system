package steps;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import models.*;
import org.junit.Assert;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static utilities.Converter.toZonedTime;
import static utilities.Validator.startsOrEndsWithUni;

/**
 * Created on 11/05/17.
 */
public class DaylightSavingsSteps {
    RideStopPoint rideStopPoint;
    ZonedDateTime currentTime;
    LocalDateTime lastTime;
    Ride earlyRide;

    @Given("^it was (\\d+):(\\d+)am the last time I looked at my clock and daylight savings ends at 2am$")
    public void itIs150am(int hour, int minute) {
        lastTime = LocalDateTime.of(2017,4,2,hour,minute);
    }

    @And("^it has been (\\d+) hours since then$")
    public void twoHoursSince(int nHours) {
        currentTime = toZonedTime(lastTime).plusHours(nHours);
    }

    @When("^I try to catch a (\\d+):(\\d+)am ride$")
    public void rideIs230(int hour, int minute) throws Throwable {
        StopPoint blank = new StopPoint("", "", "");
        LocalDateTime plannedTime = LocalDateTime.of(2017,4,2,hour,minute);
        rideStopPoint = new RideStopPoint(blank, plannedTime, LocalDate.of(2017,4,2));
        List<RideStopPoint> rideStopPoints = new ArrayList<>();
        rideStopPoints.add(rideStopPoint);

        Vehicle vehicle = new Vehicle("Ford", "Fiesta", "White", "JH007", 10, 2001, 3, LocalDate.now(), LocalDate.now());
        StopPoint stopPoint = new StopPoint(80, "Rattray Street", "Riccarton");
        Driver driver = new Driver("J", "H", "jha53", "torchwood", "jha53@uclive.ac.nz", stopPoint, "0", "0", null);
        driver.addVehicle(vehicle);

        TripDetails tripDetails = new TripDetails("JH007", driver, true, false, new ArrayList<>(), LocalDate.now());
        earlyRide = new Ride(tripDetails, rideStopPoints);
    }

    @Then("^I haven't missed the ride$")
    public void notMissRide() throws Throwable {
        Assert.assertTrue(currentTime.compareTo(earlyRide.getTime()) < 0);
    }
}
