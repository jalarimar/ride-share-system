package models;

import java.time.LocalDateTime;

/**
 * Created 21/03/2017.
 */
public class StopPoint {
    private int streetNumber;
    private String streetName;
    private String suburb;
    private LocalDateTime dateTime;

    public StopPoint(int streetNumber, String streetName, String suburb) {
        this.streetNumber = streetNumber;
        this.streetName = streetName;
        this.suburb = suburb;
    }

    public void setTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getAddress() {
        String address = Integer.toString(streetNumber) + " " + streetName + ", " + suburb;
        return address;
    }

    public String toString() {
        return Integer.toString(streetNumber) + " " + streetName + ", " + suburb;
    }

}
