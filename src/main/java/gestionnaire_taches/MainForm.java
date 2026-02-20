package gestionnaire_taches;

import gestionnaire_taches.view.AppLayout;
import gestionnaire_taches.view.index;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainForm extends Application {

    @Override
    public void start(Stage stage) {
        // Créer le layout principal avec un utilisateur fictif
        AppLayout appLayout = new AppLayout("Utilisateur Demo");
        
        // Afficher la page d'accueil au démarrage
        index homePage = new index();
        appLayout.getMainContainer().setCenter(homePage.getView());

        // Créer la scène
        Scene scene = new Scene(appLayout.getMainContainer(), 1400, 800);

        stage.setTitle("Gestion des Tâches - Système de Management");
        stage.setScene(scene);
        stage.setWidth(1400);
        stage.setHeight(800);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
