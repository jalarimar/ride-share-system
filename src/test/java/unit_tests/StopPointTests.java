package unit_tests;


import models.*;
import org.junit.Assert;
import org.junit.Test;


public class StopPointTests {

    @Test
    public void testToStringFormat() {
        StopPoint stopPoint = new StopPoint(2, "Mulberry Grove", "Warwickville");
        String expected = (stopPoint.getStreetNumber() + " " + stopPoint.getStreetName() + ", " + stopPoint.getSuburb());
        Assert.assertEquals(expected, stopPoint.toString());
    }

    @Test
    public void ifStreetNumberNegativeUseAbsolute() {
        StopPoint stopPoint = new StopPoint(-2, "Mulberry Grove", "Warwickville");
        Assert.assertTrue(stopPoint.getStreetNumber().equals("2"));
    }
}