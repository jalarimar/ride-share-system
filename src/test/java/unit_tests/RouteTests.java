package unit_tests;

import models.RideStopPoint;
import models.Route;
import models.Rss;
import models.StopPoint;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static utilities.Validator.startsOrEndsWithUni;

/**
 * Created 30/05/2017.
 */
public class RouteTests {

    @Test
    public void SetRouteReplacesWholeList() {

        List<StopPoint> oldStopPoints = new ArrayList<>();
        StopPoint aileen = new StopPoint("18A", "Aileen Place", "Riccarton");
        oldStopPoints.add(aileen);
        Route route = new Route("", oldStopPoints);

        List<StopPoint> newStopPoints = new ArrayList<>();
        newStopPoints.add(new StopPoint(104, "Victoria Street", "Christchurch"));
        newStopPoints.add(new StopPoint(29, "Arapiki Rd", "Nelson"));
        route.setRoute(newStopPoints);

        Assert.assertEquals(2, route.getRoute().size());
        Assert.assertFalse(route.getRoute().contains(aileen));
    }

    @Test
    public void testToString() {
        Route route = new Route("Jeremy", new ArrayList<>());
        Assert.assertEquals("Jeremy", route.toString());
    }

    @Test
    public void RouteOkIfEndsWithUni() {
        StopPoint blank = new StopPoint("", "", "");
        StopPoint uni = Rss.getInstance().getUniversityStopPoint();
        List<StopPoint> stopPoints = new ArrayList<>();
        stopPoints.add(blank);
        stopPoints.add(uni);
        Assert.assertTrue(startsOrEndsWithUni(stopPoints));
    }

    @Test
    public void RouteOkIfStartsWithUni() {
        StopPoint blank = new StopPoint("", "", "");
        StopPoint uni = Rss.getInstance().getUniversityStopPoint();
        List<StopPoint> stopPoints = new ArrayList<>();
        stopPoints.add(uni);
        stopPoints.add(blank);
        Assert.assertTrue(startsOrEndsWithUni(stopPoints));
    }

    @Test
    public void RouteOkIfUniOnlyStop() {
        StopPoint uni = Rss.getInstance().getUniversityStopPoint();
        List<StopPoint> stopPoints = new ArrayList<>();
        stopPoints.add(uni);
        Assert.assertTrue(startsOrEndsWithUni(stopPoints));
    }

    @Test
    public void RouteNotOkIfEmpty() {
        List<StopPoint> stopPoints = new ArrayList<>();
        Assert.assertFalse(startsOrEndsWithUni(stopPoints));
    }

    @Test
    public void RouteNotOkIfUniInMiddle() {
        StopPoint blank = new StopPoint("", "", "");
        List<StopPoint> stopPoints = new ArrayList<>();
        stopPoints.add(blank);
        stopPoints.add(Rss.getInstance().getUniversityStopPoint());
        stopPoints.add(blank);
        Assert.assertFalse(startsOrEndsWithUni(stopPoints));
    }

    @Test
    public void RouteNotOkIfUniNotIncluded() {
        StopPoint blank = new StopPoint("", "", "");
        List<StopPoint> stopPoints = new ArrayList<>();
        stopPoints.add(blank);
        stopPoints.add(blank);
        stopPoints.add(blank);
        Assert.assertFalse(startsOrEndsWithUni(stopPoints));
    }
}
