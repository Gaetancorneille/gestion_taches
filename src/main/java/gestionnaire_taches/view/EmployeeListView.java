package gestionnaire_taches.view;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class EmployeeListView {

    private ObservableList<gestionnaire_taches.model.Employee> employees;

    public EmployeeListView(ObservableList<gestionnaire_taches.model.Employee> employees) {
        this.employees = employees != null ? employees : FXCollections.observableArrayList();
    }

    public VBox getView() {
        VBox container = new VBox(10);
        container.setPadding(new Insets(20));

        // Title
        Label titleLabel = new Label("Liste des Employés");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        // Create table
        TableView<gestionnaire_taches.model.Employee> tableView = createEmployeeTable();
        
        // Buttons
        HBox buttonBox = new HBox(10);
        Button addButton = new Button("Ajouter Employé");
        Button editButton = new Button("Modifier Employé");
        Button deleteButton = new Button("Supprimer Employé");
        
        buttonBox.getChildren().addAll(addButton, editButton, deleteButton);
        buttonBox.setPadding(new Insets(10, 0, 0, 0));

        container.getChildren().addAll(titleLabel, tableView, buttonBox);

        return container;
    }

    private TableView<gestionnaire_taches.model.Employee> createEmployeeTable() {
        // Create columns
        TableColumn<gestionnaire_taches.model.Employee, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(data -> javafx.beans.binding.Bindings.createObjectBinding(() -> 
            data.getValue().getId()));

        TableColumn<gestionnaire_taches.model.Employee, String> nameCol = new TableColumn<>("Nom");
        nameCol.setCellValueFactory(data -> javafx.beans.binding.Bindings.createStringBinding(() -> 
            data.getValue().getNom()));

        TableColumn<gestionnaire_taches.model.Employee, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(data -> javafx.beans.binding.Bindings.createStringBinding(() -> 
            data.getValue().getEmail()));

        TableColumn<gestionnaire_taches.model.Employee, String> posteCol = new TableColumn<>("Poste");
        posteCol.setCellValueFactory(data -> javafx.beans.binding.Bindings.createStringBinding(() -> 
            data.getValue().getPoste()));

        TableColumn<gestionnaire_taches.model.Employee, Integer> serviceIdCol = new TableColumn<>("ID Service");
        serviceIdCol.setCellValueFactory(data -> javafx.beans.binding.Bindings.createObjectBinding(() -> 
            data.getValue().getServiceId()));

        TableColumn<gestionnaire_taches.model.Employee, Boolean> activeCol = new TableColumn<>("Actif");
        activeCol.setCellValueFactory(data -> javafx.beans.binding.Bindings.createBooleanBinding(() -> 
            data.getValue().isActif()));

        // Create table and set items
        TableView<gestionnaire_taches.model.Employee> tableView = new TableView<>();
        tableView.getColumns().addAll(idCol, nameCol, emailCol, posteCol, serviceIdCol, activeCol);
        tableView.setItems(employees);

        // Set table properties
        tableView.setPrefHeight(400);
        
        return tableView;
    }
}