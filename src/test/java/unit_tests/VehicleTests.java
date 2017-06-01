package unit_tests;

import enums.NotificationStatus;
import models.Route;
import models.Rss;
import models.StopPoint;
import models.Vehicle;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static utilities.Validator.startsOrEndsWithUni;

/**
 * Created 30/05/2017.
 */
public class VehicleTests {

    @Test
    public void TestEqualsIfSameLicencePlate() {
        Vehicle v1 = new Vehicle("Honda", "Jazz", "Pink", "CARS4LYF", 1, 1, 1, LocalDate.MAX, LocalDate.MAX);
        Vehicle v2 = new Vehicle("Lamborghini", "Murciélago", "Yellow", "CARS4LYF", 1, 1, 1, LocalDate.MAX, LocalDate.MAX);
        Assert.assertEquals(v1, v2);
    }

    @Test
    public void HashCodesSameForSameLicencePlates() {
        Vehicle v1 = new Vehicle("Honda", "Jazz", "Pink", "CARS4LYF", 1, 1, 1, LocalDate.MAX, LocalDate.MAX);
        Vehicle v2 = new Vehicle("Lamborghini", "Murciélago", "Yellow", "CARS4LYF", 1, 1, 1, LocalDate.MAX, LocalDate.MAX);
        Assert.assertTrue(v1.hashCode() == v2.hashCode());
    }

    @Test
    public void HashCodesDifferentForDifferentLicencePlates() {
        Vehicle v1 = new Vehicle("Honda", "Jazz", "Pink", "CARS", 1, 1, 1, LocalDate.MAX, LocalDate.MAX);
        Vehicle v2 = new Vehicle("Lamborghini", "Murciélago", "Yellow", "4LYF", 1, 1, 1, LocalDate.MAX, LocalDate.MAX);
        Assert.assertFalse(v1.hashCode() == v2.hashCode());
    }

    @Test
    public void WofNotificationResetWhenWofUpdated() {
        Vehicle vehicle = new Vehicle("Lamborghini", "Murciélago", "Yellow", "CARS4LYF", 1, 1, 1, LocalDate.MAX, LocalDate.MAX);
        vehicle.setWofExpiry(LocalDate.now().plusDays(10));
        NotificationStatus twoWeeks = NotificationStatus.TWO_WEEKS;
        vehicle.setLastSeenWofNotification(twoWeeks);

        vehicle.setWofExpiry(LocalDate.now().plusDays(200));
        Assert.assertFalse(twoWeeks.equals(vehicle.getLastSeenWofNotification()));
    }

    @Test
    public void WofNotificationNotResetWhenRegUpdated() {
        Vehicle vehicle = new Vehicle("Lamborghini", "Murciélago", "Yellow", "CARS4LYF", 1, 1, 1, LocalDate.MAX, LocalDate.MAX);
        vehicle.setWofExpiry(LocalDate.now().plusDays(10));
        NotificationStatus twoWeeks = NotificationStatus.TWO_WEEKS;
        vehicle.setLastSeenWofNotification(twoWeeks);

        vehicle.setRegExpiry(LocalDate.now().plusDays(200));
        Assert.assertTrue(twoWeeks.equals(vehicle.getLastSeenWofNotification()));
    }
}
