package gestionnaire_taches.view.forms;

import gestionnaire_taches.view.ServiceListView;
import gestionnaire_taches.dao.impl.AdministratorDAOImpl;
import gestionnaire_taches.dao.impl.ServiceDAOImpl;
import gestionnaire_taches.model.Administrator;
import gestionnaire_taches.model.Service;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.util.List;

public class ServiceFormView {

    private Service existingService; // null = création, non-null = modification

    public ServiceFormView() {
        this.existingService = null;
    }

    public ServiceFormView(Service service) {
        this.existingService = service;
    }

    public VBox getView() {
        VBox formContainer = new VBox(15);
        formContainer.setPadding(new Insets(20));
        formContainer.setStyle("-fx-background-color: #ecf0f1;");

        // Titre
        boolean isEdit = existingService != null;
        Label titleLabel = new Label(isEdit ? "Modifier le Service" : "Nouveau Service");
        titleLabel.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        // Formulaire
        GridPane formGrid = new GridPane();
        formGrid.setHgap(15);
        formGrid.setVgap(12);
        formGrid.setPadding(new Insets(20));
        formGrid.setStyle("-fx-background-color: white; -fx-background-radius: 8;");

        TextField nameField = new TextField();
        nameField.setPrefWidth(300);
        nameField.setPromptText("Nom du service");

        TextArea descriptionArea = new TextArea();
        descriptionArea.setPrefRowCount(4);
        descriptionArea.setPrefWidth(300);
        descriptionArea.setPromptText("Description du service");

        // Chargement des admins pour le ComboBox
        List<Administrator> admins = new AdministratorDAOImpl().findAll();
        ComboBox<Administrator> adminCombo = new ComboBox<>();
        adminCombo.setConverter(new javafx.util.StringConverter<>() {
            public String toString(Administrator a) { return a == null ? "" : a.getNom() + " (#" + a.getId() + ")"; }
            public Administrator fromString(String s) { return null; }
        });
        adminCombo.setItems(FXCollections.observableArrayList(admins));
        adminCombo.setPrefWidth(300);

        // Pré-remplissage en mode édition
        if (isEdit) {
            nameField.setText(existingService.getNom());
            descriptionArea.setText(existingService.getDescription());
            admins.stream()
                .filter(a -> a.getId() == existingService.getAdministratorId())
                .findFirst()
                .ifPresent(adminCombo::setValue);
        }

        int row = 0;
        formGrid.add(new Label("Nom :"), 0, row);        formGrid.add(nameField, 1, row++);
        formGrid.add(new Label("Description :"), 0, row); formGrid.add(descriptionArea, 1, row++);
        formGrid.add(new Label("Administrateur :"), 0, row); formGrid.add(adminCombo, 1, row++);

        // Boutons
        Button saveButton   = new Button(isEdit ? "Modifier" : "Créer");
        Button cancelButton = new Button("Annuler");
        Button resetButton  = new Button("Réinitialiser");

        saveButton.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white; -fx-cursor: hand;");
        cancelButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-cursor: hand;");
        resetButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-cursor: hand;");

        // Action Sauvegarder
        saveButton.setOnAction(e -> {
            String nom = nameField.getText().trim();
            String desc = descriptionArea.getText().trim();
            Administrator admin = adminCombo.getValue();

            if (nom.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Champ manquant", "Le nom du service est obligatoire.");
                return;
            }
            if (admin == null) {
                showAlert(Alert.AlertType.ERROR, "Champ manquant", "Veuillez sélectionner un administrateur.");
                return;
            }

            ServiceDAOImpl dao = new ServiceDAOImpl();

            if (isEdit) {
                existingService.setNom(nom);
                existingService.setDescription(desc);
                existingService.setAdministratorId(admin.getId());
                dao.update(existingService);
                showAlert(Alert.AlertType.INFORMATION, "Succès", "Service modifié avec succès !");
            } else {
                Service newService = new Service(nom, desc, admin.getId());
                dao.save(newService);
                showAlert(Alert.AlertType.INFORMATION, "Succès", "Service créé avec succès !");
                nameField.clear();
                descriptionArea.clear();
                adminCombo.setValue(null);
            }

            // Retour à la liste
            List<Service> list = dao.findAll();
            gestionnaire_taches.Main.getMainLayout().setCenter(
                new gestionnaire_taches.view.ServiceListView(FXCollections.observableArrayList(list)).getView());
        });

        // Action Annuler
        cancelButton.setOnAction(e -> {
            List<Service> list = new ServiceDAOImpl().findAll();
            gestionnaire_taches.Main.getMainLayout().setCenter(
                new gestionnaire_taches.view.ServiceListView(FXCollections.observableArrayList(list)).getView());
        });

        // Action Réinitialiser
        resetButton.setOnAction(e -> {
            nameField.clear();
            descriptionArea.clear();
            adminCombo.setValue(null);
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