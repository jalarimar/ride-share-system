package controllers;

import java.time.DayOfWeek;
import java.time.LocalDate;

/**
 * Created 6/05/2017.
 */
public final class Converter {

    private Converter(){
    }

    public static String getDayOfDate(LocalDate date) {
        DayOfWeek day = date.getDayOfWeek();
        String n = day.name();
        System.out.println(n);
        return "today";
    }
}
