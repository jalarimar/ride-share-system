package unit_tests;

import enums.NotificationStatus;
import models.Driver;
import models.Vehicle;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Created 30/05/2017.
 */
public class DriverTests {

    @Test
    public void TimeUntilExpiryNoneWhenOver1Month() {
        Driver driver = new Driver("", "", "", "", "", null, "1", "1", null);
        LocalDate expiryDate = LocalDate.now().plusMonths(9);
        Assert.assertEquals(NotificationStatus.NONE, driver.getTimeUntilExpiry(expiryDate));
    }

    @Test
    public void TimeUntilExpiry1MonthWhenBetween1MonthAnd2Weeks() {
        Driver driver = new Driver("", "", "", "", "", null, "1", "1", null);
        LocalDate expiryDate = LocalDate.now().plusDays(21);
        Assert.assertEquals(NotificationStatus.ONE_MONTH, driver.getTimeUntilExpiry(expiryDate));
    }

    @Test
    public void TimeUntilExpiry2WeeksWhenBetween2WeeksAnd1Week() {
        Driver driver = new Driver("", "", "", "", "", null, "1", "1", null);
        LocalDate expiryDate = LocalDate.now().plusDays(7);
        Assert.assertEquals(NotificationStatus.TWO_WEEKS, driver.getTimeUntilExpiry(expiryDate));
    }

    @Test
    public void TimeUntilExpiry1WeekWhenLessThan1Week() {
        Driver driver = new Driver("", "", "", "", "", null, "1", "1", null);
        LocalDate expiryDate = LocalDate.now().plusDays(1);
        Assert.assertEquals(NotificationStatus.ONE_WEEK, driver.getTimeUntilExpiry(expiryDate));
    }

    @Test
    public void TimeUntilExpiry1WeekWhenLessThanZero() {
        Driver driver = new Driver("", "", "", "", "", null, "1", "1", null);
        LocalDate expiryDate = LocalDate.now().minusDays(5);
        Assert.assertEquals(NotificationStatus.ONE_WEEK, driver.getTimeUntilExpiry(expiryDate));
    }
}
