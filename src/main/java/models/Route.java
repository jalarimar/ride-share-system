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

    public String getName() { return name; }

    public List<StopPoint> getRoute() {
        return route;
    }
}
