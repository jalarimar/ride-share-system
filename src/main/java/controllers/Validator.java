package controllers;


/**
 * Created 6/05/2017.
 */
public final class Validator {

    private Validator() {
    }

    public static Integer tryParseInt(String text) {
        if (text != null && !text.isEmpty()) {
            if (text.trim().matches("[0-9]+")) {
                return Integer.valueOf(text.trim());
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

}
