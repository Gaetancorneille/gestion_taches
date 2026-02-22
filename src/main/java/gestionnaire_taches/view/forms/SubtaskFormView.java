package gestionnaire_taches.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.collections.FXCollections;

public class SubtaskFormView {

    public VBox getView() {
        VBox formContainer = new VBox(15);
        formContainer.setPadding(new Insets(20));

        // Title
        Label titleLabel = new Label("Formulaire de Sous-tâche");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        // Form grid
        GridPane formGrid = new GridPane();
        formGrid.setHgap(10);
        formGrid.setVgap(10);

        // Fields
        Label titleLabelField = new Label("Titre:");
        TextField titleField = new TextField();
        titleField.setPrefWidth(300);

        Label descriptionLabel = new Label("Description:");
        TextArea descriptionArea = new TextArea();
        descriptionArea.setPrefRowCount(4);
        descriptionArea.setPrefWidth(300);

        Label taskIdLabel = new Label("ID Tâche:");
        TextField taskIdField = new TextField();
        taskIdField.setPrefWidth(100);

        Label orderLabel = new Label("Ordre:");
        TextField orderField = new TextField();
        orderField.setPrefWidth(100);

        Label statusLabel = new Label("Statut:");
        ComboBox<gestionnaire_taches.model.TaskStatus> statusCombo = new ComboBox<>();
        statusCombo.setItems(FXCollections.observableArrayList(
            gestionnaire_taches.model.TaskStatus.A_FAIRE,
            gestionnaire_taches.model.TaskStatus.EN_COURS,
            gestionnaire_taches.model.TaskStatus.TERMINEE
        ));

        // Add fields to grid
        int row = 0;
        formGrid.add(titleLabelField, 0, row);
        formGrid.add(titleField, 1, row++);
        
        formGrid.add(descriptionLabel, 0, row);
        formGrid.add(descriptionArea, 1, row++);
        
        formGrid.add(taskIdLabel, 0, row);
        formGrid.add(taskIdField, 1, row++);
        
        formGrid.add(orderLabel, 0, row);
        formGrid.add(orderField, 1, row++);
        
        formGrid.add(statusLabel, 0, row);
        formGrid.add(statusCombo, 1, row++);

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