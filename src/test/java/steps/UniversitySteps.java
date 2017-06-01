package steps;

import controllers.CreateRouteController;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import models.*;
import org.junit.Assert;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static utilities.Validator.startsOrEndsWithUni;

/**
 * Created on 11/05/17.
 */
public class UniversitySteps {

    Driver driver;
    List<StopPoint> stopPoints;

    @Given("^I am a driver$")
    public void aDriver() {
        driver = new Driver("", "", "ABC", "123", "driver@uclive.ac.nz", Rss.getInstance().getUniversityStopPoint(), "1", "0", null);
    }

    @When("^I attempt to create a route which does not have UC as the departure point or destination$")
    public void iCreateInvalidRoute() throws Throwable {
        stopPoints = new ArrayList<>();
        stopPoints.add(new StopPoint(104, "Victoria Street", "Christchurch"));
        stopPoints.add(new StopPoint(29, "Arapiki Rd", "Nelson"));
    }

    @When("^I attempt to create a route which has UC as the departure point$")
    public void iCreateRouteWithUniStartPoint() throws Throwable {
        stopPoints = new ArrayList<>();
        stopPoints.add(Rss.getInstance().getUniversityStopPoint());
        stopPoints.add(new StopPoint(104, "Victoria Street", "Christchurch"));
    }

    @Then("^it will not pass validation$")
    public void notPassValidation() throws Throwable {
        Assert.assertFalse(startsOrEndsWithUni(stopPoints));
    }

    @Then("^it will pass validation$")
    public void passValidation() throws Throwable {
        Assert.assertTrue(startsOrEndsWithUni(stopPoints));
    }

    // TODO time cucumbers
}
