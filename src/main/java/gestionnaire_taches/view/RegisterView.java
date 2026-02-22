package gestionnaire_taches.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.paint.Color;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;

/**
 * Vue pour l'inscription des nouveaux administrateurs
 * Permet aux super administrateurs de créer de nouveaux comptes administrateurs
 */
public class RegisterView {
    // Conteneur principal de l'interface
    private VBox view;
    
    // Champs de saisie pour les informations de l'administrateur
    private VBox nomField;
    private VBox emailField;
    private VBox passwordField;
    private VBox confirmPasswordField;
    private CheckBox isSuperAdminCheckBox;
    
    // Boutons pour valider ou annuler l'inscription
    private Button registerButton;
    private Button cancelButton;
    
    /**
     * Constructeur qui crée l'interface d'inscription
     */
    public RegisterView() {
        createView();
    }
    
    /**
     * Crée l'interface graphique complète
     */
    private void createView() {
        view = new VBox(20);
        view.setAlignment(Pos.CENTER);
        view.setPadding(new Insets(50));
        
        // Créer l'ombre pour l'effet visuel
        DropShadow shadow = new DropShadow();
        shadow.setRadius(10);
        shadow.setOffsetX(3);
        shadow.setOffsetY(3);
        shadow.setColor(Color.GRAY);
        
        // Titre de la page
        Label titleLabel = new Label("Inscription Administrateur");
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 24));
        titleLabel.setTextFill(Color.DARKBLUE);
        
        // Panneau du formulaire avec fond blanc
        VBox formPanel = new VBox(15);
        formPanel.setAlignment(Pos.CENTER);
        formPanel.setPadding(new Insets(30));
        formPanel.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(10), Insets.EMPTY)));
        formPanel.setEffect(shadow);
        
        // Créer les champs de saisie
        nomField = createTextField("Nom complet");
        emailField = createTextField("Adresse email");
        passwordField = createPasswordField("Mot de passe");
        confirmPasswordField = createPasswordField("Confirmer le mot de passe");
        
        // Case à cocher pour super administrateur
        isSuperAdminCheckBox = new CheckBox("Super Administrateur");
        isSuperAdminCheckBox.setFont(Font.font(14));
        isSuperAdminCheckBox.setTextFill(Color.DARKGRAY);
        
        // Créer les boutons
        registerButton = new Button("S'inscrire");
        registerButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10px 20px;");
        registerButton.setPrefWidth(120);
        
        cancelButton = new Button("Annuler");
        cancelButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10px 20px;");
        cancelButton.setPrefWidth(120);
        
        // Panneau des boutons
        HBox buttonPanel = new HBox(15);
        buttonPanel.setAlignment(Pos.CENTER);
        buttonPanel.getChildren().addAll(cancelButton, registerButton);
        
        // Ajouter tous les éléments au panneau du formulaire
        formPanel.getChildren().addAll(
            titleLabel,
            nomField,
            emailField,
            passwordField,
            confirmPasswordField,
            isSuperAdminCheckBox,
            buttonPanel
        );
        
        // Ajouter le formulaire au conteneur principal
        view.getChildren().add(formPanel);
        
        // Configuration des boutons
        setupButtonHandlers();
        
        // Fond dégradé pour l'arrière-plan
        view.setBackground(new Background(new BackgroundFill(
            Color.web("#f0f8ff"), CornerRadii.EMPTY, Insets.EMPTY)));
    }
    
    /**
     * Crée un champ de texte avec un label
     * @param labelText Texte du label
     * @return VBox contenant le label et le champ de texte
     */
    private VBox createTextField(String labelText) {
        Label label = new Label(labelText);
        label.setFont(Font.font(14));
        label.setTextFill(Color.DARKGRAY);
        
        TextField textField = new TextField();
        textField.setPrefHeight(35);
        textField.setFont(Font.font(14));
        textField.setPromptText(labelText.toLowerCase());
        
        VBox container = new VBox(5);
        container.getChildren().addAll(label, textField);
        return container;
    }
    
    /**
     * Crée un champ de mot de passe avec un label
     * @param labelText Texte du label
     * @return VBox contenant le label et le champ de mot de passe
     */
    private VBox createPasswordField(String labelText) {
        Label label = new Label(labelText);
        label.setFont(Font.font(14));
        label.setTextFill(Color.DARKGRAY);
        
        PasswordField passwordField = new PasswordField();
        passwordField.setPrefHeight(35);
        passwordField.setFont(Font.font(14));
        passwordField.setPromptText(labelText.toLowerCase());
        
        VBox container = new VBox(5);
        container.getChildren().addAll(label, passwordField);
        return container;
    }
    
    /**
     * Obtient le conteneur principal de la vue
     * @return VBox contenant toute l'interface
     */
    public VBox getView() {
        return view;
    }
    
    /**
     * Obtient le champ du nom
     * @return TextField du nom
     */
    public TextField getNomField() {
        // Le TextField est le deuxième enfant du VBox (après le Label)
        return (TextField) ((VBox) ((VBox) nomField).getChildren().get(1)).getChildren().get(0);
    }
    
    /**
     * Obtient le champ de l'email
     * @return TextField de l'email
     */
    public TextField getEmailField() {
        return (TextField) ((VBox) ((VBox) emailField).getChildren().get(1)).getChildren().get(0);
    }
    
    /**
     * Obtient le champ du mot de passe
     * @return PasswordField du mot de passe
     */
    public PasswordField getPasswordField() {
        return (PasswordField) ((VBox) ((VBox) passwordField).getChildren().get(1)).getChildren().get(0);
    }
    
    /**
     * Obtient le champ de confirmation du mot de passe
     * @return PasswordField de confirmation
     */
    public PasswordField getConfirmPasswordField() {
        return (PasswordField) ((VBox) ((VBox) confirmPasswordField).getChildren().get(1)).getChildren().get(0);
    }
    
    /**
     * Vérifie si l'option super administrateur est cochée
     * @return true si c'est un super admin
     */
    public boolean isSuperAdmin() {
        return isSuperAdminCheckBox.isSelected();
    }
    
    /**
     * Obtient le bouton d'inscription
     * @return Button d'inscription
     */
    public Button getRegisterButton() {
        return registerButton;
    }
    
    /**
     * Obtient le bouton d'annulation
     * @return Button d'annulation
     */
    public Button getCancelButton() {
        return cancelButton;
    }
    
    /**
     * Configuration des gestionnaires d'événements pour les boutons
     */
    private void setupButtonHandlers() {
        // Bouton d'inscription
        registerButton.setOnAction(e -> {
            // Gestion de l'inscription
            String nom = getNomField().getText().trim();
            String email = getEmailField().getText().trim();
            String password = getPasswordField().getText();
            String confirmPassword = getConfirmPasswordField().getText();
            
            // Validation des champs
            if (nom.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Tous les champs sont obligatoires.");
                return;
            }
            
            if (!password.equals(confirmPassword)) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Les mots de passe ne correspondent pas.");
                return;
            }
            
            // Création du contrôleur d'authentification
            gestionnaire_taches.controller.AuthenticationController authController = 
                new gestionnaire_taches.controller.AuthenticationController();
            
            if (authController.registerAdministrator(nom, email, password)) {
                showAlert(Alert.AlertType.INFORMATION, "Succès", "Compte administrateur créé avec succès !");
                clearFields();
                // Retour à l'écran de connexion
                gestionnaire_taches.Main.showLoginView();
            } else {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible de créer le compte. Veuillez vérifier les informations.");
            }
        });
        
        // Bouton d'annulation
        cancelButton.setOnAction(e -> {
            // Retour à l'écran de connexion
            gestionnaire_taches.Main.showLoginView();
        });
    }
    
    /**
     * Affiche une alerte
     * @param alertType Type d'alerte
     * @param title Titre de l'alerte
     * @param message Message de l'alerte
     */
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    /**
     * Efface tous les champs du formulaire
     * Utile après une inscription réussie
     */
    public void clearFields() {
        getNomField().clear();
        getEmailField().clear();
        getPasswordField().clear();
        getConfirmPasswordField().clear();
        isSuperAdminCheckBox.setSelected(false);
    }
}