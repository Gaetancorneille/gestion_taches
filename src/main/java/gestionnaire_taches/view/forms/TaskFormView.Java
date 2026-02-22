package gestionnaire_taches.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.collections.FXCollections;

public class TaskFormView {

    public VBox getView() {
        VBox formContainer = new VBox(15);
        formContainer.setPadding(new Insets(20));

        // Title
        Label titleLabel = new Label("Formulaire de Tâche");
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

        Label employeeLabel = new Label("Employé:");
        ComboBox<Integer> employeeCombo = new ComboBox<>();
        employeeCombo.setItems(FXCollections.observableArrayList(1, 2, 3, 4, 5, 6));

        Label serviceLabel = new Label("Service:");
        ComboBox<Integer> serviceCombo = new ComboBox<>();
        serviceCombo.setItems(FXCollections.observableArrayList(1, 2, 3, 4, 5));

        Label priorityLabel = new Label("Priorité:");
        ComboBox<gestionnaire_taches.model.Priority> priorityCombo = new ComboBox<>();
        priorityCombo.setItems(FXCollections.observableArrayList(
            gestionnaire_taches.model.Priority.BASSE,
            gestionnaire_taches.model.Priority.NORMALE,
            gestionnaire_taches.model.Priority.HAUTE,
            gestionnaire_taches.model.Priority.URGENTE
        ));

        Label statusLabel = new Label("Statut:");
        ComboBox<gestionnaire_taches.model.TaskStatus> statusCombo = new ComboBox<>();
        statusCombo.setItems(FXCollections.observableArrayList(
            gestionnaire_taches.model.TaskStatus.A_FAIRE,
            gestionnaire_taches.model.TaskStatus.EN_COURS,
            gestionnaire_taches.model.TaskStatus.TERMINEE,
            gestionnaire_taches.model.TaskStatus.ANNULEE
        ));

        Label deadlineLabel = new Label("Date Limite:");
        DatePicker deadlinePicker = new DatePicker();

        // Add fields to grid
        int row = 0;
        formGrid.add(titleLabelField, 0, row);
        formGrid.add(titleField, 1, row++);
        
        formGrid.add(descriptionLabel, 0, row);
        formGrid.add(descriptionArea, 1, row++);
        
        formGrid.add(employeeLabel, 0, row);
        formGrid.add(employeeCombo, 1, row++);
        
        formGrid.add(serviceLabel, 0, row);
        formGrid.add(serviceCombo, 1, row++);
        
        formGrid.add(priorityLabel, 0, row);
        formGrid.add(priorityCombo, 1, row++);
        
        formGrid.add(statusLabel, 0, row);
        formGrid.add(statusCombo, 1, row++);
        
        formGrid.add(deadlineLabel, 0, row);
        formGrid.add(deadlinePicker, 1, row++);

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