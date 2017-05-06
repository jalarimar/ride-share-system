package models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

/**
 * Created 26/04/2017.
 */
public class Rss {

    private Collection<User> users;
    private Collection<Driver> drivers;
    private Collection<Vehicle> vehicles;
    private Collection<Route> routes;

    public Rss() {
        users = new HashSet<>();
        drivers = new HashSet<>();
        vehicles = new ArrayList<>();
        routes = new ArrayList<>();
    }

}
