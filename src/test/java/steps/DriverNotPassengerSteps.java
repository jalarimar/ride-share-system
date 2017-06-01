package steps;

import controllers.RideDetailsController;
import utilities.SessionManager;
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


/**
 * Created on 11/03/17.
 */
public class DriverNotPassengerSteps extends RideDetailsController {

    Driver driver;
    Ride ride;
    StopPoint defaultPoint;

    @Given("^I am a driver named \"([^\"]*) ([^\"]*)\" with university id (\\w+)$")
    public void aDriverNamed(String firstName, String lastName, String uniId) {
        defaultPoint = new StopPoint("20", "Kirkwood Ave", "Riccarton");
        driver = new Driver(firstName, lastName, uniId, "123", "driver@uclive.ac.nz", defaultPoint, "1", "0", null);
    }

    @And("^I am offering a ride which has (\\d+) passengers$")
    public void iOfferARide(int numberOfPassengers) throws Throwable {
        List<RideStopPoint> rsps = new ArrayList<>();
        rsps.add(new RideStopPoint(defaultPoint, LocalDateTime.now(), LocalDate.now()));
        TripDetails tripDetails = new TripDetails("ABC", driver, true, false, new ArrayList<>(), LocalDate.MAX);
        ride = new Ride(tripDetails, rsps);
        Assert.assertEquals(numberOfPassengers, ride.getPassengers().size());
    }

    @When("^I try to book the ride$")
    public void iTryToBookTheRide() throws Throwable {
        SessionManager.getInstance().setCurrentUser(driver);
        SessionManager.getInstance().setFocusedRide(ride);

        ride.tryBookRide();
    }

    @Then("^I am not allowed to book the ride$")
    public void IAmNotAllowedToBookTheRide() throws Throwable {
        Assert.assertFalse(ride.isAllowedToBookRide(driver));
    }

    @And("^the ride still has (\\d+) passengers$")
    public void theRideStillHasZeroPassengers(int numberOfPassengers) throws Throwable {
        Assert.assertEquals(numberOfPassengers, ride.getPassengers().size());
    }
}
