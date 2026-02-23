package gestionnaire_taches.view.forms;

import java.util.List;

import gestionnaire_taches.dao.impl.EmployeeDAOImpl;
import gestionnaire_taches.dao.impl.ServiceDAOImpl;
import gestionnaire_taches.dao.impl.TaskDAOImpl;
import gestionnaire_taches.model.Employee;
import gestionnaire_taches.model.Priority;
import gestionnaire_taches.model.Service;
import gestionnaire_taches.model.Task;
import gestionnaire_taches.model.TaskStatus;
import gestionnaire_taches.view.AppShell;
import gestionnaire_taches.view.TaskListView;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Formulaire de création / modification d'une tâche.
 *
 * Navigation corrigée : utilise Main.getAppShell().navigateTo(...)
 * au lieu de Main.getMainLayout().setCenter(...).
 * Ainsi la sidebar et le footer restent toujours visibles.
 */
public class TaskFormView {

    private static final String COLOR_BG     = "#F5F7FA";
    private static final String COLOR_WHITE  = "#FFFFFF";
    private static final String COLOR_TITLE  = "#1E2A3A";
    private static final String COLOR_LABEL  = "#34495E";
    private static final String COLOR_BORDER = "#D5DCE4";

    private Task existingTask;

    public TaskFormView() {
        this.existingTask = null;
    }

    public TaskFormView(Task task) {
        this.existingTask = task;
    }

    public VBox getView() {
        boolean isEdit = existingTask != null;

        VBox formContainer = new VBox(20);
        formContainer.setPadding(new Insets(30));
        formContainer.setStyle("-fx-background-color: " + COLOR_BG + ";");

        // ── Titre de la page ─────────────────────────────────────────────
        Label pageTitle = new Label(isEdit ? "✏️  Modifier la tâche" : "➕  Nouvelle tâche");
        pageTitle.setStyle(
            "-fx-font-size: 20px;" +
            "-fx-font-weight: bold;" +
            "-fx-text-fill: " + COLOR_TITLE + ";"
        );

        // ── Carte formulaire ─────────────────────────────────────────────
        VBox formCard = new VBox(0);
        formCard.setStyle(
            "-fx-background-color: " + COLOR_WHITE + ";" +
            "-fx-background-radius: 10;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.07), 8, 0, 0, 2);"
        );

        GridPane formGrid = new GridPane();
        formGrid.setHgap(20);
        formGrid.setVgap(15);
        formGrid.setPadding(new Insets(25, 30, 25, 30));

        // Champs
        TextField titleField = styledTextField("Titre de la tâche");

        TextArea descriptionArea = new TextArea();
        descriptionArea.setPrefRowCount(4);
        descriptionArea.setPrefWidth(380);
        descriptionArea.setPromptText("Description détaillée (optionnelle)");
        descriptionArea.setStyle(fieldStyle());

        // ComboBox employés
        List<Employee> employees = new EmployeeDAOImpl().findAll();
        ComboBox<Employee> employeeCombo = new ComboBox<>();
        employeeCombo.setConverter(new javafx.util.StringConverter<>() {
            public String toString(Employee e) { return e == null ? "" : e.getNom() + " (#" + e.getId() + ")"; }
            public Employee fromString(String s) { return null; }
        });
        employeeCombo.setItems(FXCollections.observableArrayList(employees));
        employeeCombo.setPrefWidth(380);
        employeeCombo.setStyle(fieldStyle());

        // ComboBox services
        List<Service> services = new ServiceDAOImpl().findAll();
        ComboBox<Service> serviceCombo = new ComboBox<>();
        serviceCombo.setConverter(new javafx.util.StringConverter<>() {
            public String toString(Service s) { return s == null ? "" : s.getNom() + " (#" + s.getId() + ")"; }
            public Service fromString(String s) { return null; }
        });
        serviceCombo.setItems(FXCollections.observableArrayList(services));
        serviceCombo.setPrefWidth(380);
        serviceCombo.setStyle(fieldStyle());

        ComboBox<Priority> priorityCombo = new ComboBox<>();
        priorityCombo.setItems(FXCollections.observableArrayList(Priority.values()));
        priorityCombo.setPrefWidth(380);
        priorityCombo.setStyle(fieldStyle());

        ComboBox<TaskStatus> statusCombo = new ComboBox<>();
        statusCombo.setItems(FXCollections.observableArrayList(TaskStatus.values()));
        statusCombo.setPrefWidth(380);
        statusCombo.setStyle(fieldStyle());

        DatePicker deadlinePicker = new DatePicker();
        deadlinePicker.setPrefWidth(380);
        deadlinePicker.setStyle(fieldStyle());

        // Pré-remplissage en mode édition
        if (isEdit) {
            titleField.setText(existingTask.getTitre());
            descriptionArea.setText(existingTask.getDescription());
            priorityCombo.setValue(existingTask.getPriorite());
            statusCombo.setValue(existingTask.getStatut());
            if (existingTask.getDateLimite() != null)
                deadlinePicker.setValue(existingTask.getDateLimite());
            employees.stream().filter(emp -> emp.getId() == existingTask.getEmployeeId())
                .findFirst().ifPresent(employeeCombo::setValue);
            services.stream().filter(s -> s.getId() == existingTask.getServiceId())
                .findFirst().ifPresent(serviceCombo::setValue);
        }

        // Disposition du formulaire
        int row = 0;
        formGrid.add(fieldLabel("Titre *"),       0, row); formGrid.add(titleField,     1, row++);
        formGrid.add(fieldLabel("Description"),   0, row); formGrid.add(descriptionArea, 1, row++);
        formGrid.add(fieldLabel("Employé *"),     0, row); formGrid.add(employeeCombo,  1, row++);
        formGrid.add(fieldLabel("Service *"),     0, row); formGrid.add(serviceCombo,   1, row++);
        formGrid.add(fieldLabel("Priorité *"),    0, row); formGrid.add(priorityCombo,  1, row++);
        formGrid.add(fieldLabel("Statut *"),      0, row); formGrid.add(statusCombo,    1, row++);
        formGrid.add(fieldLabel("Date limite"),   0, row); formGrid.add(deadlinePicker, 1, row++);

        // ── Séparateur ───────────────────────────────────────────────────
        Separator sep = new Separator();
        sep.setStyle("-fx-background-color: " + COLOR_BORDER + ";");

        // ── Boutons d'action ─────────────────────────────────────────────
        Button saveButton   = actionButton(isEdit ? "💾  Enregistrer" : "➕  Créer la tâche", "#1565C0");
        Button cancelButton = actionButton("✖  Annuler", "#E74C3C");
        Button resetButton  = actionButton("↺  Réinitialiser", "#7F8C8D");

        // ── Logique Enregistrer ──────────────────────────────────────────
        saveButton.setOnAction(e -> {
            String titre     = titleField.getText().trim();
            String desc      = descriptionArea.getText().trim();
            Employee emp     = employeeCombo.getValue();
            Service svc      = serviceCombo.getValue();
            Priority priority = priorityCombo.getValue();
            TaskStatus status = statusCombo.getValue();

            if (titre.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Champ manquant", "Le titre est obligatoire.");
                return;
            }
            if (emp == null || svc == null || priority == null || status == null) {
                showAlert(Alert.AlertType.ERROR, "Champs manquants",
                    "Veuillez remplir tous les champs obligatoires (marqués *).");
                return;
            }

            TaskDAOImpl dao = new TaskDAOImpl();
            if (isEdit) {
                existingTask.setTitre(titre);
                existingTask.setDescription(desc);
                existingTask.setEmployeeId(emp.getId());
                existingTask.setServiceId(svc.getId());
                existingTask.setPriorite(priority);
                existingTask.setStatut(status);
                existingTask.setDateLimite(deadlinePicker.getValue());
                dao.update(existingTask);
                showAlert(Alert.AlertType.INFORMATION, "Succès", "Tâche modifiée avec succès !");
            } else {
                Task task = new Task(titre, desc, emp.getId(), svc.getId(),
                    deadlinePicker.getValue(), priority, status);
                dao.save(task);
                showAlert(Alert.AlertType.INFORMATION, "Succès", "Tâche créée avec succès !");
            }

            navigateToTaskList(dao);
        });

        // ── Logique Annuler — CORRIGÉ : sidebar reste visible ────────────
        cancelButton.setOnAction(e -> navigateToTaskList(new TaskDAOImpl()));

        // ── Logique Réinitialiser ────────────────────────────────────────
        resetButton.setOnAction(e -> {
            titleField.clear();
            descriptionArea.clear();
            employeeCombo.setValue(null);
            serviceCombo.setValue(null);
            priorityCombo.setValue(null);
            statusCombo.setValue(null);
            deadlinePicker.setValue(null);
        });

        HBox buttonBox = new HBox(12, saveButton, cancelButton, resetButton);
        buttonBox.setAlignment(Pos.CENTER_LEFT);
        buttonBox.setPadding(new Insets(20, 30, 20, 30));

        formCard.getChildren().addAll(formGrid, sep, buttonBox);
        formContainer.getChildren().addAll(pageTitle, formCard);
        return formContainer;
    }

