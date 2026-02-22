package gestionnaire_taches.view.forms;

import gestionnaire_taches.view.TaskListView;
import gestionnaire_taches.dao.impl.EmployeeDAOImpl;
import gestionnaire_taches.dao.impl.ServiceDAOImpl;
import gestionnaire_taches.dao.impl.TaskDAOImpl;
import gestionnaire_taches.model.*;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.GridPane;

import java.util.List;

public class TaskFormView {

    private Task existingTask; // null = création

    public TaskFormView() {
        this.existingTask = null;
    }

    public TaskFormView(Task task) {
        this.existingTask = task;
    }

    public VBox getView() {
        VBox formContainer = new VBox(15);
        formContainer.setPadding(new Insets(20));
        formContainer.setStyle("-fx-background-color: #ecf0f1;");

        boolean isEdit = existingTask != null;
        Label titleLabel = new Label(isEdit ? "Modifier la Tâche" : "Nouvelle Tâche");
        titleLabel.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        GridPane formGrid = new GridPane();
        formGrid.setHgap(15);
        formGrid.setVgap(12);
        formGrid.setPadding(new Insets(20));
        formGrid.setStyle("-fx-background-color: white; -fx-background-radius: 8;");

        TextField titleField = new TextField();
        titleField.setPromptText("Titre de la tâche");
        titleField.setPrefWidth(300);

        TextArea descriptionArea = new TextArea();
        descriptionArea.setPrefRowCount(4);
        descriptionArea.setPrefWidth(300);
        descriptionArea.setPromptText("Description détaillée");

        // Employés depuis la DB
        List<Employee> employees = new EmployeeDAOImpl().findAll();
        ComboBox<Employee> employeeCombo = new ComboBox<>();
        employeeCombo.setConverter(new javafx.util.StringConverter<>() {
            public String toString(Employee e) { return e == null ? "" : e.getNom() + " (#" + e.getId() + ")"; }
            public Employee fromString(String s) { return null; }
        });
        employeeCombo.setItems(FXCollections.observableArrayList(employees));
        employeeCombo.setPrefWidth(300);

        // Services depuis la DB
        List<Service> services = new ServiceDAOImpl().findAll();
        ComboBox<Service> serviceCombo = new ComboBox<>();
        serviceCombo.setConverter(new javafx.util.StringConverter<>() {
            public String toString(Service s) { return s == null ? "" : s.getNom() + " (#" + s.getId() + ")"; }
            public Service fromString(String s) { return null; }
        });
        serviceCombo.setItems(FXCollections.observableArrayList(services));
        serviceCombo.setPrefWidth(300);

        ComboBox<Priority> priorityCombo = new ComboBox<>();
        priorityCombo.setItems(FXCollections.observableArrayList(Priority.values()));
        priorityCombo.setPrefWidth(300);

        ComboBox<TaskStatus> statusCombo = new ComboBox<>();
        statusCombo.setItems(FXCollections.observableArrayList(TaskStatus.values()));
        statusCombo.setPrefWidth(300);

        DatePicker deadlinePicker = new DatePicker();

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

        int row = 0;
        formGrid.add(new Label("Titre :"), 0, row);       formGrid.add(titleField, 1, row++);
        formGrid.add(new Label("Description :"), 0, row); formGrid.add(descriptionArea, 1, row++);
        formGrid.add(new Label("Employé :"), 0, row);     formGrid.add(employeeCombo, 1, row++);
        formGrid.add(new Label("Service :"), 0, row);     formGrid.add(serviceCombo, 1, row++);
        formGrid.add(new Label("Priorité :"), 0, row);    formGrid.add(priorityCombo, 1, row++);
        formGrid.add(new Label("Statut :"), 0, row);      formGrid.add(statusCombo, 1, row++);
        formGrid.add(new Label("Date limite :"), 0, row); formGrid.add(deadlinePicker, 1, row++);

        Button saveButton   = new Button(isEdit ? "Modifier" : "Créer");
        Button cancelButton = new Button("Annuler");
        Button resetButton  = new Button("Réinitialiser");

        saveButton.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white; -fx-cursor: hand;");
        cancelButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-cursor: hand;");
        resetButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-cursor: hand;");

        saveButton.setOnAction(e -> {
            String titre = titleField.getText().trim();
            String desc  = descriptionArea.getText().trim();
            Employee emp = employeeCombo.getValue();
            Service svc  = serviceCombo.getValue();
            Priority priority = priorityCombo.getValue();
            TaskStatus status = statusCombo.getValue();

            if (titre.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Champ manquant", "Le titre est obligatoire.");
                return;
            }
            if (emp == null || svc == null || priority == null || status == null) {
                showAlert(Alert.AlertType.ERROR, "Champs manquants", "Veuillez remplir tous les champs obligatoires.");
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
                Task task = new Task(titre, desc, emp.getId(), svc.getId(), deadlinePicker.getValue(), priority, status);
                dao.save(task);
                showAlert(Alert.AlertType.INFORMATION, "Succès", "Tâche créée avec succès !");
            }

            List<Task> list = dao.findAll();
            gestionnaire_taches.Main.getMainLayout().setCenter(
                new TaskListView(FXCollections.observableArrayList(list)).getView());
        });

        cancelButton.setOnAction(e -> {
            List<Task> list = new TaskDAOImpl().findAll();
            gestionnaire_taches.Main.getMainLayout().setCenter(
                new TaskListView(FXCollections.observableArrayList(list)).getView());
        });

        resetButton.setOnAction(e -> {
            titleField.clear(); descriptionArea.clear(); employeeCombo.setValue(null);
            serviceCombo.setValue(null); priorityCombo.setValue(null);
            statusCombo.setValue(null); deadlinePicker.setValue(null);
        });

        HBox buttonBox = new HBox(10, saveButton, cancelButton, resetButton);
        buttonBox.setAlignment(Pos.CENTER);

        formContainer.getChildren().addAll(titleLabel, formGrid, buttonBox);
        return formContainer;
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}