
import controllers.SessionManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.Rss;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static controllers.Converter.getReadableDate;
import static controllers.Converter.getShortDayOfDate;
import static controllers.Converter.getTimeFromString;
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
        String h = getShortDayOfDate(LocalDate.now());
    }

    @Override
    public void stop() {
        saveRss();
    }
}
