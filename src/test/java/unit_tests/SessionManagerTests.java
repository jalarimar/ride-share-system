package unit_tests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import utilities.Navigator;
import utilities.SessionManager;

/**
 * Created 30/05/2017.
 */
public class SessionManagerTests {
    SessionManager session;

    @Before
    public void setUp() {
        session = SessionManager.getInstance();
    }

    @Test
    public void checkPreviousScene() {
        session.setPreviousScene(Navigator.bookedRides);
        Assert.assertEquals(Navigator.bookedRides, session.getPreviousScene());
    }
}
