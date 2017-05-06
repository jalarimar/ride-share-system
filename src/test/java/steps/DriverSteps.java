package steps;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import models.Driver;
import models.Vehicle;
import org.junit.Assert;


/**
 * Created by sam on 11/03/17.
 */
public class DriverSteps {

    Driver driver;
    Vehicle current;

    @Given("^a driver named \"([^\"]*)\" \"([^\"]*)\"$")
    public void aDriverNamed(String firstName, String lastName) {
        driver = new Driver(firstName, lastName);
    }


    @When("^I register a (\\w+) car: (\\w+) (\\w+) (\\d+), with license (\\w+) and (\\d+) seats$")
    public void iRegisterACar(String colour, String type, String model, int year, String licensePlate, int physicalSeats) throws Throwable {
        Vehicle vehicle = new Vehicle(type, model, colour, licensePlate, "", year, physicalSeats);
        driver.addVehicle(vehicle);
    }

    @And("^I look at the car with license (\\w+)$")
    public void iLookAtTheCarWithLicense(String license) throws Throwable {
        for (Vehicle v: driver.getVehicles()) {
            if (v.getLicensePlate().equals(license)) {
                current = v;
                return;
            }
        }
        Assert.fail(String.format("no car with license '%s' found", license));
    }

    @Then("^it is the colour (\\w+)$")
    public void itIsTheColour(String colour) throws Throwable {
        Assert.assertEquals(colour, current.getColour());
    }

    @And("^it has make and model (.*)$")
    public void itHasMakeAndModelHondaAccord(String typeAndModel) throws Throwable {
        Assert.assertEquals(typeAndModel, current.getTypeAndModel());
    }
}
