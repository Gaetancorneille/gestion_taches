package gestionnaire_taches.view.forms;

import gestionnaire_taches.dao.impl.SubtaskDAOImpl;
import gestionnaire_taches.dao.impl.TaskDAOImpl;
import gestionnaire_taches.model.Subtask;
import gestionnaire_taches.model.Task;
import gestionnaire_taches.model.TaskStatus;
import gestionnaire_taches.view.AppShell;
import gestionnaire_taches.view.TaskListView;
import gestionnaire_taches.view.forms.SubtaskFormView;
import gestionnaire_taches.view.AppShell;
import gestionnaire_taches.view.TaskListView;
import gestionnaire_taches.view.forms.TaskFormView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.List;
import java.util.Optional;

import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

public class TaskDetailsView {

    private static final String COLOR_BG     = "#F5F7FA";
    private static final String COLOR_WHITE  = "#FFFFFF";
    private static final String COLOR_TITLE  = "#1E2A3A";
    private static final String COLOR_LABEL  = "#34495E";
    private static final String COLOR_VALUE  = "#555555";
    private static final String COLOR_BORDER = "#E0E6ED";

    private final Task task;
    private final ObservableList<Subtask> subtasks;

    public TaskDetailsView(Task task) {
        this.task = task;
        this.subtasks = FXCollections.observableArrayList();
        if (task != null) {
            List<Subtask> loaded = new SubtaskDAOImpl().findByTaskId(task.getId());
            if (loaded != null) subtasks.addAll(loaded);
        }
    }

