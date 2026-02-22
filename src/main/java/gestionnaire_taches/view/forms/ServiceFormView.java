package gestionnaire_taches.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class ServiceFormView {

    public VBox getView() {
        VBox formContainer = new VBox(15);
        formContainer.setPadding(new Insets(20));

        // Title
        Label titleLabel = new Label("Formulaire de Service");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        // Form grid
        GridPane formGrid = new GridPane();
        formGrid.setHgap(10);
        formGrid.setVgap(10);

        // Fields
        Label nameLabel = new Label("Nom:");
        TextField nameField = new TextField();
        nameField.setPrefWidth(300);

        Label descriptionLabel = new Label("Description:");
        TextArea descriptionArea = new TextArea();
        descriptionArea.setPrefRowCount(4);
        descriptionArea.setPrefWidth(300);

        Label adminIdLabel = new Label("ID Administrateur:");
        TextField adminIdField = new TextField();
        adminIdField.setPrefWidth(100);

        // Add fields to grid
        int row = 0;
        formGrid.add(nameLabel, 0, row);
        formGrid.add(nameField, 1, row++);
        
        formGrid.add(descriptionLabel, 0, row);
        formGrid.add(descriptionArea, 1, row++);
        
        formGrid.add(adminIdLabel, 0, row);
        formGrid.add(adminIdField, 1, row++);

        // Buttons
        HBox buttonBox = new HBox(10);
        Button saveButton = new Button("Sauvegarder");
        Button cancelButton = new Button("Annuler");
        Button resetButton = new Button("Réinitialiser");
        
        saveButton.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white;");
        cancelButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white;");
        resetButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");

        buttonBox.getChildren().addAll(saveButton, cancelButton, resetButton);
        buttonBox.setAlignment(Pos.CENTER);

        formContainer.getChildren().addAll(titleLabel, formGrid, buttonBox);

        return formContainer;
    }
}