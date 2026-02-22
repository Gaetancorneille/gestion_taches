package gestionnaire_taches.view;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TaskDetailsView {

    private gestionnaire_taches.model.Task task;
    private ObservableList<gestionnaire_taches.model.Subtask> subtasks;

    public TaskDetailsView(gestionnaire_taches.model.Task task) {
        this.task = task;
        this.subtasks = FXCollections.observableArrayList();
        if (task != null && task.getSubtasks() != null) {
            this.subtasks.addAll(task.getSubtasks());
        }
    }

    public VBox getView() {
        VBox container = new VBox(15);
        container.setPadding(new Insets(20));

        // Title
        Label titleLabel = new Label("Détails de la Tâche");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        // Task details
        GridPane detailsGrid = new GridPane();
        detailsGrid.setHgap(10);
        detailsGrid.setVgap(10);

        // Add task details
        int row = 0;
        detailsGrid.add(new Label("ID:"), 0, row);
        detailsGrid.add(new Label(task != null ? String.valueOf(task.getId()) : "N/A"), 1, row++);

        detailsGrid.add(new Label("Titre:"), 0, row);
        detailsGrid.add(new Label(task != null ? task.getTitre() : "N/A"), 1, row++);

        detailsGrid.add(new Label("Description:"), 0, row);
        Label descLabel = new Label(task != null ? task.getDescription() : "N/A");
        descLabel.setWrapText(true);
        detailsGrid.add(descLabel, 1, row++);

        detailsGrid.add(new Label("Statut:"), 0, row);
        detailsGrid.add(new Label(task != null ? task.getStatut().toString() : "N/A"), 1, row++);

        detailsGrid.add(new Label("Priorité:"), 0, row);
        detailsGrid.add(new Label(task != null ? task.getPriorite().toString() : "N/A"), 1, row++);

        detailsGrid.add(new Label("Date Limite:"), 0, row);
        detailsGrid.add(new Label(task != null ? (task.getDateLimite() != null ? task.getDateLimite().toString() : "N/A") : "N/A"), 1, row++);

        detailsGrid.add(new Label("ID Employé:"), 0, row);
        detailsGrid.add(new Label(task != null ? String.valueOf(task.getEmployeeId()) : "N/A"), 1, row++);

        detailsGrid.add(new Label("ID Service:"), 0, row);
        detailsGrid.add(new Label(task != null ? String.valueOf(task.getServiceId()) : "N/A"), 1, row++);

        // Subtasks section
        Label subtasksLabel = new Label("Sous-tâches");
        subtasksLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        
        TableView<gestionnaire_taches.model.Subtask> subtasksTable = createSubtasksTable();

        // Buttons
        HBox buttonBox = new HBox(10);
        Button addSubtaskButton = new Button("Ajouter Sous-tâche");
        Button editTaskButton = new Button("Modifier Tâche");
        Button backButton = new Button("Retour");
        
        buttonBox.getChildren().addAll(addSubtaskButton, editTaskButton, backButton);
        buttonBox.setPadding(new Insets(10, 0, 0, 0));

        container.getChildren().addAll(titleLabel, detailsGrid, subtasksLabel, subtasksTable, buttonBox);

        return container;
    }

    private TableView<gestionnaire_taches.model.Subtask> createSubtasksTable() {
        // Create columns
        TableColumn<gestionnaire_taches.model.Subtask, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(data -> javafx.beans.binding.Bindings.createObjectBinding(() -> 
            data.getValue().getId()));

        TableColumn<gestionnaire_taches.model.Subtask, String> titleCol = new TableColumn<>("Titre");
        titleCol.setCellValueFactory(data -> javafx.beans.binding.Bindings.createStringBinding(() -> 
            data.getValue().getTitre()));

        TableColumn<gestionnaire_taches.model.Subtask, String> descCol = new TableColumn<>("Description");
        descCol.setCellValueFactory(data -> javafx.beans.binding.Bindings.createStringBinding(() -> 
            data.getValue().getDescription()));

        TableColumn<gestionnaire_taches.model.Subtask, Integer> taskIdCol = new TableColumn<>("ID Tâche");
        taskIdCol.setCellValueFactory(data -> javafx.beans.binding.Bindings.createObjectBinding(() -> 
            data.getValue().getTaskId()));

        TableColumn<gestionnaire_taches.model.Subtask, Integer> orderCol = new TableColumn<>("Ordre");
        orderCol.setCellValueFactory(data -> javafx.beans.binding.Bindings.createObjectBinding(() -> 
            data.getValue().getOrdre()));

        TableColumn<gestionnaire_taches.model.Subtask, gestionnaire_taches.model.TaskStatus> statusCol = new TableColumn<>("Statut");
        statusCol.setCellValueFactory(data -> javafx.beans.binding.Bindings.createObjectBinding(() -> 
            data.getValue().getStatut()));

        // Create table and set items
        TableView<gestionnaire_taches.model.Subtask> tableView = new TableView<>();
        tableView.getColumns().addAll(idCol, titleCol, descCol, taskIdCol, orderCol, statusCol);
        tableView.setItems(subtasks);

        // Set table properties
        tableView.setPrefHeight(200);
        
        return tableView;
    }
}