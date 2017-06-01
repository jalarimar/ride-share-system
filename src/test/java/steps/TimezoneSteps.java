package steps;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import models.*;
import org.junit.Assert;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static utilities.Converter.toZonedTime;

/**
 * Created on 11/05/17.
 */
public class TimezoneSteps {
    RideStopPoint rideStopPoint;
    ZonedDateTime zonedDateTime;
    String readableTime;

    @Given("^I have a stop point with a time$")
    public void iHaveRideStopPoint() {
        StopPoint blank = new StopPoint("", "", "");
        LocalDateTime plannedTime = LocalDateTime.of(2017,7,2,5,00);
        rideStopPoint = new RideStopPoint(blank, plannedTime, LocalDate.of(2017,7,2));
    }

    @When("^I get the stop point's zoned time$")
    public void iGetZonedTime() throws Throwable {
        zonedDateTime = rideStopPoint.getZonedTime();
    }

    @Then("^the timezone is (.*)$")
    public void notMissRide(String timezone) throws Throwable {
        ZoneId zone = zonedDateTime.getZone();
        Assert.assertEquals(ZoneId.of(timezone), zone);
    }

    @When("^I get the stop point's readable time$")
    public void iGetReadableTime() throws Throwable {
        readableTime = rideStopPoint.getTime();
    }

    @Then("^it is in the AM/PM format$")
    public void ampm() throws Throwable {
        Assert.assertEquals("am", readableTime.substring(readableTime.length()-2));
    }
}
