package gestionnaire_taches.view;

import gestionnaire_taches.dao.impl.TaskDAOImpl;
import gestionnaire_taches.model.Priority;
import gestionnaire_taches.model.Task;
import gestionnaire_taches.model.TaskStatus;
import gestionnaire_taches.view.forms.TaskDetailsView;
import gestionnaire_taches.view.forms.TaskFormView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.Optional;

public class TaskListView {

    private static final String COLOR_BG     = "#F5F7FA";
    private static final String COLOR_WHITE  = "#FFFFFF";
    private static final String COLOR_TITLE  = "#1E2A3A";
    private static final String COLOR_BORDER = "#E0E6ED";

    private ObservableList<Task> tasks;

    public TaskListView(ObservableList<Task> tasks) {
        this.tasks = tasks != null ? tasks : FXCollections.observableArrayList();
    }

    public VBox getView() {
        VBox container = new VBox(20);
        container.setPadding(new Insets(30));
        container.setStyle("-fx-background-color: " + COLOR_BG + ";");

        // ── En-tête ──────────────────────────────────────────────────────
        HBox header = new HBox();
        header.setAlignment(Pos.CENTER_LEFT);

        Label titleLabel = new Label("✅  Tâches");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        titleLabel.setStyle("-fx-text-fill: " + COLOR_TITLE + ";");

        Region spacer = new Region();
        HBox.setHgrow(spacer, javafx.scene.layout.Priority.ALWAYS);

        Button addButton = actionBtn("➕  Ajouter", "#1565C0");
        addButton.setOnAction(e -> {
            AppShell shell = gestionnaire_taches.Main.getAppShell();
            if (shell != null) {
                shell.navigateTo(new TaskFormView().getView());
            }
        });

        header.getChildren().addAll(titleLabel, spacer, addButton);

        // ── Tableau ───────────────────────────────────────────────────────
        TableView<Task> tableView = createTaskTable();

        // Double-clic → détails
        tableView.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                Task selected = tableView.getSelectionModel().getSelectedItem();
                if (selected != null) {
                    AppShell shell = gestionnaire_taches.Main.getAppShell();
                    if (shell != null) {
                        shell.navigateTo(new TaskDetailsView(selected).getView());
                    }
                }
            }
        });

        // ── Barre d'actions ───────────────────────────────────────────────
        Button editButton    = actionBtn("✏️  Modifier",     "#E65100");
        Button deleteButton  = actionBtn("🗑️  Supprimer",    "#C62828");
        Button detailsButton = actionBtn("🔍  Voir détails", "#1565C0");

        editButton.setOnAction(e -> {
            Task selected = tableView.getSelectionModel().getSelectedItem();
            if (selected == null) { showAlert("Veuillez sélectionner une tâche à modifier."); return; }
            AppShell shell = gestionnaire_taches.Main.getAppShell();
            if (shell != null) {
                shell.navigateTo(new TaskFormView(selected).getView());
            }
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
            AppShell shell = gestionnaire_taches.Main.getAppShell();
            if (shell != null) {
                shell.navigateTo(new TaskDetailsView(selected).getView());
            }
        });

        HBox actionBar = new HBox(10, editButton, deleteButton, detailsButton);
        actionBar.setAlignment(Pos.CENTER_LEFT);

        container.getChildren().addAll(header, tableView, actionBar);
        return container;
    }

    private TableView<Task> createTaskTable() {
        TableColumn<Task, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(data ->
            javafx.beans.binding.Bindings.createObjectBinding(() -> data.getValue().getId()));
        idCol.setPrefWidth(50);

        TableColumn<Task, String> titleCol = new TableColumn<>("Titre");
        titleCol.setCellValueFactory(data ->
            javafx.beans.binding.Bindings.createStringBinding(() -> data.getValue().getTitre()));
        titleCol.setPrefWidth(200);

        TableColumn<Task, String> descCol = new TableColumn<>("Description");
        descCol.setCellValueFactory(data ->
            javafx.beans.binding.Bindings.createStringBinding(() -> data.getValue().getDescription()));
        descCol.setPrefWidth(180);

        TableColumn<Task, Integer> empIdCol = new TableColumn<>("Employé");
        empIdCol.setCellValueFactory(data ->
            javafx.beans.binding.Bindings.createObjectBinding(() -> data.getValue().getEmployeeId()));
        empIdCol.setPrefWidth(70);

        TableColumn<Task, Integer> servIdCol = new TableColumn<>("Service");
        servIdCol.setCellValueFactory(data ->
            javafx.beans.binding.Bindings.createObjectBinding(() -> data.getValue().getServiceId()));
        servIdCol.setPrefWidth(70);

        TableColumn<Task, TaskStatus> statusCol = new TableColumn<>("Statut");
        statusCol.setCellValueFactory(data ->
            javafx.beans.binding.Bindings.createObjectBinding(() -> data.getValue().getStatut()));
        statusCol.setPrefWidth(110);

        // Cellule colorée selon le statut
        statusCol.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(TaskStatus item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) { setText(null); setStyle(""); return; }
                setText(item.toString());
                String color = switch (item.toString().toUpperCase()) {
                    case "TERMINEE", "TERMINÉ", "DONE", "COMPLETED" -> "#2E7D32";
                    case "EN_COURS", "EN COURS", "IN_PROGRESS"      -> "#1565C0";
                    case "EN_ATTENTE", "EN ATTENTE", "PENDING"      -> "#E65100";
                    default -> "#555555";
                };
                setStyle("-fx-text-fill: " + color + "; -fx-font-weight: bold;");
            }
        });

        TableColumn<Task, Priority> priorityCol = new TableColumn<>("Priorité");
        priorityCol.setCellValueFactory(data ->
            javafx.beans.binding.Bindings.createObjectBinding(() -> data.getValue().getPriorite()));
        priorityCol.setPrefWidth(90);

        // Cellule colorée selon la priorité
        priorityCol.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(Priority item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) { setText(null); setStyle(""); return; }
                setText(item.toString());
                String color = switch (item.toString().toUpperCase()) {
                    case "HAUTE", "HIGH"     -> "#C62828";
                    case "MOYENNE", "MEDIUM" -> "#E65100";
                    case "BASSE", "LOW"      -> "#2E7D32";
                    default -> "#555555";
                };
                setStyle("-fx-text-fill: " + color + "; -fx-font-weight: bold;");
            }
        });

        TableView<Task> tableView = new TableView<>();
        tableView.getColumns().addAll(idCol, titleCol, descCol, empIdCol, servIdCol, statusCol, priorityCol);
        tableView.setItems(tasks);
        tableView.setPrefHeight(430);
        tableView.setStyle(
            "-fx-background-color: " + COLOR_WHITE + ";" +
            "-fx-background-radius: 8;" +
            "-fx-border-color: " + COLOR_BORDER + ";" +
            "-fx-border-radius: 8;"
        );
        tableView.setPlaceholder(new Label("Aucune tâche trouvée."));
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