
import controllers.SessionManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;
import models.Driver;
import models.Licence;
import models.Rss;
import models.User;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDate;

import static controllers.Converter.getDayOfDate;
import static controllers.Serializer.loadRss;
import static controllers.Serializer.saveRss;


/**
 * Created on 7/03/17.
 */
public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        Rss rss = Rss.getInstance();
        SessionManager session = SessionManager.getInstance();
        session.setStage(stage);

        System.out.println(rss.getAllUsers().toString());

        FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, 500, 400);
        stage.setTitle("UC RSS");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();

        // For testing only
        String h = getDayOfDate(LocalDate.now());
    }

    @Override
    public void stop() {
        saveRss();
    }
}
