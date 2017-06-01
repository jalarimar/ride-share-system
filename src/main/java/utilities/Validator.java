package utilities;


import models.Rss;
import models.StopPoint;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static utilities.Converter.getReadableDate;
import static utilities.Converter.getTimeFromString;

/**
 * Created 6/05/2017.
 */
public final class Validator {

    private Validator() {
    }

    public static Integer tryParseInt(String text) {
        if (text != null && !text.isEmpty()) {
            if (text.trim().matches("[0-9]+")) {
                Integer number = Integer.valueOf(text.trim());
                // number positive non-zero
                if (number > 0) {
                    return number;
                }
            }
        }
        return -1;
    }

    public static double tryParseDouble(String text) {
        if (text != null && !text.isEmpty()) {
            try {
                Double number = Double.parseDouble(text.trim());
                // number positive non-zero
                if (number > 0.0) {
                    return number;
                } else {
                    return -1;
                }
            } catch (Exception e) {
                return -1;
            }
        }
        return -1;
    }

    public static boolean isAlphanumeric(String text) {
        if (text != null && !text.isEmpty()) {
            if (text.matches("[a-zA-Z0-9 ]+")) {
                return true;
            }
        }
        return false;
    }

    public static boolean isAlphabetic(String text) {
        if (text != null && !text.isEmpty()) {
            if (text.matches("[a-zA-Z ]+")) {
                return true;
            }
        }
        return false;
    }

    public static boolean isValidPhoto(File photo) {
        String fileFormatRegex = "^.*\\.(jpg|JPG|jpeg|JPEG|png|PNG)$";
        return photo != null && photo.getName().matches(fileFormatRegex);
    }

    public static boolean isValidEmailAddress(String email) {
        String studentEmailRegex = "^[a-zA-Z0-9.]+@uclive.ac.nz$";
        String staffEmailRegex = "^[a-zA-Z0-9.]+@canterbury.ac.nz$";
        return email != null && (email.matches(studentEmailRegex) || email.matches(staffEmailRegex));

        // TODO mockito
    }

    public static boolean startsOrEndsWithUni(List<StopPoint> route) {
        StopPoint uni = Rss.getInstance().getUniversityStopPoint();
        if (route.size() < 1) {
            return false;
        }
        return (route.get(0).equals(uni) || route.get(route.size()-1).equals(uni));
    }

    public static boolean validTimes(LocalDate date, String rawInput) {
        for (String time : rawInput.split(",")) {
            LocalDateTime thyme = getTimeFromString(getReadableDate(date) + time.trim());
            if (thyme == null) {
                return false;
            }
        }
        return true;
    }
}