    // ── Navigation ───────────────────────────────────────────────────────────

    /**
     * Retour à la liste des tâches via le shell persistant.
     * La sidebar reste visible car on ne recrée PAS le shell.
     */
    private void navigateToTaskList(TaskDAOImpl dao) {
        AppShell shell = gestionnaire_taches.Main.getAppShell();
        if (shell != null) {
            List<Task> list = dao.findAll();
            shell.navigateTo(new TaskListView(
                FXCollections.observableArrayList(list)).getView());
        }
    }

    // ── Helpers de style ─────────────────────────────────────────────────────

    private TextField styledTextField(String prompt) {
        TextField f = new TextField();
        f.setPromptText(prompt);
        f.setPrefWidth(380);
        f.setStyle(fieldStyle());
        return f;
    }

    private String fieldStyle() {
        return
            "-fx-background-color: #FAFBFC;" +
            "-fx-border-color: " + COLOR_BORDER + ";" +
            "-fx-border-radius: 5;" +
            "-fx-background-radius: 5;" +
            "-fx-padding: 8 10 8 10;" +
            "-fx-font-size: 13px;";
    }

    private Label fieldLabel(String text) {
        Label lbl = new Label(text);
        lbl.setStyle(
            "-fx-text-fill: " + COLOR_LABEL + ";" +
            "-fx-font-size: 13px;" +
            "-fx-font-weight: bold;"
        );
        lbl.setMinWidth(120);
        return lbl;
    }

    private Button actionButton(String text, String color) {
        Button btn = new Button(text);
        btn.setStyle(
            "-fx-background-color: " + color + ";" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 13px;" +
            "-fx-padding: 10 20 10 20;" +
            "-fx-background-radius: 6;" +
            "-fx-cursor: hand;"
        );
        return btn;
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}