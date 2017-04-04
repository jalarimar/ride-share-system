package models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
