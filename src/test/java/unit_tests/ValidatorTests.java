package unit_tests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static utilities.Validator.*;

/**
 * Created 30/05/2017.
 */
public class ValidatorTests {

    @Test
    public void emptyTextNotAlphanumeric() {
        Assert.assertFalse(isAlphanumeric(""));
    }

    @Test
    public void charactersAreAlphanumeric() {
        Assert.assertTrue(isAlphanumeric("abc"));
    }

    @Test
    public void capsAreAlphanumeric() {
        Assert.assertTrue(isAlphanumeric("ABC"));
    }

    @Test
    public void numbersAreAlphanumeric() {
        Assert.assertTrue(isAlphanumeric("123"));
    }

    @Test
    public void mixedCharactersCapsAndNumbersAreAlphanumeric() {
        Assert.assertTrue(isAlphanumeric("a1B2c3"));
    }

    @Test
    public void noTimesNotValid() {
        String rawInput = "";
        Assert.assertFalse(validTimes(LocalDate.now(), rawInput));
    }

    @Test
    public void TooLongTimesNotValid() {
        String rawInput = "123456,78910";
        Assert.assertFalse(validTimes(LocalDate.now(), rawInput));
    }

    @Test
    public void TooShortTimesNotValid() {
        String rawInput = "1,7";
        Assert.assertFalse(validTimes(LocalDate.now(), rawInput));
    }

    @Test
    public void ValidTimeIsValid() {
        String rawInput = "0800,1230";
        Assert.assertTrue(validTimes(LocalDate.now(), rawInput));
    }

    @Test
    public void ValidTimeWithExtraSpacesIsValid() {
        String rawInput = " 0800, 1230  ";
        Assert.assertTrue(validTimes(LocalDate.now(), rawInput));
    }

    @Test
    public void ValidIntParses() {
        String rawInput = "0800";
        int expected = 800;
        Assert.assertEquals(expected, (long)tryParseInt(rawInput));
    }

    @Test
    public void ZeroDoesNotParse() {
        String rawInput = "0";
        int failure = -1;
        Assert.assertEquals(failure, (int)tryParseInt(rawInput));
    }

    @Test
    public void NegativeIntDoesNotParse() {
        String rawInput = "-3";
        int failure = -1;
        Assert.assertEquals(failure, (int)tryParseInt(rawInput));
    }

    @Test
    public void DecimalDoesNotParse() {
        String rawInput = "9.3";
        int failure = -1;
        Assert.assertEquals(failure, (int)tryParseInt(rawInput));
    }

    @Test
    public void NumberWithSpacesDoesNotParse() {
        String rawInput = "9 3";
        int failure = -1;
        Assert.assertEquals(failure, (int)tryParseInt(rawInput));
    }

    @Test
    public void YouShallNotParse() {
        String rawInput = "you";
        int failure = -1;
        Assert.assertEquals(failure, (int)tryParseInt(rawInput));
    }

    @Test
    public void ValidDoubleParses() {
        String rawInput = "0800";
        Double expected = 800.0;
        Assert.assertEquals(expected, (Double)tryParseDouble(rawInput));
    }

    @Test
    public void ZeroDoubleDoesNotParse() {
        String rawInput = "0";
        Double failure = -1.0;
        Assert.assertEquals(failure, (Double)tryParseDouble(rawInput));
    }

    @Test
    public void NegativeDoubleDoesNotParse() {
        String rawInput = "-3";
        Double failure = -1.0;
        Assert.assertEquals(failure, (Double)tryParseDouble(rawInput));
    }

    @Test
    public void DecimalDoubleParses() {
        String rawInput = "9.3";
        Double expected = 9.3;
        Assert.assertEquals(expected, (Double)tryParseDouble(rawInput));
    }

    @Test
    public void DoubleWithSpacesDoesNotParse() {
        String rawInput = "9 3";
        Double failure = -1.0;
        Assert.assertEquals(failure, (Double)tryParseDouble(rawInput));
    }

    @Test
    public void LettersDoNotParse() {
        String rawInput = "abc";
        Double failure = -1.0;
        Assert.assertEquals(failure, (Double)tryParseDouble(rawInput));
    }
}