    public VBox getView() {
        VBox container = new VBox(22);
        container.setPadding(new Insets(30));
        container.setStyle("-fx-background-color: " + COLOR_BG + ";");

        // ── En-tête ──────────────────────────────────────────────────────
        HBox header = new HBox(15);
        header.setAlignment(Pos.CENTER_LEFT);

        Button backButton = actionBtn("⬅  Retour", "#546E7A");
        backButton.setOnAction(e -> {
            List<Task> list = new TaskDAOImpl().findAll();
            AppShell shell = gestionnaire_taches.Main.getAppShell();
            if (shell != null) {
                shell.navigateTo(new TaskListView(FXCollections.observableArrayList(list)).getView());
            }
        });

        Label titleLabel = new Label("🔍  Détails de la tâche");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        titleLabel.setStyle("-fx-text-fill: " + COLOR_TITLE + ";");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button editTaskButton = actionBtn("✏️  Modifier la tâche", "#E65100");
        editTaskButton.setOnAction(e -> {
            AppShell shell = gestionnaire_taches.Main.getAppShell();
            if (shell != null) {
                shell.navigateTo(new TaskFormView(task).getView());
            }
        });

        header.getChildren().addAll(backButton, titleLabel, spacer, editTaskButton);

        // ── Fiche de la tâche ─────────────────────────────────────────────
        VBox detailsCard = new VBox(0);
        detailsCard.setStyle(
            "-fx-background-color: " + COLOR_WHITE + ";" +
            "-fx-background-radius: 10;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.07), 8, 0, 0, 2);"
        );

        GridPane detailsGrid = new GridPane();
        detailsGrid.setHgap(30);
        detailsGrid.setVgap(14);
        detailsGrid.setPadding(new Insets(25, 30, 25, 30));

        int row = 0;
        addDetailRow(detailsGrid, row++, "Titre",       val(task, () -> task.getTitre()));
        addDetailRow(detailsGrid, row++, "Description", val(task, () -> task.getDescription()));
        addDetailRow(detailsGrid, row++, "Statut",      val(task, () -> task.getStatut().toString()));
        addDetailRow(detailsGrid, row++, "Priorité",    val(task, () -> task.getPriorite().toString()));
        addDetailRow(detailsGrid, row++, "Date limite", val(task, () -> task.getDateLimite() != null ? task.getDateLimite().toString() : "Non définie"));
        addDetailRow(detailsGrid, row++, "ID Employé",  val(task, () -> String.valueOf(task.getEmployeeId())));
        addDetailRow(detailsGrid, row++, "ID Service",  val(task, () -> String.valueOf(task.getServiceId())));

        detailsCard.getChildren().add(detailsGrid);

        // ── Section sous-tâches ───────────────────────────────────────────
        HBox subtaskHeader = new HBox(15);
        subtaskHeader.setAlignment(Pos.CENTER_LEFT);

        Label subtasksLabel = new Label("📌  Sous-tâches");
        subtasksLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        subtasksLabel.setStyle("-fx-text-fill: " + COLOR_TITLE + ";");

        Region subtaskSpacer = new Region();
        HBox.setHgrow(subtaskSpacer, Priority.ALWAYS);

        Button addSubtaskBtn = actionBtn("➕  Ajouter", "#1565C0");
        addSubtaskBtn.setOnAction(e -> {
            AppShell shell = gestionnaire_taches.Main.getAppShell();
            if (shell != null) {
                shell.navigateTo(new SubtaskFormView(task).getView());
            }
        });

        subtaskHeader.getChildren().addAll(subtasksLabel, subtaskSpacer, addSubtaskBtn);

        TableView<Subtask> subtasksTable = createSubtasksTable();

        Button editSubtaskBtn = actionBtn("✏️  Modifier",  "#E65100");
        Button delSubtaskBtn  = actionBtn("🗑️  Supprimer", "#C62828");

        editSubtaskBtn.setOnAction(e -> {
            Subtask selected = subtasksTable.getSelectionModel().getSelectedItem();
            if (selected == null) { showAlert("Veuillez sélectionner une sous-tâche à modifier."); return; }
            AppShell shell = gestionnaire_taches.Main.getAppShell();
            if (shell != null) {
                shell.navigateTo(new SubtaskFormView(selected, task).getView());
            }
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

        HBox subtaskActionBar = new HBox(10, editSubtaskBtn, delSubtaskBtn);

        container.getChildren().addAll(
            header,
            detailsCard,
            subtaskHeader,
            subtasksTable,
            subtaskActionBar
        );

        return container;
    }

    // ── Helpers ──────────────────────────────────────────────────────────────

    private void addDetailRow(GridPane grid, int row, String label, String value) {
        Label lbl = new Label(label);
        lbl.setStyle(
            "-fx-font-weight: bold;" +
            "-fx-text-fill: " + COLOR_LABEL + ";" +
            "-fx-font-size: 13px;"
        );
        lbl.setMinWidth(120);

        Label val = new Label(value);
        val.setStyle("-fx-text-fill: " + COLOR_VALUE + "; -fx-font-size: 13px;");
        val.setWrapText(true);

        grid.add(lbl, 0, row);
        grid.add(val, 1, row);
    }

    /** Récupère une valeur de la tâche de façon null-safe */
    private String val(Task t, java.util.function.Supplier<String> supplier) {
        if (t == null) return "N/A";
        try { return supplier.get(); } catch (Exception e) { return "N/A"; }
    }

    private TableView<Subtask> createSubtasksTable() {
        TableColumn<Subtask, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(data ->
            javafx.beans.binding.Bindings.createObjectBinding(() -> data.getValue().getId()));
        idCol.setPrefWidth(50);

        TableColumn<Subtask, String> titleCol = new TableColumn<>("Titre");
        titleCol.setCellValueFactory(data ->
            javafx.beans.binding.Bindings.createStringBinding(() -> data.getValue().getTitre()));
        titleCol.setPrefWidth(220);

        TableColumn<Subtask, String> descCol = new TableColumn<>("Description");
        descCol.setCellValueFactory(data ->
            javafx.beans.binding.Bindings.createStringBinding(() -> data.getValue().getDescription()));
        descCol.setPrefWidth(220);

        TableColumn<Subtask, Integer> orderCol = new TableColumn<>("Ordre");
        orderCol.setCellValueFactory(data ->
            javafx.beans.binding.Bindings.createObjectBinding(() -> data.getValue().getOrdre()));
        orderCol.setPrefWidth(60);

        TableColumn<Subtask, TaskStatus> statusCol = new TableColumn<>("Statut");
        statusCol.setCellValueFactory(data ->
            javafx.beans.binding.Bindings.createObjectBinding(() -> data.getValue().getStatut()));
        statusCol.setPrefWidth(110);

        TableView<Subtask> tableView = new TableView<>();
        tableView.getColumns().addAll(idCol, titleCol, descCol, orderCol, statusCol);
        tableView.setItems(subtasks);
        tableView.setPrefHeight(200);
        tableView.setStyle(
            "-fx-background-color: " + COLOR_WHITE + ";" +
            "-fx-background-radius: 8;" +
            "-fx-border-color: " + COLOR_BORDER + ";" +
            "-fx-border-radius: 8;"
        );
        tableView.setPlaceholder(new Label("Aucune sous-tâche pour cette tâche."));
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