package gestionnaire_taches.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

/**
 * Composant principal de layout pour l'application de gestion des tâches.
 * Fournit la structure générale avec en-tête et zone de contenu principal.
 */
public class AppLayout {
    private BorderPane mainContainer;
    private final String username;

    public AppLayout(String username) {
        this.username = username;
        initializeLayout();
    }

    private void initializeLayout() {
        mainContainer = new BorderPane();
        mainContainer.setStyle("-fx-background-color: #ffffff;");

        // Créer l'en-tête
        HBox header = createHeader();
        mainContainer.setTop(header);
    }

    private HBox createHeader() {
        HBox header = new HBox();
        header.setPadding(new Insets(15));
        header.setStyle("-fx-background-color: #2c3e50; -fx-border-color: #34495e;");
        header.setAlignment(Pos.CENTER_LEFT);

        Label titleLabel = new Label("Gestion des Tâches");
        titleLabel.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 18; -fx-font-weight: bold;");

        Label userLabel = new Label("Utilisateur: " + username);
        userLabel.setStyle("-fx-text-fill: #ecf0f1; -fx-font-size: 12;");

        HBox.setHgrow(userLabel, javafx.scene.layout.Priority.ALWAYS);
        userLabel.setAlignment(Pos.CENTER_RIGHT);

        header.getChildren().addAll(titleLabel, userLabel);

        return header;
    }

    public BorderPane getMainContainer() {
        return mainContainer;
    }

    public String getUsername() {
        return username;
    }
}