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

    public StopPoint(int streetNumber, String streetName, String suburb, LocalDateTime dateTime) {
        this.streetNumber = streetNumber;
        this.streetName = streetName;
        this.suburb = suburb;
        this.dateTime = dateTime;
    }

    public String getAddress() {
        return "address";
    }

}
