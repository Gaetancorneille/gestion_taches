package gestionnaire_taches.view;

import gestionnaire_taches.dao.impl.TaskDAOImpl;
import gestionnaire_taches.model.Task;
import gestionnaire_taches.view.forms.TaskFormView;
import gestionnaire_taches.model.Priority;
import gestionnaire_taches.model.TaskStatus;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.util.Optional;

public class TaskListView {

    private ObservableList<Task> tasks;

    public TaskListView(ObservableList<Task> tasks) {
        this.tasks = tasks != null ? tasks : FXCollections.observableArrayList();
    }

    public VBox getView() {
        VBox container = new VBox(10);
        container.setPadding(new Insets(20));
        container.setStyle("-fx-background-color: #ecf0f1;");

        Label titleLabel = new Label("Liste des Tâches");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        TableView<Task> tableView = createTaskTable();

        Button addButton     = new Button("➕ Ajouter");
        Button editButton    = new Button("✏️ Modifier");
        Button deleteButton  = new Button("🗑️ Supprimer");
        Button detailsButton = new Button("🔍 Voir détails");

        addButton.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white; -fx-cursor: hand;");
        editButton.setStyle("-fx-background-color: #f39c12; -fx-text-fill: white; -fx-cursor: hand;");
        deleteButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-cursor: hand;");
        detailsButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-cursor: hand;");

        addButton.setOnAction(e ->
            gestionnaire_taches.Main.getMainLayout().setCenter(new gestionnaire_taches.view.forms.TaskFormView().getView())
        );

        editButton.setOnAction(e -> {
            Task selected = tableView.getSelectionModel().getSelectedItem();
            if (selected == null) { showAlert("Veuillez sélectionner une tâche à modifier."); return; }
            gestionnaire_taches.Main.getMainLayout().setCenter(new gestionnaire_taches.view.forms.TaskFormView(selected).getView());
        });

        deleteButton.setOnAction(e -> {
            Task selected = tableView.getSelectionModel().getSelectedItem();
            if (selected == null) { showAlert("Veuillez sélectionner une tâche à supprimer."); return; }
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                "Supprimer la tâche \"" + selected.getTitre() + "\" ?", ButtonType.OK, ButtonType.CANCEL);
            confirm.setHeaderText(null);
            Optional<ButtonType> result = confirm.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                new TaskDAOImpl().delete(selected.getId());
                tasks.remove(selected);
            }
        });

        detailsButton.setOnAction(e -> {
            Task selected = tableView.getSelectionModel().getSelectedItem();
            if (selected == null) { showAlert("Veuillez sélectionner une tâche."); return; }
            gestionnaire_taches.Main.getMainLayout().setCenter(new gestionnaire_taches.view.forms.TaskDetailsView(selected).getView());
        });

        // Double-clic sur une ligne ouvre aussi les détails
        tableView.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                Task selected = tableView.getSelectionModel().getSelectedItem();
                if (selected != null)
                    gestionnaire_taches.Main.getMainLayout().setCenter(new gestionnaire_taches.view.forms.TaskDetailsView(selected).getView());
            }
        });

        HBox buttonBox = new HBox(10, addButton, editButton, deleteButton, detailsButton);
        buttonBox.setPadding(new Insets(10, 0, 0, 0));

        container.getChildren().addAll(titleLabel, tableView, buttonBox);
        return container;
    }

    private TableView<Task> createTaskTable() {
        TableColumn<Task, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(data -> javafx.beans.binding.Bindings.createObjectBinding(() -> data.getValue().getId()));

        TableColumn<Task, String> titleCol = new TableColumn<>("Titre");
        titleCol.setCellValueFactory(data -> javafx.beans.binding.Bindings.createStringBinding(() -> data.getValue().getTitre()));
        titleCol.setPrefWidth(200);

        TableColumn<Task, String> descCol = new TableColumn<>("Description");
        descCol.setCellValueFactory(data -> javafx.beans.binding.Bindings.createStringBinding(() -> data.getValue().getDescription()));
        descCol.setPrefWidth(200);

        TableColumn<Task, Integer> empIdCol = new TableColumn<>("ID Employé");
        empIdCol.setCellValueFactory(data -> javafx.beans.binding.Bindings.createObjectBinding(() -> data.getValue().getEmployeeId()));

        TableColumn<Task, Integer> servIdCol = new TableColumn<>("ID Service");
        servIdCol.setCellValueFactory(data -> javafx.beans.binding.Bindings.createObjectBinding(() -> data.getValue().getServiceId()));

        TableColumn<Task, TaskStatus> statusCol = new TableColumn<>("Statut");
        statusCol.setCellValueFactory(data -> javafx.beans.binding.Bindings.createObjectBinding(() -> data.getValue().getStatut()));
        statusCol.setPrefWidth(100);

        TableColumn<Task, Priority> priorityCol = new TableColumn<>("Priorité");
        priorityCol.setCellValueFactory(data -> javafx.beans.binding.Bindings.createObjectBinding(() -> data.getValue().getPriorite()));
        priorityCol.setPrefWidth(100);

        TableView<Task> tableView = new TableView<>();
        tableView.getColumns().addAll(idCol, titleCol, descCol, empIdCol, servIdCol, statusCol, priorityCol);
        tableView.setItems(tasks);
        tableView.setPrefHeight(420);
        return tableView;
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING, message, ButtonType.OK);
        alert.setHeaderText(null);
        alert.showAndWait();
    }
}