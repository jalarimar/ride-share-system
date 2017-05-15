package steps;

import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import models.Driver;
import models.StopPoint;
import models.Vehicle;
import org.junit.Assert;

import java.util.List;


/**
 * Created by sam on 11/03/17.
 */
public class StopPointSteps {

    Driver driver;
    List<StopPoint> driverStopPoints;

    @Given("^a driver called \"([^\"]*)\" \"([^\"]*)\"$")
    public void aDriverCalled(String firstName, String lastName) {
        driver = new Driver(firstName, lastName);
    }

    @When("^I create a stop point at (\\d+) (\\w+\\s\\w+), (\\w+)$")
    public void iCreateAStopPoint(int num, String name, String suburb) throws Throwable {
        StopPoint stopPoint = new StopPoint(num, name, suburb);
        driver.addStopPoint(stopPoint);
    }

    @And("^I look at the driver's list of stop points$")
    public void iLookAtTheDriversListOfStopPoints() throws Throwable {
        driverStopPoints = driver.getStopPoints();
    }

    @Then("^the list of stop points contains (\\d+) (\\w+\\s\\w+), (\\w+)$")
    public void theListOfStopPointsContainsClonbernPlaceIlam(int num, String name, String suburb) throws Throwable {
        boolean containsAddress = false;
        for (StopPoint sp : driverStopPoints) {
            if (sp.toString().equals(Integer.toString(num) + " " + name + ", " + suburb)) {
                containsAddress = true;
            }
        }
        Assert.assertTrue(containsAddress);
    }
}
