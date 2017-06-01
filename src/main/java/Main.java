
import utilities.SessionManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.Rss;
import java.time.LocalDate;

import static utilities.Converter.getShortDayOfDate;
import static utilities.Serializer.saveRss;


/**
 * Created on 7/03/17.
 */
public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        Rss rss = Rss.getInstance();
        SessionManager session = SessionManager.getInstance();
        session.setStage(stage);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, 500, 400);
        stage.setTitle("UC RSS");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();

        // TODO clear rss.json before submission
    }

    @Override
    public void stop() {
        saveRss();
    }
}
