package utilities;


import java.time.LocalDate;
import java.time.LocalDateTime;

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