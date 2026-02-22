package gestionnaire_taches.view;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TaskListView {

    private ObservableList<gestionnaire_taches.model.Task> tasks;

    public TaskListView(ObservableList<gestionnaire_taches.model.Task> tasks) {
        this.tasks = tasks != null ? tasks : FXCollections.observableArrayList();
    }

    public VBox getView() {
        VBox container = new VBox(10);
        container.setPadding(new Insets(20));

        // Title
        Label titleLabel = new Label("Liste des Tâches");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        // Create table
        TableView<gestionnaire_taches.model.Task> tableView = createTaskTable();
        
        // Buttons
        HBox buttonBox = new HBox(10);
        Button addButton = new Button("Ajouter Tâche");
        Button editButton = new Button("Modifier Tâche");
        Button deleteButton = new Button("Supprimer Tâche");
        
        buttonBox.getChildren().addAll(addButton, editButton, deleteButton);
        buttonBox.setPadding(new Insets(10, 0, 0, 0));

        container.getChildren().addAll(titleLabel, tableView, buttonBox);

        return container;
    }

    private TableView<gestionnaire_taches.model.Task> createTaskTable() {
        // Create columns
        TableColumn<gestionnaire_taches.model.Task, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(data -> javafx.beans.binding.Bindings.createObjectBinding(() -> 
            data.getValue().getId()));

        TableColumn<gestionnaire_taches.model.Task, String> titleCol = new TableColumn<>("Titre");
        titleCol.setCellValueFactory(data -> javafx.beans.binding.Bindings.createStringBinding(() -> 
            data.getValue().getTitre()));

        TableColumn<gestionnaire_taches.model.Task, String> descCol = new TableColumn<>("Description");
        descCol.setCellValueFactory(data -> javafx.beans.binding.Bindings.createStringBinding(() -> 
            data.getValue().getDescription()));

        TableColumn<gestionnaire_taches.model.Task, Integer> empIdCol = new TableColumn<>("ID Employé");
        empIdCol.setCellValueFactory(data -> javafx.beans.binding.Bindings.createObjectBinding(() -> 
            data.getValue().getEmployeeId()));

        TableColumn<gestionnaire_taches.model.Task, Integer> servIdCol = new TableColumn<>("ID Service");
        servIdCol.setCellValueFactory(data -> javafx.beans.binding.Bindings.createObjectBinding(() -> 
            data.getValue().getServiceId()));

        TableColumn<gestionnaire_taches.model.Task, gestionnaire_taches.model.TaskStatus> statusCol = new TableColumn<>("Statut");
        statusCol.setCellValueFactory(data -> javafx.beans.binding.Bindings.createObjectBinding(() -> 
            data.getValue().getStatut()));

        TableColumn<gestionnaire_taches.model.Task, gestionnaire_taches.model.Priority> priorityCol = new TableColumn<>("Priorité");
        priorityCol.setCellValueFactory(data -> javafx.beans.binding.Bindings.createObjectBinding(() -> 
            data.getValue().getPriorite()));

        // Create table and set items
        TableView<gestionnaire_taches.model.Task> tableView = new TableView<>();
        tableView.getColumns().addAll(idCol, titleCol, descCol, empIdCol, servIdCol, statusCol, priorityCol);
        tableView.setItems(tasks);

        // Set table properties
        tableView.setPrefHeight(400);
        
        return tableView;
    }
}