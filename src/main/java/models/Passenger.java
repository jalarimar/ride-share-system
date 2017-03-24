package models;


/**
 * Created 21/03/2017.
 */
public class Passenger extends User implements AutoCloseable {

    public Passenger(String firstName, String lastName) {
        super(firstName, lastName, false);
    }

    public void close() {
        this.close();
    }
}
