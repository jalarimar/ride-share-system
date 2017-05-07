package models;


/**
 * Created 21/03/2017.
 */
public class StopPoint {
    private int streetNumber;
    private String streetName;
    private String suburb;

    public StopPoint(int streetNumber, String streetName, String suburb) {
        if (streetNumber > 0) {
            this.streetNumber = streetNumber;
        }
        this.streetName = streetName;
        this.suburb = suburb;
    }

    @Override
    public String toString() {
        return Integer.toString(streetNumber) + " " + streetName + ", " + suburb;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof StopPoint)) {
            return false;
        }
        StopPoint stopPoint = (StopPoint)obj;
        if (!this.toString().equals(stopPoint.toString())) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + this.toString().hashCode();
        return result;
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
