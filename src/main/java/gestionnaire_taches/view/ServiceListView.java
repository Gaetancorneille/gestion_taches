package gestionnaire_taches.view;

import gestionnaire_taches.dao.impl.ServiceDAOImpl;
import gestionnaire_taches.model.Service;
import gestionnaire_taches.view.forms.ServiceFormView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.util.Optional;

public class ServiceListView {

    private ObservableList<Service> services;

    public ServiceListView(ObservableList<Service> services) {
        this.services = services != null ? services : FXCollections.observableArrayList();
    }

    public VBox getView() {
        VBox container = new VBox(10);
        container.setPadding(new Insets(20));
        container.setStyle("-fx-background-color: #ecf0f1;");

        Label titleLabel = new Label("Liste des Services");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        TableView<Service> tableView = createServiceTable();

        Button addButton    = new Button("➕ Ajouter");
        Button editButton   = new Button("✏️ Modifier");
        Button deleteButton = new Button("🗑️ Supprimer");

        addButton.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white; -fx-cursor: hand;");
        editButton.setStyle("-fx-background-color: #f39c12; -fx-text-fill: white; -fx-cursor: hand;");
        deleteButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-cursor: hand;");

        addButton.setOnAction(e ->
            gestionnaire_taches.Main.getMainLayout().setCenter(new gestionnaire_taches.view.forms.ServiceFormView().getView())
        );

        editButton.setOnAction(e -> {
            Service selected = tableView.getSelectionModel().getSelectedItem();
            if (selected == null) { showAlert("Veuillez sélectionner un service à modifier."); return; }
            gestionnaire_taches.Main.getMainLayout().setCenter(new gestionnaire_taches.view.forms.ServiceFormView(selected).getView());
        });

        deleteButton.setOnAction(e -> {
            Service selected = tableView.getSelectionModel().getSelectedItem();
            if (selected == null) { showAlert("Veuillez sélectionner un service à supprimer."); return; }
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                "Supprimer le service \"" + selected.getNom() + "\" ?", ButtonType.OK, ButtonType.CANCEL);
            confirm.setHeaderText(null);
            Optional<ButtonType> result = confirm.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                new ServiceDAOImpl().delete(selected.getId());
                services.remove(selected);
            }
        });

        HBox buttonBox = new HBox(10, addButton, editButton, deleteButton);
        buttonBox.setPadding(new Insets(10, 0, 0, 0));

        container.getChildren().addAll(titleLabel, tableView, buttonBox);
        return container;
    }

    private TableView<Service> createServiceTable() {
        TableColumn<Service, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(data -> javafx.beans.binding.Bindings.createObjectBinding(() -> data.getValue().getId()));

        TableColumn<Service, String> nameCol = new TableColumn<>("Nom");
        nameCol.setCellValueFactory(data -> javafx.beans.binding.Bindings.createStringBinding(() -> data.getValue().getNom()));
        nameCol.setPrefWidth(180);

        TableColumn<Service, String> descCol = new TableColumn<>("Description");
        descCol.setCellValueFactory(data -> javafx.beans.binding.Bindings.createStringBinding(() -> data.getValue().getDescription()));
        descCol.setPrefWidth(300);

        TableColumn<Service, Integer> adminIdCol = new TableColumn<>("ID Admin");
        adminIdCol.setCellValueFactory(data -> javafx.beans.binding.Bindings.createObjectBinding(() -> data.getValue().getAdministratorId()));

        TableColumn<Service, Boolean> activeCol = new TableColumn<>("Actif");
        activeCol.setCellValueFactory(data -> javafx.beans.binding.Bindings.createBooleanBinding(() -> data.getValue().isActif()));

        TableView<Service> tableView = new TableView<>();
        tableView.getColumns().addAll(idCol, nameCol, descCol, adminIdCol, activeCol);
        tableView.setItems(services);
        tableView.setPrefHeight(450);
        return tableView;
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING, message, ButtonType.OK);
        alert.setHeaderText(null);
        alert.showAndWait();
    }
}