package models;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created 21/03/2017.
 */
public class StopPoint {
    private int streetNumber;
    private String streetName;
    private String suburb;
    private double distanceFromUni;

    public StopPoint(int streetNumber, String streetName, String suburb) {
        if (streetNumber > 0) {
            this.streetNumber = streetNumber;
        }
        this.streetName = streetName;
        this.suburb = suburb;

        calculateDistanceFromUni();
    }

    @Override
    public String toString() {
        return Integer.toString(streetNumber) + " " + streetName + ", " + suburb;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof StopPoint)) {
            return false;
        }
        StopPoint stopPoint = (StopPoint)obj;
        if (!this.toString().equals(stopPoint.toString())) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + this.toString().hashCode();
        return result;
    }

    public String getStreetNumber() {
        return Integer.toString(streetNumber);
    }
    public Integer getStreetNumAsInt() {
        return streetNumber;
    }
    public String getStreetName() {
        return streetName;
    }
    public String getSuburb() {
        return suburb;
    }
    public double getDistanceFromUni() {
        return distanceFromUni;
    }


    private void calculateDistanceFromUni() {
        String uniAddress = "University Dr, Ilam";
        String thisAddress = this.toString();
        String apikey =  "AIzaSyDimWu3GwLCSyM22KEE-0jh9Z9oJoW-6Ek";
        String baseUrl = "https://maps.googleapis.com/maps/api/distancematrix/json?";

        HttpURLConnection connection = null;
        double value = 0;

        try {
            //Create connection
            String origin = "origins=" + URLEncoder.encode(uniAddress, "UTF-8");
            String destination = "destinations=" + URLEncoder.encode(thisAddress, "UTF-8");
            String parameters = origin + "&" + destination + "&key=" + apikey;
            URL url = new URL(baseUrl + parameters);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            //Get Response
            InputStream inputStream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("distance")) {
                    reader.readLine(); // text value
                    String valueLine = reader.readLine(); // numeric value
                    value = Integer.valueOf(valueLine.substring(28));
                }
            }
            reader.close();
        } catch (Exception e) {
            System.out.println("Could not connect.");
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        distanceFromUni = value / 1000; // m to km
    }
}
