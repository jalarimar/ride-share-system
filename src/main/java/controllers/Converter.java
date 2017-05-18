package controllers;

import models.RideStatus;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.FormatStyle;
import java.time.format.TextStyle;
import java.util.Locale;

/**
 * Created 6/05/2017.
 */
public final class Converter {

    private Converter(){
    }

    public static String getShortDayOfDate(LocalDate date) {
        DayOfWeek day = date.getDayOfWeek();
        String n = day.name();
        System.out.println(n);
        return day.getDisplayName(TextStyle.SHORT, Locale.ENGLISH);
    }

    public static String getLongDayOfDate(LocalDate date) {
        DayOfWeek day = date.getDayOfWeek();
        return day.getDisplayName(TextStyle.FULL, Locale.ENGLISH);
    }

    public static String getReadableDate(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public static String getReadableTime(LocalDateTime time) {
        String formatted = time.format(DateTimeFormatter.ofPattern("h:mma"));
        if (formatted.substring(formatted.length() - 2).equals("PM")) {
            return formatted.substring(0, formatted.length() - 2) + "pm";
        } else {
            return formatted.substring(0, formatted.length() - 2) + "am";
        }
    }

    public static LocalDateTime getTimeFromString(String input) {
        try {
            return LocalDateTime.parse(input, DateTimeFormatter.ofPattern("dd/MM/yyyyHHmm"));
        } catch (DateTimeParseException d){
            return null;
        }
    }

    public static String getRideStatusAsString(RideStatus status) {
        switch (status) {
            case UNSHARED:
                return "Unshared";
            case AVAILABLE:
                return "Available";
            case FULL:
                return "Full";
            case DONE:
                return "Done";
            case CANCELLED:
                return "Cancelled";
            default:
                return null;
        }
    }
}
