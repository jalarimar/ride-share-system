package models;

import java.time.LocalDateTime;

/**
 * Created 21/03/2017.
 */
public class StopPoint {
    private int streetNumber;
    private String streetName;
    private String suburb;

    public StopPoint(int streetNumber, String streetName, String suburb) {
        this.streetNumber = streetNumber;
        this.streetName = streetName;
        this.suburb = suburb;
    }

    @Override
    public String toString() {
        return Integer.toString(streetNumber) + " " + streetName + ", " + suburb;
    }

    public String getStreetNumber() {
        return Integer.toString(streetNumber);
    }

    public Integer getStreetNumAsInt() {
        return streetNumber;
    }

    public String getStreetName() {
        return streetName;
    }
    public String getSuburb() {
        return suburb;
    }

    public String getAddress() {
        String address = Integer.toString(streetNumber) + " " + streetName + ", " + suburb;
        return address;
    }

}
