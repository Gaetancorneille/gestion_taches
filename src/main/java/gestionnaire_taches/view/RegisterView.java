package gestionnaire_taches.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Vue d'inscription des nouveaux administrateurs.
 * Thème : Clair & Professionnel — cohérent avec LoginView et AppShell.
 */
public class RegisterView {

    private TextField     nomField;
    private TextField     emailField;
    private PasswordField passwordField;
    private PasswordField confirmPasswordField;
    private CheckBox      isSuperAdminCheckBox;
    private Button        registerButton;
    private Button        cancelButton;

    private VBox view;

    public RegisterView() {
        createView();
    }

    private void createView() {
        // ── Layout principal : panneau gauche bleu + panneau droit formulaire ──
        BorderPane root = new BorderPane();
        root.setLeft(createBrandPanel());
        root.setCenter(createFormPanel());

        view = new VBox(root);
        VBox.setVgrow(root, Priority.ALWAYS);
        root.prefHeightProperty().bind(view.heightProperty());
        root.prefWidthProperty().bind(view.widthProperty());
    }

    // ── Panneau gauche (branding) ────────────────────────────────────────────

    private VBox createBrandPanel() {
        VBox brand = new VBox(20);
        brand.setAlignment(Pos.CENTER);
        brand.setPrefWidth(420);
        brand.setPadding(new Insets(60));
        brand.setStyle("-fx-background-color: #1565C0;");

        Label appIcon = new Label("📋");
        appIcon.setStyle("-fx-font-size: 56px;");

        Label appName = new Label("TaskManager Pro");
        appName.setFont(Font.font("Arial", FontWeight.BOLD, 26));
        appName.setStyle("-fx-text-fill: white;");

        Label tagline = new Label("Créez votre compte administrateur\npour gérer votre organisation.");
        tagline.setStyle(
            "-fx-text-fill: rgba(255,255,255,0.75);" +
            "-fx-font-size: 14px;" +
            "-fx-text-alignment: center;"
        );
        tagline.setWrapText(true);
        tagline.setAlignment(Pos.CENTER);

        Region sep = new Region();
        sep.setMinHeight(2);
        sep.setMaxWidth(60);
        sep.setStyle("-fx-background-color: rgba(255,255,255,0.35); -fx-background-radius: 2;");

        Label info = new Label(
            "✔  Accès complet au système\n" +
            "✔  Gestion des services et employés\n" +
            "✔  Supervision des tâches"
        );
        info.setStyle(
            "-fx-text-fill: rgba(255,255,255,0.65);" +
            "-fx-font-size: 13px;" +
            "-fx-line-spacing: 6;"
        );

        brand.getChildren().addAll(appIcon, appName, tagline, sep, info);
        return brand;
    }

    // ── Panneau droit (formulaire) ───────────────────────────────────────────

    private VBox createFormPanel() {
        VBox panel = new VBox();
        panel.setAlignment(Pos.CENTER);
        panel.setStyle("-fx-background-color: #F5F7FA;");
        VBox.setVgrow(panel, Priority.ALWAYS);

        VBox card = new VBox(18);
        card.setAlignment(Pos.TOP_LEFT);
        card.setPrefWidth(400);
        card.setMaxWidth(400);
        card.setPadding(new Insets(40));
        card.setStyle(
            "-fx-background-color: white;" +
            "-fx-background-radius: 12;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.10), 20, 0, 0, 4);"
        );

        // En-tête
        Label title = new Label("Créer un compte");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        title.setStyle("-fx-text-fill: #1E2A3A;");

        Label subtitle = new Label("Remplissez les informations ci-dessous pour créer un compte administrateur.");
        subtitle.setStyle("-fx-text-fill: #7F8C8D; -fx-font-size: 12px;");
        subtitle.setWrapText(true);

        // Champs de saisie
        nomField             = styledTextField("Jean Dupont");
        emailField           = styledTextField("jean.dupont@entreprise.com");
        passwordField        = new PasswordField();
        confirmPasswordField = new PasswordField();

        stylePasswordField(passwordField,        "Minimum 6 caractères");
        stylePasswordField(confirmPasswordField, "Répétez le mot de passe");

        isSuperAdminCheckBox = new CheckBox("Compte Super Administrateur");
        isSuperAdminCheckBox.setStyle("-fx-font-size: 13px; -fx-text-fill: #34495E;");

        // Message d'erreur
        Label errorLabel = new Label();
        errorLabel.setStyle(
            "-fx-text-fill: #C0392B;" +
            "-fx-background-color: #FDEDEC;" +
            "-fx-background-radius: 6;" +
            "-fx-padding: 8 12 8 12;" +
            "-fx-font-size: 12px;"
        );
        errorLabel.setVisible(false);
        errorLabel.setManaged(false);
        errorLabel.setWrapText(true);
        errorLabel.setMaxWidth(Double.MAX_VALUE);

        // Boutons
        registerButton = new Button("Créer le compte");
        registerButton.setMaxWidth(Double.MAX_VALUE);
        registerButton.setStyle(
            "-fx-background-color: #1565C0;" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 14px;" +
            "-fx-font-weight: bold;" +
            "-fx-padding: 13 0 13 0;" +
            "-fx-background-radius: 7;" +
            "-fx-cursor: hand;"
        );

        cancelButton = new Button("Annuler");
        cancelButton.setMaxWidth(Double.MAX_VALUE);
        cancelButton.setStyle(
            "-fx-background-color: transparent;" +
            "-fx-text-fill: #7F8C8D;" +
            "-fx-font-size: 13px;" +
            "-fx-border-color: #D5DCE4;" +
            "-fx-border-radius: 7;" +
            "-fx-background-radius: 7;" +
            "-fx-padding: 11 0 11 0;" +
            "-fx-cursor: hand;"
        );

        // Lien retour connexion
        HBox loginRow = new HBox(6);
        loginRow.setAlignment(Pos.CENTER);
        Label alreadyLabel = new Label("Déjà un compte ?");
        alreadyLabel.setStyle("-fx-text-fill: #7F8C8D; -fx-font-size: 12px;");
        Button loginLink = new Button("Se connecter");
        loginLink.setStyle(
            "-fx-background-color: transparent;" +
            "-fx-text-fill: #1565C0;" +
            "-fx-font-size: 12px;" +
            "-fx-cursor: hand;" +
            "-fx-underline: true;" +
            "-fx-padding: 0;"
        );
        loginLink.setOnAction(e -> gestionnaire_taches.Main.showLoginView());
        loginRow.getChildren().addAll(alreadyLabel, loginLink);

        // Handlers
        setupButtonHandlers(errorLabel);

        card.getChildren().addAll(
            title, subtitle,
            fieldGroup("Nom complet",             nomField),
            fieldGroup("Adresse email",           emailField),
            fieldGroup("Mot de passe",            passwordField),
            fieldGroup("Confirmer le mot de passe", confirmPasswordField),
            isSuperAdminCheckBox,
            errorLabel,
            registerButton,
            cancelButton,
            loginRow
        );

        panel.getChildren().add(card);
        return panel;
    }

