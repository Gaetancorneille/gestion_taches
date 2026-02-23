package gestionnaire_taches.view.forms;

import java.util.List;

import gestionnaire_taches.dao.impl.SubtaskDAOImpl;
import gestionnaire_taches.dao.impl.TaskDAOImpl;
import gestionnaire_taches.model.Subtask;
import gestionnaire_taches.model.Task;
import gestionnaire_taches.model.TaskStatus;
import gestionnaire_taches.view.AppShell;
import gestionnaire_taches.view.TaskListView;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class SubtaskFormView {

    private Subtask existingSubtask;
    private Task parentTask;

    public SubtaskFormView(Task parentTask) {
        this.existingSubtask = null;
        this.parentTask = parentTask;
    }

    public SubtaskFormView(Subtask subtask, Task parentTask) {
        this.existingSubtask = subtask;
        this.parentTask = parentTask;
    }

    public VBox getView() {
        VBox formContainer = new VBox(15);
        formContainer.setPadding(new Insets(20));
        formContainer.setStyle("-fx-background-color: #ecf0f1;");

        boolean isEdit = existingSubtask != null;
        Label titleLabel = new Label(isEdit ? "Modifier la Sous-tâche" : "Nouvelle Sous-tâche");
        titleLabel.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        // Info tâche parente
        if (parentTask != null) {
            Label parentInfo = new Label("Tâche parente : " + parentTask.getTitre());
            parentInfo.setStyle("-fx-text-fill: #7f8c8d; -fx-font-size: 13px;");
            formContainer.getChildren().addAll(titleLabel, parentInfo);
        } else {
            formContainer.getChildren().add(titleLabel);
        }

        GridPane formGrid = new GridPane();
        formGrid.setHgap(15);
        formGrid.setVgap(12);
        formGrid.setPadding(new Insets(20));
        formGrid.setStyle("-fx-background-color: white; -fx-background-radius: 8;");

        TextField titleField = new TextField();
        titleField.setPromptText("Titre de la sous-tâche");
        titleField.setPrefWidth(300);

        TextArea descriptionArea = new TextArea();
        descriptionArea.setPrefRowCount(3);
        descriptionArea.setPrefWidth(300);
        descriptionArea.setPromptText("Description");

        TextField orderField = new TextField();
        orderField.setPromptText("Ex: 1, 2, 3...");
        orderField.setPrefWidth(100);

        ComboBox<TaskStatus> statusCombo = new ComboBox<>();
        statusCombo.setItems(FXCollections.observableArrayList(
            TaskStatus.A_FAIRE, TaskStatus.EN_COURS, TaskStatus.TERMINEE
        ));
        statusCombo.setPrefWidth(300);

        // Pré-remplissage en édition
        if (isEdit) {
            titleField.setText(existingSubtask.getTitre());
            descriptionArea.setText(existingSubtask.getDescription());
            orderField.setText(String.valueOf(existingSubtask.getOrdre()));
            statusCombo.setValue(existingSubtask.getStatut());
        } else {
            statusCombo.setValue(TaskStatus.A_FAIRE);
        }

        int row = 0;
        formGrid.add(new Label("Titre :"), 0, row);       formGrid.add(titleField, 1, row++);
        formGrid.add(new Label("Description :"), 0, row); formGrid.add(descriptionArea, 1, row++);
        formGrid.add(new Label("Ordre :"), 0, row);       formGrid.add(orderField, 1, row++);
        formGrid.add(new Label("Statut :"), 0, row);      formGrid.add(statusCombo, 1, row++);

        Button saveButton   = new Button(isEdit ? "Modifier" : "Créer");
        Button cancelButton = new Button("Annuler");
        Button resetButton  = new Button("Réinitialiser");

        saveButton.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white; -fx-cursor: hand;");
        cancelButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-cursor: hand;");
        resetButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-cursor: hand;");

        saveButton.setOnAction(e -> {
            String titre = titleField.getText().trim();
            String desc  = descriptionArea.getText().trim();
            String orderStr = orderField.getText().trim();
            TaskStatus status = statusCombo.getValue();

            if (titre.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Champ manquant", "Le titre est obligatoire.");
                return;
            }
            if (status == null) {
                showAlert(Alert.AlertType.ERROR, "Champ manquant", "Veuillez sélectionner un statut.");
                return;
            }
            int ordre = 1;
            try {
                if (!orderStr.isEmpty()) ordre = Integer.parseInt(orderStr);
            } catch (NumberFormatException ex) {
                showAlert(Alert.AlertType.ERROR, "Format invalide", "L'ordre doit être un nombre entier.");
                return;
            }

            SubtaskDAOImpl dao = new SubtaskDAOImpl();

            if (isEdit) {
                existingSubtask.setTitre(titre);
                existingSubtask.setDescription(desc);
                existingSubtask.setOrdre(ordre);
                existingSubtask.setStatut(status);
                dao.update(existingSubtask);
                showAlert(Alert.AlertType.INFORMATION, "Succès", "Sous-tâche modifiée avec succès !");
            } else {
                int taskId = parentTask != null ? parentTask.getId() : 0;
                Subtask subtask = new Subtask(titre, desc, taskId);
                subtask.setOrdre(ordre);
                subtask.setStatut(status);
                dao.save(subtask);
                showAlert(Alert.AlertType.INFORMATION, "Succès", "Sous-tâche créée avec succès !");
            }

            // Retour aux détails de la tâche parente
            if (parentTask != null) {
                AppShell shell = gestionnaire_taches.Main.getAppShell();
                if (shell != null) {
                    shell.navigateTo(new TaskDetailsView(parentTask).getView());
                }
            } else {
                List<Task> list = new TaskDAOImpl().findAll();
                AppShell shell = gestionnaire_taches.Main.getAppShell();
                if (shell != null) {
                    shell.navigateTo(new TaskListView(FXCollections.observableArrayList(list)).getView());
                }
            }
        });

        cancelButton.setOnAction(e -> {
            AppShell shell = gestionnaire_taches.Main.getAppShell();
            if (shell != null) {
                if (parentTask != null) {
                    shell.navigateTo(new TaskDetailsView(parentTask).getView());
                } else {
                    List<Task> list = new TaskDAOImpl().findAll();
                    shell.navigateTo(new TaskListView(FXCollections.observableArrayList(list)).getView());
                }
            }
        });

        resetButton.setOnAction(e -> {
            titleField.clear();
            descriptionArea.clear();
            orderField.clear();
            statusCombo.setValue(TaskStatus.A_FAIRE);
        });

        HBox buttonBox = new HBox(10, saveButton, cancelButton, resetButton);
        buttonBox.setAlignment(Pos.CENTER);

        formContainer.getChildren().addAll(formGrid, buttonBox);
        return formContainer;
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}