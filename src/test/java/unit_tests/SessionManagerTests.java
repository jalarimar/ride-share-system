package unit_tests;

import models.Driver;
import models.StopPoint;
import models.User;
import models.Vehicle;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import utilities.Navigator;
import utilities.SessionManager;

import java.time.LocalDate;

import static org.mockito.Mockito.mock;

/**
 * Created 30/05/2017.
 */
public class SessionManagerTests {
    SessionManager session;
    StopPoint mockStop;

    @Before
    public void setUp() {
        session = SessionManager.getInstance();
        mockStop = mock(StopPoint.class);
    }

    @Test
    public void checkPreviousScene() {
        session.setPreviousScene(Navigator.bookedRides);
        Assert.assertEquals(Navigator.bookedRides, session.getPreviousScene());
    }

    @Test
    public void checkSetUserRetainsDriverType() {
        User driver = new Driver("J", "H", "jha53", "torchwood", "jha53@uclive.ac.nz", mockStop, "0", "0", null);
        session.setCurrentUser(driver);
        Assert.assertEquals(driver, session.getCurrentDriver());
    }

    @Test
    public void checkGetDriverReturnsNullWhenUserNotDriver() {
        User passenger = new User("J", "H", false,"jha53", "torchwood", "jha53@uclive.ac.nz", mockStop, "0", "0", null);
        session.setCurrentUser(passenger);
        Assert.assertNull(session.getCurrentDriver());
    }

    @Test
    public void checkGetUserReturnsUserWhenUserNotDriver() {
        User passenger = new User("J", "H", false,"jha53", "torchwood", "jha53@uclive.ac.nz", mockStop, "0", "0", null);
        session.setCurrentUser(passenger);
        Assert.assertEquals(passenger, session.getCurrentUser());
    }
}
