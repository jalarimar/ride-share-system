package enums;

/**
 * Created 24/03/2017.
 */
public enum RideStatus {
    UNSHARED,
    AVAILABLE,
    FULL,
    DONE,
    CANCELLED;

    @Override
    public String toString() {
        switch(super.ordinal()) {
            case 0:
                return "Unshared";
            case 1:
                return "Available";
            case 2:
                return "Full";
            case 3:
                return "Done";
            case 4:
                return "Cancelled";
            default:
                return "Unknown";
        }
    }
}
