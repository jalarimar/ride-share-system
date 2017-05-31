package steps;

import controllers.CreateRideController;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import models.*;
import org.junit.Assert;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static utilities.Converter.addZeroBeforeTimeIfNecessary;


/**
 * Created on 11/03/17.
 */
public class RideStopTimeSteps {

    Route route;
    LocalDate date;
    String rawTimeInput;
    List<RideStopPoint> stopPointsWithTimes;
    Vehicle vehicle;
    Driver driver;

    @Given("^I have defined a route with (\\d+) stop points$")
    public void iHaveDefinedARoute(int numberOfStopPoints) {
        StopPoint defaultPoint = new StopPoint("20", "Kirkwood Ave", "Riccarton");
        StopPoint secondPoint = new StopPoint("40", "Kirkwood Ave", "Riccarton");
        List<StopPoint> stopPoints = new ArrayList<>();
        stopPoints.add(defaultPoint);
        stopPoints.add(secondPoint);
        Assert.assertTrue(numberOfStopPoints == stopPoints.size());
        route = new Route("My Route", stopPoints);

        vehicle = new Vehicle("", "", "", "", 1, 1, 1, LocalDate.MAX, LocalDate.MAX);
        driver = new Driver("", "", "", "", "", null, "", "", null);
        driver.setLicence(new Licence("", "", LocalDate.MAX, LocalDate.MAX));
    }

    @And("^The first time in my list of (\\d+) times is less than (\\d+) hour ahead of my system time$")
    public void theFirstTimeIsLessThan1HourAheadOfSystemTime(int numberOfTimes, int numberOfHours) throws Throwable {
        date = LocalDate.now();
        LocalDateTime hourAhead = LocalDateTime.now().plusHours(1);
        LocalDateTime firstTime = LocalDateTime.now().plusMinutes(20);
        LocalDateTime secondTime = LocalDateTime.now().plusMinutes(30);
        Assert.assertTrue(firstTime.compareTo(hourAhead) < 0);

        String hour1 = addZeroBeforeTimeIfNecessary(Integer.toString(firstTime.getHour()));
        String minute1 = addZeroBeforeTimeIfNecessary(Integer.toString(firstTime.getMinute()));
        String hour2 = addZeroBeforeTimeIfNecessary(Integer.toString(secondTime.getHour()));
        String minute2 = addZeroBeforeTimeIfNecessary(Integer.toString(secondTime.getMinute()));
        rawTimeInput = hour1 + minute1 + "," + hour2 + minute2;
        Assert.assertTrue(rawTimeInput.split(",").length == numberOfTimes);
    }

    @And("^The second time in my list of (\\d+) times is less than (\\d+) minutes ahead of the first time$")
    public void theSecondTimeIsLessThan5MinsAheadOfTheFirst(int numberOfTimes, int numberOfMinutes) throws Throwable {
        date = LocalDate.now();
        LocalDateTime firstTime = LocalDateTime.now().plusMinutes(20);
        LocalDateTime secondTime = LocalDateTime.now().plusMinutes(24);
        Assert.assertTrue(secondTime.compareTo(firstTime.plusMinutes(5)) < 0);

        String hour1 = addZeroBeforeTimeIfNecessary(Integer.toString(firstTime.getHour()));
        String minute1 = addZeroBeforeTimeIfNecessary(Integer.toString(firstTime.getMinute()));
        String hour2 = addZeroBeforeTimeIfNecessary(Integer.toString(secondTime.getHour()));
        String minute2 = addZeroBeforeTimeIfNecessary(Integer.toString(secondTime.getMinute()));
        rawTimeInput = hour1 + minute1 + "," + hour2 + minute2;
        Assert.assertTrue(rawTimeInput.split(",").length == numberOfTimes);
    }

    @And("^I have defined a list of (\\d+) time$")
    public void iHaveDefinedAListOfTimes(int numberOfTimes) throws Throwable {
        date = LocalDate.now();
        LocalDateTime firstTime = LocalDateTime.now().plusMinutes(20);

        String hour1 = addZeroBeforeTimeIfNecessary(Integer.toString(firstTime.getHour()));
        String minute1 = addZeroBeforeTimeIfNecessary(Integer.toString(firstTime.getMinute()));
        rawTimeInput = hour1 + minute1;
        Assert.assertTrue(rawTimeInput.split(",").length == numberOfTimes);
    }

    @And("^The expiry date is after the driver's licence expiry$")
    public void expiryDateIsAfterLicenceExpiry() throws Throwable {
        driver.setLicence(new Licence("", "", LocalDate.MIN, LocalDate.now()));
        date = LocalDate.now().plusDays(10);
        rawTimeInput = "0900,0930";
    }

    @And("^The expiry date is after the vehicle's WOF expiry$")
    public void expiryDateIsAfterWofExpiry() throws Throwable {
        vehicle.setWofExpiry(LocalDate.now());
        date = LocalDate.now().plusDays(10);
        rawTimeInput = "0900,0930";
    }

    @And("^The expiry date is after the vehicle's registration expiry$")
    public void expiryDateIsAfterRegExpiry() throws Throwable {
        vehicle.setRegExpiry(LocalDate.now());
        date = LocalDate.now().plusDays(10);
        rawTimeInput = "0900,0930";
    }

    @When("^I try to map these times to the route$")
    public void iTryToMapTheTimes() throws Throwable {
        CreateRideController crc = new CreateRideController();
        stopPointsWithTimes = crc.mapTimesToStopPoints(route, vehicle, driver, date, rawTimeInput);
    }

    @Then("^The mapping will not succeed$")
    public void theMappingWillNotSucceed() throws Throwable {
        Assert.assertNull(stopPointsWithTimes);
    }
}
