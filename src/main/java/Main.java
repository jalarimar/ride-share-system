
import controllers.MainController;
import controllers.Serializer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;
import models.Driver;
import models.Rss;
import models.StopPoint;
import models.Vehicle;

import java.time.LocalDate;


/**
 * Created by samschofield on 7/03/17.
 */
public class Main extends Application {

    public void start(Stage stage) throws Exception {

        Serializer serializer = new Serializer();
        Rss rss;
        try {
            rss = serializer.load();
        } catch (Exception e) {
            rss = new Rss();
        }

        MainController main = MainController.getInstance();
        main.setStage(stage);
        main.setRss(rss);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, 500, 400);
        stage.setTitle("UC RSS");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();

        // For testing only
        String h = main.getDayOfDate(LocalDate.now());

    }
}
