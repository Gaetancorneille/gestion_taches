package gestionnaire_taches.view;

import gestionnaire_taches.controller.AuthenticationController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import gestionnaire_taches.model.User;

public class LoginView {

    private AuthenticationController authController;
    
    public LoginView() {
        this.authController = new AuthenticationController();
    }
    
    public VBox getView() {
        VBox loginBox = new VBox(20);
        loginBox.setAlignment(Pos.CENTER);
        loginBox.setPadding(new Insets(40));
        
        // Logo et titre
        Text title = new Text("Gestion de Tâches");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        
        Label subtitle = new Label("Connectez-vous pour accéder au système");
        subtitle.setFont(Font.font(14));
        
        // Formulaire de connexion
        GridPane form = new GridPane();
        form.setHgap(15);
        form.setVgap(15);
        form.setAlignment(Pos.CENTER);
        
        Label emailLabel = new Label("Email:");
        TextField emailField = new TextField();
        emailField.setPromptText("Votre email");

        Label passwordLabel = new Label("Mot de passe:");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Votre mot de passe");

        Label userTypeLabel = new Label("Type d'utilisateur:");
        ComboBox<String> userTypeBox = new ComboBox<>();
        userTypeBox.getItems().addAll("Administrateur", "Employé");
        userTypeBox.setValue("Administrateur");

        form.add(emailLabel, 0, 0);
        form.add(emailField, 1, 0);
        form.add(passwordLabel, 0, 1);
        form.add(passwordField, 1, 1);
        form.add(userTypeLabel, 0, 2);
        form.add(userTypeBox, 1, 2);

        // Boutons
        HBox buttonBox = new HBox(15);
        buttonBox.setAlignment(Pos.CENTER);

        Button loginBtn = new Button("Se connecter");
        loginBtn.setPrefWidth(120);
        loginBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        loginBtn.setOnAction(e -> {
            String email = emailField.getText().trim();
            String password = passwordField.getText();
            
            if (authController.login(email, password)) {
                // Récupérer l'utilisateur connecté
                gestionnaire_taches.model.User currentUser = authController.getCurrentUser();
                
                if (currentUser instanceof gestionnaire_taches.model.Administrator) {
                    // Rediriger vers le dashboard administrateur
                    gestionnaire_taches.Main.showAdminDashboard();
                } else if (currentUser instanceof gestionnaire_taches.model.Employee) {
                    // Rediriger vers le dashboard employé
                    gestionnaire_taches.Main.showEmployeeDashboard(currentUser.getNom());
                }
            } else {
                showAlert(Alert.AlertType.ERROR, "Erreur de connexion", "Email ou mot de passe incorrect.");
            }
        });

        Button registerBtn = new Button("S'inscrire");
        registerBtn.setPrefWidth(120);
        registerBtn.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");
        registerBtn.setOnAction(e -> {
            // Navigate to the register view
            gestionnaire_taches.Main.showRegisterView();
        });

        buttonBox.getChildren().addAll(loginBtn, registerBtn);

        loginBox.getChildren().addAll(title, subtitle, form, buttonBox);

        return loginBox;
    }
    
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}