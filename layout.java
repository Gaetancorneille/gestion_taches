package gestionnaire_taches;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class layout extends Application {

    @Override
    public void start(Stage primaryStage) {

        Label label = new Label("Hello Gestion Tâches");

        Scene scene = new Scene(label, 600, 400);

        primaryStage.setTitle("Gestion Tâches");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
