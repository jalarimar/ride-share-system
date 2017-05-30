package models;

import utilities.SessionManager;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static utilities.Converter.getReadableDate;
import static utilities.Converter.getTimeFromString;
import static utilities.Validator.validTimes;

/**
 * Created 21/03/2017.
 */
public class Route {

    private String name;
    private List<StopPoint> route;

    public Route(String name, List<StopPoint> route) {
        this.name = name;
        this.route = route;
    }

    @Override
    public String toString() {
        return name;
    }

    public String getName() { return name; }

    public List<StopPoint> getRoute() {
        return route;
    }

    public void setRoute(List<StopPoint> stopPoints) {
        for (StopPoint sp : stopPoints) {
            route.add(sp);
        }
    }
}
