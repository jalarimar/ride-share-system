package controllers;

import gherkin.deps.com.google.gson.Gson;
import gherkin.deps.com.google.gson.GsonBuilder;
import models.Rss;

import java.io.*;

/**
 * Created 22/03/2017.
 */
public class Serializer {

    static Gson gson = new GsonBuilder().create();

    public static Rss load() throws UnsupportedEncodingException {
        Reader reader = new InputStreamReader(Serializer.class.getResourceAsStream("/rss.json"), "UTF-8");
        return gson.fromJson(reader, Rss.class);
    }

    public static void save(Rss system) throws IOException {
        Writer writer = new OutputStreamWriter(new FileOutputStream("src/main/resources/rss.json"), "UTF-8");
        gson.toJson(system, writer);
        writer.close();
    }
}
