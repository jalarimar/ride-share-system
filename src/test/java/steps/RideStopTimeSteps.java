package steps;

import controllers.CreateRideController;
import controllers.RideDetailsController;
import controllers.SessionManager;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import javafx.event.ActionEvent;
import models.*;
import org.junit.Assert;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static controllers.Converter.addZeroBeforeTimeIfNecessary;
import static controllers.Converter.getReadableDate;
import static controllers.Converter.getTimeFromString;


/**
 * Created on 11/03/17.
 */
public class RideStopTimeSteps extends RideDetailsController {

    Route route;
    LocalDate startDate;
    String rawTimeInput;
    List<RideStopPoint> stopPointsWithTimes;

    @Given("^I have defined a route with (\\d+) stop points$")
    public void iHaveDefinedARoute(int numberOfStopPoints) {
        StopPoint defaultPoint = new StopPoint("20", "Kirkwood Ave", "Riccarton");
        StopPoint secondPoint = new StopPoint("40", "Kirkwood Ave", "Riccarton");
        List<StopPoint> stopPoints = new ArrayList<>();
        stopPoints.add(defaultPoint);
        stopPoints.add(secondPoint);
        Assert.assertTrue(numberOfStopPoints == stopPoints.size());
        route = new Route("My Route", stopPoints);
    }

    @And("^The first time in my list of (\\d+) times is less than (\\d+) hour ahead of my system time$")
    public void theFirstTimeIsLessThan1HourAheadOfSystemTime(int numberOfTimes, int numberOfHours) throws Throwable {
        startDate = LocalDate.now();
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
        startDate = LocalDate.now();
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
        startDate = LocalDate.now();
        LocalDateTime firstTime = LocalDateTime.now().plusMinutes(20);

        String hour1 = addZeroBeforeTimeIfNecessary(Integer.toString(firstTime.getHour()));
        String minute1 = addZeroBeforeTimeIfNecessary(Integer.toString(firstTime.getMinute()));
        rawTimeInput = hour1 + minute1;
        Assert.assertTrue(rawTimeInput.split(",").length == numberOfTimes);
    }

    @When("^I try to map these times to the route$")
    public void iTryToMapTheTimes() throws Throwable {
        CreateRideController crc = new CreateRideController();
        stopPointsWithTimes = crc.mapTimesToStopPoints(route, startDate, rawTimeInput);
    }

    @Then("^The mapping will not succeed$")
    public void theMappingWillNotSucceed() throws Throwable {
        Assert.assertNull(stopPointsWithTimes);
    }
}
