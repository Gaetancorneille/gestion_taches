package gestionnaire_taches.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.collections.FXCollections;

public class EmployeeFormView {

    public VBox getView() {
        VBox formContainer = new VBox(15);
        formContainer.setPadding(new Insets(20));

        // Title
        Label titleLabel = new Label("Formulaire d'Employé");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        // Form grid
        GridPane formGrid = new GridPane();
        formGrid.setHgap(10);
        formGrid.setVgap(10);

        // Fields
        Label nameLabel = new Label("Nom:");
        TextField nameField = new TextField();
        nameField.setPrefWidth(300);

        Label emailLabel = new Label("Email:");
        TextField emailField = new TextField();
        emailField.setPrefWidth(300);

        Label passwordLabel = new Label("Mot de passe:");
        PasswordField passwordField = new PasswordField();
        passwordField.setPrefWidth(300);

        Label serviceLabel = new Label("ID Service:");
        ComboBox<Integer> serviceCombo = new ComboBox<>();
        serviceCombo.setItems(FXCollections.observableArrayList(1, 2, 3, 4, 5));

        Label posteLabel = new Label("Poste:");
        TextField posteField = new TextField();
        posteField.setPrefWidth(200);

        Label embaucheLabel = new Label("Date d'embauche:");
        DatePicker embauchePicker = new DatePicker();

        // Add fields to grid
        int row = 0;
        formGrid.add(nameLabel, 0, row);
        formGrid.add(nameField, 1, row++);
        
        formGrid.add(emailLabel, 0, row);
        formGrid.add(emailField, 1, row++);
        
        formGrid.add(passwordLabel, 0, row);
        formGrid.add(passwordField, 1, row++);
        
        formGrid.add(serviceLabel, 0, row);
        formGrid.add(serviceCombo, 1, row++);
        
        formGrid.add(posteLabel, 0, row);
        formGrid.add(posteField, 1, row++);
        
        formGrid.add(embaucheLabel, 0, row);
        formGrid.add(embauchePicker, 1, row++);

        // Buttons
        HBox buttonBox = new HBox(10);
        Button saveButton = new Button("Sauvegarder");
        Button cancelButton = new Button("Annuler");
        Button resetButton = new Button("Réinitialiser");
        
        saveButton.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white;");
        cancelButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white;");
        resetButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");

        buttonBox.getChildren().addAll(saveButton, cancelButton, resetButton);
        buttonBox.setAlignment(Pos.CENTER);

        formContainer.getChildren().addAll(titleLabel, formGrid, buttonBox);

        return formContainer;
    }
}