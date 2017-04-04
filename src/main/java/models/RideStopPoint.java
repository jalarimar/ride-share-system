package models;


/**
 * Created 04/04/2017.
 */
public class RideStopPoint {
    private Ride ride;
    private StopPoint stopPoint;
    private String time;
    private String day;

    public RideStopPoint(StopPoint sp, String time, String day) {
        this.ride = null;
        this.stopPoint = sp;
        this.time = time;
        this.day = day;
    }

    @Override
    public String toString() {
        return stopPoint.toString();
    }

    public void setRide(Ride ride) {
        this.ride = ride;
    }
    public Ride getRide() {
        return ride;
    }

    public StopPoint getStopPoint() {
        return stopPoint;
    }
    public void setStopPoint(StopPoint stopPoint) {
        this.stopPoint = stopPoint;
    }

    public String getTime() {return time; }
    public void setTime(String time) {
        this.time = time;
    }

    public String getDay() {return day; }
    public void setDay(String day) {
        this.day = day;
    }

    public String getDirection() {
        return ride.getDirection();
    }

    public Integer getAvailableSeats() {
        return ride.getAvailableSeats();
    }

}
