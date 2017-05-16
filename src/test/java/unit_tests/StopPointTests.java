package unit_tests;


import models.*;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;

public class StopPointTests {

    @Test
    public void testToStringFormat() {
        StopPoint stopPoint = new StopPoint(2, "Mulberry Grove", "Warwickville");
        Assert.assertTrue(stopPoint.toString().equals(stopPoint.getStreetNumber() + " " + stopPoint.getStreetName() + ", " + stopPoint.getSuburb()));
    }

    @Test
    public void testStreetNumberNonNegative() {
        StopPoint stopPoint = new StopPoint(-2, "Mulberry Grove", "Warwickville");
        Assert.assertTrue(stopPoint.getStreetNumber().equals("0"));
        // TODO
    }
}