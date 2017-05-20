package models;

/**
 * Created 20/05/2017.
 */
public enum BookingStatus {
    BOOKED,
    DONE,
    CANCELLED;

    @Override
    public String toString() {
        switch(super.ordinal()) {
            case 0:
                return "Booked";
            case 1:
                return "Done";
            case 2:
                return "Cancelled";
            default:
                return "Unknown";
        }
    }
}