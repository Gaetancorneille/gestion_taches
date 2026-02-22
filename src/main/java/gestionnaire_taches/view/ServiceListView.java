package gestionnaire_taches.view;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ServiceListView {

    private ObservableList<gestionnaire_taches.model.Service> services;

    public ServiceListView(ObservableList<gestionnaire_taches.model.Service> services) {
        this.services = services != null ? services : FXCollections.observableArrayList();
    }

    public VBox getView() {
        VBox container = new VBox(10);
        container.setPadding(new Insets(20));

        // Title
        Label titleLabel = new Label("Liste des Services");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        // Create table
        TableView<gestionnaire_taches.model.Service> tableView = createServiceTable();
        
        // Buttons
        HBox buttonBox = new HBox(10);
        Button addButton = new Button("Ajouter Service");
        Button editButton = new Button("Modifier Service");
        Button deleteButton = new Button("Supprimer Service");
        
        buttonBox.getChildren().addAll(addButton, editButton, deleteButton);
        buttonBox.setPadding(new Insets(10, 0, 0, 0));

        container.getChildren().addAll(titleLabel, tableView, buttonBox);

        return container;
    }

    private TableView<gestionnaire_taches.model.Service> createServiceTable() {
        // Create columns
        TableColumn<gestionnaire_taches.model.Service, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(data -> javafx.beans.binding.Bindings.createObjectBinding(() -> 
            data.getValue().getId()));

        TableColumn<gestionnaire_taches.model.Service, String> nameCol = new TableColumn<>("Nom");
        nameCol.setCellValueFactory(data -> javafx.beans.binding.Bindings.createStringBinding(() -> 
            data.getValue().getNom()));

        TableColumn<gestionnaire_taches.model.Service, String> descCol = new TableColumn<>("Description");
        descCol.setCellValueFactory(data -> javafx.beans.binding.Bindings.createStringBinding(() -> 
            data.getValue().getDescription()));

        TableColumn<gestionnaire_taches.model.Service, Integer> adminIdCol = new TableColumn<>("ID Admin");
        adminIdCol.setCellValueFactory(data -> javafx.beans.binding.Bindings.createObjectBinding(() -> 
            data.getValue().getAdministratorId()));

        TableColumn<gestionnaire_taches.model.Service, Boolean> activeCol = new TableColumn<>("Actif");
        activeCol.setCellValueFactory(data -> javafx.beans.binding.Bindings.createBooleanBinding(() -> 
            data.getValue().isActif()));

        // Create table and set items
        TableView<gestionnaire_taches.model.Service> tableView = new TableView<>();
        tableView.getColumns().addAll(idCol, nameCol, descCol, adminIdCol, activeCol);
        tableView.setItems(services);

        // Set table properties
        tableView.setPrefHeight(400);
        
        return tableView;
    }
}