
import controllers.SessionManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;
import models.Rss;

import java.time.LocalDate;

import static controllers.Converter.getDayOfDate;
import static controllers.Serializer.loadRss;


/**
 * Created on 7/03/17.
 */
public class Main extends Application {

    public void start(Stage stage) throws Exception {

        Rss rss = loadRss();
        if (rss == null) {
            rss = new Rss();
        }

        SessionManager session = SessionManager.getInstance();
        session.setStage(stage);
        session.setRss(rss);

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
}
