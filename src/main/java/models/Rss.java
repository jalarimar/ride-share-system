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
        users = new HashSet<User>();
        drivers = new HashSet<Driver>();
        vehicles = new ArrayList<Vehicle>();
        routes = new ArrayList<Route>();
        //loadDemoData();
    }

//    private void loadDemoData() {
//        addUsers();
//        addVehicles();
//        //addRoutes();
//    }

//    private void addUsers() {
//        users.add(new User("Tasha", "Miles", false));
//        users.add(new User("Reed", "Vin", false));
//        users.add(new User("Wendell", "Mane", false));
//        users.add(new User("Bill", "Murray", false));
//        users.add(new User("Daniella", "Parson", true));
//    }
//
//    private void addVehicles() {
//        vehicles.add(new Vehicle("ABC123"));
//        vehicles.add(new Vehicle("HJD765"));
//        vehicles.add(new Vehicle("LKH789"));
//        vehicles.add(new Vehicle("QEW293"));
//        vehicles.add(new Vehicle("LKJ983"));
//    }

//    private void addRoutes() {
//        Route r1 = new Route("R1");
//        r1.addStopPoint(new StopPoint("1 abc rd"));
//        r1.addStop(new Stop("10 def st"));
//        r1.addStop(new Stop("55 ghi ln"));
//        routes.add(r1);
//
//        Route r2 = new Route("R2");
//        r2.addStop(new Stop("1 jkl rd"));
//        r2.addStop(new Stop("10 mno st"));
//        r2.addStop(new Stop("55 pqr ln"));
//        routes.add(r2);
//    }
}
