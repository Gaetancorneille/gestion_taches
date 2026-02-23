package gestionnaire_taches.view.forms;

import gestionnaire_taches.view.AppShell;
import gestionnaire_taches.view.EmployeeListView;
import gestionnaire_taches.dao.impl.EmployeeDAOImpl;
import gestionnaire_taches.dao.impl.ServiceDAOImpl;
import gestionnaire_taches.model.Employee;
import gestionnaire_taches.model.Service;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.time.LocalDate;
import java.util.List;

public class EmployeeFormView {

    private Employee existingEmployee; // null = création

    public EmployeeFormView() {
        this.existingEmployee = null;
    }

    public EmployeeFormView(Employee employee) {
        this.existingEmployee = employee;
    }

    public VBox getView() {
        VBox formContainer = new VBox(15);
        formContainer.setPadding(new Insets(20));
        formContainer.setStyle("-fx-background-color: #ecf0f1;");

        boolean isEdit = existingEmployee != null;
        Label titleLabel = new Label(isEdit ? "Modifier l'Employé" : "Nouvel Employé");
        titleLabel.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        GridPane formGrid = new GridPane();
        formGrid.setHgap(15);
        formGrid.setVgap(12);
        formGrid.setPadding(new Insets(20));
        formGrid.setStyle("-fx-background-color: white; -fx-background-radius: 8;");

        TextField nameField = new TextField();
        nameField.setPromptText("Nom complet");
        nameField.setPrefWidth(300);

        TextField emailField = new TextField();
        emailField.setPromptText("adresse@email.com");
        emailField.setPrefWidth(300);

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText(isEdit ? "Laisser vide pour ne pas changer" : "Mot de passe");
        passwordField.setPrefWidth(300);

        TextField posteField = new TextField();
        posteField.setPromptText("Intitulé du poste");
        posteField.setPrefWidth(300);

        DatePicker embauchePicker = new DatePicker();

        // ComboBox services depuis la DB
        List<Service> services = new ServiceDAOImpl().findAll();
        ComboBox<Service> serviceCombo = new ComboBox<>();
        serviceCombo.setConverter(new javafx.util.StringConverter<>() {
            public String toString(Service s) { return s == null ? "" : s.getNom() + " (#" + s.getId() + ")"; }
            public Service fromString(String s) { return null; }
        });
        serviceCombo.setItems(FXCollections.observableArrayList(services));
        serviceCombo.setPrefWidth(300);

        // Pré-remplissage en mode édition
        if (isEdit) {
            nameField.setText(existingEmployee.getNom());
            emailField.setText(existingEmployee.getEmail());
            posteField.setText(existingEmployee.getPoste());
            if (existingEmployee.getDateEmbauche() != null)
                embauchePicker.setValue(existingEmployee.getDateEmbauche());
            services.stream()
                .filter(s -> s.getId() == existingEmployee.getServiceId())
                .findFirst()
                .ifPresent(serviceCombo::setValue);
        }

        int row = 0;
        formGrid.add(new Label("Nom :"), 0, row);             formGrid.add(nameField, 1, row++);
        formGrid.add(new Label("Email :"), 0, row);           formGrid.add(emailField, 1, row++);
        formGrid.add(new Label("Mot de passe :"), 0, row);    formGrid.add(passwordField, 1, row++);
        formGrid.add(new Label("Poste :"), 0, row);           formGrid.add(posteField, 1, row++);
        formGrid.add(new Label("Service :"), 0, row);         formGrid.add(serviceCombo, 1, row++);
        formGrid.add(new Label("Date d'embauche :"), 0, row); formGrid.add(embauchePicker, 1, row++);

        Button saveButton   = new Button(isEdit ? "Modifier" : "Créer");
        Button cancelButton = new Button("Annuler");
        Button resetButton  = new Button("Réinitialiser");

        saveButton.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white; -fx-cursor: hand;");
        cancelButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-cursor: hand;");
        resetButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-cursor: hand;");

        saveButton.setOnAction(e -> {
            String nom   = nameField.getText().trim();
            String email = emailField.getText().trim();
            String mdp   = passwordField.getText();
            String poste = posteField.getText().trim();
            Service service = serviceCombo.getValue();
            LocalDate embauche = embauchePicker.getValue();

            if (nom.isEmpty() || email.isEmpty() || poste.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Champs manquants", "Nom, email et poste sont obligatoires.");
                return;
            }
            if (!isEdit && mdp.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Champ manquant", "Le mot de passe est obligatoire à la création.");
                return;
            }
            if (service == null) {
                showAlert(Alert.AlertType.ERROR, "Champ manquant", "Veuillez sélectionner un service.");
                return;
            }

            EmployeeDAOImpl dao = new EmployeeDAOImpl();

            if (isEdit) {
                existingEmployee.setNom(nom);
                existingEmployee.setEmail(email);
                existingEmployee.setPoste(poste);
                existingEmployee.setServiceId(service.getId());
                if (embauche != null) existingEmployee.setDateEmbauche(embauche);
                dao.update(existingEmployee);
                // Mise à jour du mot de passe seulement si renseigné
                if (!mdp.isEmpty()) dao.updatePassword(existingEmployee.getId(), mdp);
                showAlert(Alert.AlertType.INFORMATION, "Succès", "Employé modifié avec succès !");
            } else {
                Employee emp = new Employee(nom, email, mdp, service.getId());
                emp.setPoste(poste);
                if (embauche != null) emp.setDateEmbauche(embauche);
                dao.save(emp);
                showAlert(Alert.AlertType.INFORMATION, "Succès", "Employé créé avec succès !");
            }

            List<Employee> list = dao.findAll();
            AppShell shell = gestionnaire_taches.Main.getAppShell();
            if (shell != null) {
                shell.navigateTo(new EmployeeListView(FXCollections.observableArrayList(list)).getView());
            }
        });

        cancelButton.setOnAction(e -> {
            List<Employee> list = new EmployeeDAOImpl().findAll();
            AppShell shell = gestionnaire_taches.Main.getAppShell();
            if (shell != null) {
                shell.navigateTo(new EmployeeListView(FXCollections.observableArrayList(list)).getView());
            }
        });

        resetButton.setOnAction(e -> {
            nameField.clear(); emailField.clear(); passwordField.clear();
            posteField.clear(); serviceCombo.setValue(null); embauchePicker.setValue(null);
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