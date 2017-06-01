package unit_tests;

import models.RideStopPoint;
import models.Route;
import models.StopPoint;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created 30/05/2017.
 */
public class RouteTests {

    @Test
    public void replaceRoute() {
        List<StopPoint> oldStopPoints = new ArrayList<>();
        oldStopPoints.add(new StopPoint("18A", "Aileen Place", "Riccarton"));
        List<StopPoint> newStopPoints = new ArrayList<>();
        newStopPoints.add(new StopPoint(104, "Victoria Street", "Christchurch"));
        newStopPoints.add(new StopPoint(29, "Arapiki Rd", "Nelson"));
        Route route = new Route("", oldStopPoints);
        route.setRoute(newStopPoints);
        Assert.assertEquals(2, route.getRoute().size());
    }

    @Test
    public void testToString() {
        Route route = new Route("Jeremy", new ArrayList<>());
        Assert.assertEquals("Jeremy", route.toString());
    }
}
