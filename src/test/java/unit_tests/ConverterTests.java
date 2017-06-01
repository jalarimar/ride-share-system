package unit_tests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

import static utilities.Converter.*;

/**
 * Created 30/05/2017.
 */
public class ConverterTests {
    LocalDateTime now;

    @Before
    public void setUp() {
        now = LocalDateTime.now();
    }

    @Test
    public void MakeMorningTimeReadable() {
        LocalDateTime time = LocalDateTime.of(2017, 2, 16, 3, 2);
        String expected = "3:02am";
        Assert.assertEquals(expected, getReadableTime(time));
    }

    @Test
    public void MakeAfternoonTimeReadable() {
        LocalDateTime time = LocalDateTime.of(2017, 2, 16, 16, 50);
        String expected = "4:50pm";
        Assert.assertEquals(expected, getReadableTime(time));
    }

    @Test
    public void MakeMiddayTimeReadable() {
        LocalDateTime time = LocalDateTime.of(2017, 2, 16, 12, 0);
        String expected = "12:00pm";
        Assert.assertEquals(expected, getReadableTime(time));
    }

    @Test
    public void MakeDateReadable() {
        LocalDate date = LocalDate.of(2017, 2, 16);
        String expected = "16/02/2017";
        Assert.assertEquals(expected, getReadableDate(date));
    }

    @Test
    public void AddNecessaryZeroBeforeTime() {
        String rawInput = "8";
        String expected = "08";
        Assert.assertEquals(expected, addZeroBeforeTimeIfNecessary(rawInput));
    }

    @Test
    public void DoNotAddUnnecessaryZeroBeforeTime() {
        String rawInput = "12";
        String expected = "12";
        Assert.assertEquals(expected, addZeroBeforeTimeIfNecessary(rawInput));
    }

    @Test
    public void DoNotBreakIfNoInput() {
        String rawInput = "";
        String expected = "";
        Assert.assertEquals(expected, addZeroBeforeTimeIfNecessary(rawInput));
    }

    @Test
    public void ReturnNullOnInvalidString() {
        Assert.assertNull(getTimeFromString("notValid"));
    }

    @Test
    public void GetTheCorrectTimeFromString() {
        String date = getReadableDate(now.toLocalDate());
        String hour = addZeroBeforeTimeIfNecessary(Integer.toString(now.getHour()));
        String minute = addZeroBeforeTimeIfNecessary(Integer.toString(now.getMinute()));
        String input = date + hour + minute;
        Assert.assertEquals(now.toLocalDate(), getTimeFromString(input).toLocalDate());
        Assert.assertEquals(now.getHour(), getTimeFromString(input).getHour());
        Assert.assertEquals(now.getMinute(), getTimeFromString(input).getMinute());
    }

    @Test
    public void GetShortFormDay() {
        LocalDate date = LocalDate.of(2017, 5, 30);
        String expected = "Tue";
        Assert.assertEquals(expected, getShortDayOfDate(date));
    }

    @Test
    public void GetLongFormDay() {
        LocalDate date = LocalDate.of(2017, 5, 30);
        String expected = "Tuesday";
        Assert.assertEquals(expected, getLongDayOfDate(date));
    }

    @Test
    public void HourLostWhenDstEnds() {
        LocalDateTime dstEnd = LocalDateTime.of(2017,4,2,1,0);
        LocalDateTime threeHoursLater = dstEnd.plusHours(3);
        ZonedDateTime end = toZonedTime(dstEnd);
        ZonedDateTime endPlusThree = end.plusHours(3);
        Assert.assertTrue(threeHoursLater.compareTo(endPlusThree.toLocalDateTime()) > 0);
    }

    @Test
    public void HourGainedWhenDstBegins() {
        LocalDateTime dstEnd = LocalDateTime.of(2016,9,25,1,0);
        LocalDateTime threeHoursLater = dstEnd.plusHours(3);
        ZonedDateTime end = toZonedTime(dstEnd);
        ZonedDateTime endPlusThree = end.plusHours(3);
        Assert.assertTrue(threeHoursLater.compareTo(endPlusThree.toLocalDateTime()) < 0);
    }

    @Test
    public void NoChangeWhenDstSame() {
        LocalDateTime dstEnd = LocalDateTime.of(2016,2,16,4,0);
        LocalDateTime threeHoursLater = dstEnd.plusHours(3);
        ZonedDateTime end = toZonedTime(dstEnd);
        ZonedDateTime endPlusThree = end.plusHours(3);
        Assert.assertTrue(threeHoursLater.compareTo(endPlusThree.toLocalDateTime()) == 0);
    }
}
