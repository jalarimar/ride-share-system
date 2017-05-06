package controllers;

import gherkin.deps.com.google.gson.Gson;
import gherkin.deps.com.google.gson.GsonBuilder;
import models.Rss;

import java.io.*;

/**
 * Created 22/03/2017.
 */
public final class Serializer {

    private Serializer() {
    }

    static Gson gson = new GsonBuilder().create();

    public static Rss loadRss() {
        try {
            Reader reader = new InputStreamReader(Serializer.class.getResourceAsStream("/rss.json"), "UTF-8");
            return gson.fromJson(reader, Rss.class);

        } catch (UnsupportedEncodingException e) {
            System.out.println(e);
            return null;
        }
    }

    public static void saveRss(Rss system) {
        try {
            Writer writer = new OutputStreamWriter(new FileOutputStream("src/main/resources/rss.json"), "UTF-8");
            gson.toJson(system, writer);
            writer.close();

        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
