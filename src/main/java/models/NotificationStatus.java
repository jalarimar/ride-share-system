package models;

/**
 * Created 08/05/2017.
 */
public enum NotificationStatus {
    ONE_WEEK,
    TWO_WEEKS,
    ONE_MONTH,
    NONE;

    @Override
    public String toString() {
        switch(this) {
            case ONE_WEEK:
                return "1 week";
            case TWO_WEEKS:
                return "2 weeks";
            case ONE_MONTH:
                return "1 month";
            default:
                return "forever";
        }
    }
}
