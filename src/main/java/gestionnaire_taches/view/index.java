package gestionnaire_taches.view;

import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.geometry.Insets;

public class index {
    
    public VBox getIndexView() {
        VBox container = new VBox(10);
        container.setPadding(new Insets(20));
        
        Label titleLabel = new Label("Application de Gestion de Tâches");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        
        Label welcomeLabel = new Label("Bienvenue dans le système de gestion de tâches. Veuillez vous connecter pour continuer.");
        welcomeLabel.setWrapText(true);
        
        container.getChildren().addAll(titleLabel, welcomeLabel);
        
        return container;
    }
}