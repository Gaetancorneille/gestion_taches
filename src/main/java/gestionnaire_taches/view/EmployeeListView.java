package gestionnaire_taches.view;

import gestionnaire_taches.dao.impl.EmployeeDAOImpl;
import gestionnaire_taches.view.forms.EmployeeFormView;
import gestionnaire_taches.model.Employee;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.util.Optional;

public class EmployeeListView {

    private ObservableList<Employee> employees;

    public EmployeeListView(ObservableList<Employee> employees) {
        this.employees = employees != null ? employees : FXCollections.observableArrayList();
    }

    public VBox getView() {
        VBox container = new VBox(10);
        container.setPadding(new Insets(20));
        container.setStyle("-fx-background-color: #ecf0f1;");

        Label titleLabel = new Label("Liste des Employés");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        TableView<Employee> tableView = createEmployeeTable();

        Button addButton    = new Button("➕ Ajouter");
        Button editButton   = new Button("✏️ Modifier");
        Button deleteButton = new Button("🗑️ Supprimer");

        addButton.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white; -fx-cursor: hand;");
        editButton.setStyle("-fx-background-color: #f39c12; -fx-text-fill: white; -fx-cursor: hand;");
        deleteButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-cursor: hand;");

        addButton.setOnAction(e ->
            gestionnaire_taches.Main.getMainLayout().setCenter(new gestionnaire_taches.view.forms.EmployeeFormView().getView())
        );

        editButton.setOnAction(e -> {
            Employee selected = tableView.getSelectionModel().getSelectedItem();
            if (selected == null) { showAlert("Veuillez sélectionner un employé à modifier."); return; }
            gestionnaire_taches.Main.getMainLayout().setCenter(new gestionnaire_taches.view.forms.EmployeeFormView(selected).getView());
        });

        deleteButton.setOnAction(e -> {
            Employee selected = tableView.getSelectionModel().getSelectedItem();
            if (selected == null) { showAlert("Veuillez sélectionner un employé à supprimer."); return; }
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                "Supprimer l'employé \"" + selected.getNom() + "\" ?", ButtonType.OK, ButtonType.CANCEL);
            confirm.setHeaderText(null);
            Optional<ButtonType> result = confirm.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                new EmployeeDAOImpl().delete(selected.getId());
                employees.remove(selected);
            }
        });

        HBox buttonBox = new HBox(10, addButton, editButton, deleteButton);
        buttonBox.setPadding(new Insets(10, 0, 0, 0));

        container.getChildren().addAll(titleLabel, tableView, buttonBox);
        return container;
    }

    private TableView<Employee> createEmployeeTable() {
        TableColumn<Employee, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(data -> javafx.beans.binding.Bindings.createObjectBinding(() -> data.getValue().getId()));

        TableColumn<Employee, String> nameCol = new TableColumn<>("Nom");
        nameCol.setCellValueFactory(data -> javafx.beans.binding.Bindings.createStringBinding(() -> data.getValue().getNom()));
        nameCol.setPrefWidth(160);

        TableColumn<Employee, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(data -> javafx.beans.binding.Bindings.createStringBinding(() -> data.getValue().getEmail()));
        emailCol.setPrefWidth(200);

        TableColumn<Employee, String> posteCol = new TableColumn<>("Poste");
        posteCol.setCellValueFactory(data -> javafx.beans.binding.Bindings.createStringBinding(() -> data.getValue().getPoste()));
        posteCol.setPrefWidth(150);

        TableColumn<Employee, Integer> serviceIdCol = new TableColumn<>("ID Service");
        serviceIdCol.setCellValueFactory(data -> javafx.beans.binding.Bindings.createObjectBinding(() -> data.getValue().getServiceId()));

        TableColumn<Employee, Boolean> activeCol = new TableColumn<>("Actif");
        activeCol.setCellValueFactory(data -> javafx.beans.binding.Bindings.createBooleanBinding(() -> data.getValue().isActif()));

        TableView<Employee> tableView = new TableView<>();
        tableView.getColumns().addAll(idCol, nameCol, emailCol, posteCol, serviceIdCol, activeCol);
        tableView.setItems(employees);
        tableView.setPrefHeight(450);
        return tableView;
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING, message, ButtonType.OK);
        alert.setHeaderText(null);
        alert.showAndWait();
    }
}