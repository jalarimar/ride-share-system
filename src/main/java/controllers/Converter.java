package controllers;

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

    public static String getTimeOfDate(LocalDateTime time) {
        return time.format(DateTimeFormatter.ofPattern("hh:mma"));
    }

    public static LocalDateTime getTimeFromString(String input) {
        try {
            return LocalDateTime.parse(input, DateTimeFormatter.ofPattern("dd/MM/yyyyHHmm"));
        } catch (DateTimeParseException d){
            return null;
        }

    }
}
