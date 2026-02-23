package gestionnaire_taches.view;

import gestionnaire_taches.dao.impl.EmployeeDAOImpl;
import gestionnaire_taches.view.forms.EmployeeFormView;
import gestionnaire_taches.model.Employee;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.Optional;

public class EmployeeListView {

    private static final String COLOR_BG     = "#F5F7FA";
    private static final String COLOR_WHITE  = "#FFFFFF";
    private static final String COLOR_TITLE  = "#1E2A3A";
    private static final String COLOR_BORDER = "#E0E6ED";

    private ObservableList<Employee> employees;

    public EmployeeListView(ObservableList<Employee> employees) {
        this.employees = employees != null ? employees : FXCollections.observableArrayList();
    }

    public VBox getView() {
        VBox container = new VBox(20);
        container.setPadding(new Insets(30));
        container.setStyle("-fx-background-color: " + COLOR_BG + ";");

        // ── En-tête ──────────────────────────────────────────────────────
        HBox header = new HBox();
        header.setAlignment(Pos.CENTER_LEFT);

        Label titleLabel = new Label("👥  Employés");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        titleLabel.setStyle("-fx-text-fill: " + COLOR_TITLE + ";");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button addButton = actionBtn("➕  Ajouter", "#1565C0");
        addButton.setOnAction(e -> {
            AppShell shell = gestionnaire_taches.Main.getAppShell();
            if (shell != null) {
                shell.navigateTo(new EmployeeFormView().getView());
            }
        });

        header.getChildren().addAll(titleLabel, spacer, addButton);

        // ── Tableau ───────────────────────────────────────────────────────
        TableView<Employee> tableView = createEmployeeTable();

        // ── Barre d'actions ───────────────────────────────────────────────
        Button editButton   = actionBtn("✏️  Modifier",   "#E65100");
        Button deleteButton = actionBtn("🗑️  Supprimer",  "#C62828");

        editButton.setOnAction(e -> {
            Employee selected = tableView.getSelectionModel().getSelectedItem();
            if (selected == null) { showAlert("Veuillez sélectionner un employé à modifier."); return; }
            AppShell shell = gestionnaire_taches.Main.getAppShell();
            if (shell != null) {
                shell.navigateTo(new EmployeeFormView(selected).getView());
            }
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

        HBox actionBar = new HBox(10, editButton, deleteButton);
        actionBar.setAlignment(Pos.CENTER_LEFT);

        container.getChildren().addAll(header, tableView, actionBar);
        return container;
    }

    private TableView<Employee> createEmployeeTable() {
        TableColumn<Employee, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(data ->
            javafx.beans.binding.Bindings.createObjectBinding(() -> data.getValue().getId()));
        idCol.setPrefWidth(50);

        TableColumn<Employee, String> nameCol = new TableColumn<>("Nom");
        nameCol.setCellValueFactory(data ->
            javafx.beans.binding.Bindings.createStringBinding(() -> data.getValue().getNom()));
        nameCol.setPrefWidth(180);

        TableColumn<Employee, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(data ->
            javafx.beans.binding.Bindings.createStringBinding(() -> data.getValue().getEmail()));
        emailCol.setPrefWidth(220);

        TableColumn<Employee, String> posteCol = new TableColumn<>("Poste");
        posteCol.setCellValueFactory(data ->
            javafx.beans.binding.Bindings.createStringBinding(() -> data.getValue().getPoste()));
        posteCol.setPrefWidth(160);

        TableColumn<Employee, Integer> serviceIdCol = new TableColumn<>("Service");
        serviceIdCol.setCellValueFactory(data ->
            javafx.beans.binding.Bindings.createObjectBinding(() -> data.getValue().getServiceId()));
        serviceIdCol.setPrefWidth(80);

        TableColumn<Employee, Boolean> activeCol = new TableColumn<>("Actif");
        activeCol.setCellValueFactory(data ->
            new javafx.beans.property.SimpleBooleanProperty(data.getValue().isActif()));
        activeCol.setPrefWidth(70);

        // Cellule colorée pour le statut actif
        activeCol.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) { setText(null); setStyle(""); return; }
                setText(item ? "✔" : "✘");
                setStyle(item
                    ? "-fx-text-fill: #2E7D32; -fx-font-weight: bold; -fx-alignment: center;"
                    : "-fx-text-fill: #C62828; -fx-font-weight: bold; -fx-alignment: center;");
            }
        });

        TableView<Employee> tableView = new TableView<>();
        tableView.getColumns().addAll(idCol, nameCol, emailCol, posteCol, serviceIdCol, activeCol);
        tableView.setItems(employees);
        tableView.setPrefHeight(460);
        tableView.setStyle(
            "-fx-background-color: " + COLOR_WHITE + ";" +
            "-fx-background-radius: 8;" +
            "-fx-border-color: " + COLOR_BORDER + ";" +
            "-fx-border-radius: 8;"
        );
        tableView.setPlaceholder(new Label("Aucun employé trouvé."));
        return tableView;
    }

    private Button actionBtn(String text, String color) {
        Button btn = new Button(text);
        btn.setStyle(
            "-fx-background-color: " + color + ";" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 13px;" +
            "-fx-padding: 9 18 9 18;" +
            "-fx-background-radius: 6;" +
            "-fx-cursor: hand;"
        );
        return btn;
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING, message, ButtonType.OK);
        alert.setHeaderText(null);
        alert.showAndWait();
    }
}