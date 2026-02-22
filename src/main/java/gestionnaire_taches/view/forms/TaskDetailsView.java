package gestionnaire_taches.view.forms;

import gestionnaire_taches.view.TaskListView;
import gestionnaire_taches.dao.impl.SubtaskDAOImpl;
import gestionnaire_taches.view.forms.TaskFormView;
import gestionnaire_taches.dao.impl.TaskDAOImpl;
import gestionnaire_taches.model.Subtask;
import gestionnaire_taches.model.Task;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.GridPane;

import java.util.List;
import java.util.Optional;

public class TaskDetailsView {

    private Task task;
    private ObservableList<Subtask> subtasks;

    public TaskDetailsView(Task task) {
        this.task = task;
        this.subtasks = FXCollections.observableArrayList();
        if (task != null) {
            // Chargement des sous-tâches depuis la DB
            List<Subtask> loaded = new SubtaskDAOImpl().findByTaskId(task.getId());
            if (loaded != null) subtasks.addAll(loaded);
        }
    }

    public VBox getView() {
        VBox container = new VBox(15);
        container.setPadding(new Insets(20));
        container.setStyle("-fx-background-color: #ecf0f1;");

        Label titleLabel = new Label("Détails de la Tâche");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        // Fiche de la tâche
        GridPane detailsGrid = new GridPane();
        detailsGrid.setHgap(15);
        detailsGrid.setVgap(10);
        detailsGrid.setPadding(new Insets(15));
        detailsGrid.setStyle("-fx-background-color: white; -fx-background-radius: 8;");

        int row = 0;
        addDetailRow(detailsGrid, row++, "Titre :",       task != null ? task.getTitre() : "N/A");
        addDetailRow(detailsGrid, row++, "Description :", task != null ? task.getDescription() : "N/A");
        addDetailRow(detailsGrid, row++, "Statut :",      task != null ? task.getStatut().toString() : "N/A");
        addDetailRow(detailsGrid, row++, "Priorité :",    task != null ? task.getPriorite().toString() : "N/A");
        addDetailRow(detailsGrid, row++, "Date limite :", task != null && task.getDateLimite() != null ? task.getDateLimite().toString() : "N/A");
        addDetailRow(detailsGrid, row++, "ID Employé :",  task != null ? String.valueOf(task.getEmployeeId()) : "N/A");
        addDetailRow(detailsGrid, row++, "ID Service :",  task != null ? String.valueOf(task.getServiceId()) : "N/A");

        // Section sous-tâches
        Label subtasksLabel = new Label("Sous-tâches");
        subtasksLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        TableView<Subtask> subtasksTable = createSubtasksTable();

        // Boutons sous-tâches
        Button addSubtaskBtn  = new Button("➕ Ajouter sous-tâche");
        Button editSubtaskBtn = new Button("✏️ Modifier");
        Button delSubtaskBtn  = new Button("🗑️ Supprimer");

        addSubtaskBtn.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white; -fx-cursor: hand;");
        editSubtaskBtn.setStyle("-fx-background-color: #f39c12; -fx-text-fill: white; -fx-cursor: hand;");
        delSubtaskBtn.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-cursor: hand;");

        addSubtaskBtn.setOnAction(e ->
            gestionnaire_taches.Main.getMainLayout().setCenter(new SubtaskFormView(task).getView())
        );

        editSubtaskBtn.setOnAction(e -> {
            Subtask selected = subtasksTable.getSelectionModel().getSelectedItem();
            if (selected == null) { showAlert("Veuillez sélectionner une sous-tâche à modifier."); return; }
            gestionnaire_taches.Main.getMainLayout().setCenter(new SubtaskFormView(selected, task).getView());
        });

        delSubtaskBtn.setOnAction(e -> {
            Subtask selected = subtasksTable.getSelectionModel().getSelectedItem();
            if (selected == null) { showAlert("Veuillez sélectionner une sous-tâche à supprimer."); return; }
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                "Supprimer la sous-tâche \"" + selected.getTitre() + "\" ?", ButtonType.OK, ButtonType.CANCEL);
            confirm.setHeaderText(null);
            Optional<ButtonType> result = confirm.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                new SubtaskDAOImpl().delete(selected.getId());
                subtasks.remove(selected);
            }
        });

        HBox subtaskBtnBox = new HBox(10, addSubtaskBtn, editSubtaskBtn, delSubtaskBtn);

        // Boutons principaux
        Button editTaskButton = new Button("✏️ Modifier cette tâche");
        Button backButton     = new Button("⬅ Retour aux tâches");

        editTaskButton.setStyle("-fx-background-color: #f39c12; -fx-text-fill: white; -fx-cursor: hand;");
        backButton.setStyle("-fx-background-color: #7f8c8d; -fx-text-fill: white; -fx-cursor: hand;");

        editTaskButton.setOnAction(e ->
            gestionnaire_taches.Main.getMainLayout().setCenter(new TaskFormView(task).getView())
        );

        backButton.setOnAction(e -> {
            List<Task> list = new TaskDAOImpl().findAll();
            gestionnaire_taches.Main.getMainLayout().setCenter(
                new gestionnaire_taches.view.TaskListView(FXCollections.observableArrayList(list)).getView());
        });

        HBox mainBtnBox = new HBox(10, editTaskButton, backButton);
        mainBtnBox.setPadding(new Insets(10, 0, 0, 0));

        container.getChildren().addAll(
            titleLabel, detailsGrid,
            subtasksLabel, subtasksTable, subtaskBtnBox,
            mainBtnBox
        );

        return container;
    }

    private void addDetailRow(GridPane grid, int row, String label, String value) {
        Label lbl = new Label(label);
        lbl.setStyle("-fx-font-weight: bold; -fx-text-fill: #555;");
        Label val = new Label(value);
        val.setWrapText(true);
        grid.add(lbl, 0, row);
        grid.add(val, 1, row);
    }

    private TableView<Subtask> createSubtasksTable() {
        TableColumn<Subtask, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(data -> javafx.beans.binding.Bindings.createObjectBinding(() -> data.getValue().getId()));

        TableColumn<Subtask, String> titleCol = new TableColumn<>("Titre");
        titleCol.setCellValueFactory(data -> javafx.beans.binding.Bindings.createStringBinding(() -> data.getValue().getTitre()));
        titleCol.setPrefWidth(200);

        TableColumn<Subtask, String> descCol = new TableColumn<>("Description");
        descCol.setCellValueFactory(data -> javafx.beans.binding.Bindings.createStringBinding(() -> data.getValue().getDescription()));
        descCol.setPrefWidth(200);

        TableColumn<Subtask, Integer> orderCol = new TableColumn<>("Ordre");
        orderCol.setCellValueFactory(data -> javafx.beans.binding.Bindings.createObjectBinding(() -> data.getValue().getOrdre()));

        TableColumn<Subtask, gestionnaire_taches.model.TaskStatus> statusCol = new TableColumn<>("Statut");
        statusCol.setCellValueFactory(data -> javafx.beans.binding.Bindings.createObjectBinding(() -> data.getValue().getStatut()));
        statusCol.setPrefWidth(100);

        TableView<Subtask> tableView = new TableView<>();
        tableView.getColumns().addAll(idCol, titleCol, descCol, orderCol, statusCol);
        tableView.setItems(subtasks);
        tableView.setPrefHeight(200);
        return tableView;
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING, message, ButtonType.OK);
        alert.setHeaderText(null);
        alert.showAndWait();
    }
}