    // ── Handlers ─────────────────────────────────────────────────────────────

    private void setupButtonHandlers(Label errorLabel) {
        registerButton.setOnAction(e -> {
            String nom             = nomField.getText().trim();
            String email           = emailField.getText().trim();
            String password        = passwordField.getText();
            String confirmPassword = confirmPasswordField.getText();

            if (nom.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                showError(errorLabel, "Tous les champs sont obligatoires.");
                return;
            }
            if (password.length() < 6) {
                showError(errorLabel, "Le mot de passe doit contenir au moins 6 caractères.");
                return;
            }
            if (!password.equals(confirmPassword)) {
                showError(errorLabel, "Les mots de passe ne correspondent pas.");
                return;
            }

            gestionnaire_taches.controller.AuthenticationController authController =
                new gestionnaire_taches.controller.AuthenticationController();

            if (authController.registerAdministrator(nom, email, password)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Compte créé");
                alert.setHeaderText(null);
                alert.setContentText("✔  Compte administrateur créé avec succès !");
                alert.showAndWait();
                gestionnaire_taches.Main.showLoginView();
            } else {
                showError(errorLabel, "Impossible de créer le compte. Vérifiez que l'email n'est pas déjà utilisé.");
            }
        });

        cancelButton.setOnAction(e -> gestionnaire_taches.Main.showLoginView());
    }

    // ── Helpers ──────────────────────────────────────────────────────────────

    private VBox fieldGroup(String labelText, Control field) {
        VBox group = new VBox(6);
        Label lbl = new Label(labelText);
        lbl.setStyle(
            "-fx-text-fill: #34495E;" +
            "-fx-font-size: 13px;" +
            "-fx-font-weight: bold;"
        );
        group.getChildren().addAll(lbl, field);
        return group;
    }

    private TextField styledTextField(String prompt) {
        TextField tf = new TextField();
        tf.setPromptText(prompt);
        tf.setMaxWidth(Double.MAX_VALUE);
        tf.setStyle(inputStyle());
        return tf;
    }

    private void stylePasswordField(PasswordField pf, String prompt) {
        pf.setPromptText(prompt);
        pf.setMaxWidth(Double.MAX_VALUE);
        pf.setStyle(inputStyle());
    }

    private String inputStyle() {
        return
            "-fx-background-color: #FAFBFC;" +
            "-fx-border-color: #D5DCE4;" +
            "-fx-border-radius: 6;" +
            "-fx-background-radius: 6;" +
            "-fx-padding: 10 12 10 12;" +
            "-fx-font-size: 13px;";
    }

    private void showError(Label errorLabel, String message) {
        errorLabel.setText("⚠  " + message);
        errorLabel.setVisible(true);
        errorLabel.setManaged(true);
    }

    // ── Getters (compatibilité avec l'ancienne API) ───────────────────────────

    public VBox getView() { return view; }
    public TextField getNomField()              { return nomField; }
    public TextField getEmailField()            { return emailField; }
    public PasswordField getPasswordField()     { return passwordField; }
    public PasswordField getConfirmPasswordField() { return confirmPasswordField; }
    public boolean isSuperAdmin()               { return isSuperAdminCheckBox.isSelected(); }
    public Button getRegisterButton()           { return registerButton; }
    public Button getCancelButton()             { return cancelButton; }

    public void clearFields() {
        nomField.clear();
        emailField.clear();
        passwordField.clear();
        confirmPasswordField.clear();
        isSuperAdminCheckBox.setSelected(false);
    }
}