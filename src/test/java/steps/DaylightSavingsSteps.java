package steps;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import models.*;
import org.junit.Assert;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static utilities.Converter.toZonedTime;

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
        LocalDateTime plannedTime = LocalDateTime.of(2017,4,2,hour,minute);

        rideStopPoint = mock(RideStopPoint.class);
        when(rideStopPoint.getRawTime()).thenReturn(plannedTime);
        List<RideStopPoint> rideStopPoints = new ArrayList<>();
        rideStopPoints.add(rideStopPoint);

        TripDetails tripDetails = mock(TripDetails.class);
        earlyRide = new Ride(tripDetails, rideStopPoints);
    }

    @Then("^I haven't missed the ride$")
    public void notMissRide() throws Throwable {
        Assert.assertTrue(currentTime.compareTo(earlyRide.getTime()) < 0);
    }
}
