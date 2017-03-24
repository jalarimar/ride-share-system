package controllers;

import models.Driver;
import models.Passenger;
import models.Vehicle;

import java.io.*;

/**
 * Created 22/03/2017.
 */
public class Serializer {

    public void serialize(Object obj, String filePath) {
        try {
            FileOutputStream fileOut =
                    new FileOutputStream(filePath);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(obj);
            out.close();
            fileOut.close();
            System.out.printf("Serialized data is saved in " + filePath);
        }catch(IOException i) {
            i.printStackTrace();
        }
    }

    public <T> T deserialize(String filePath) {
        try {
            FileInputStream fileIn = new FileInputStream(filePath);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            T obj = (T) in.readObject();
            in.close();
            fileIn.close();
            return obj;
        } catch (FileNotFoundException f) {
            return null;
        } catch(IOException i) {
            i.printStackTrace();
            return null;
        }catch(ClassNotFoundException c) {
            System.out.println("Class not found");
            c.printStackTrace();
            return null;
        }
    }

}
