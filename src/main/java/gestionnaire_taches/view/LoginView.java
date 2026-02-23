package gestionnaire_taches.view;

import gestionnaire_taches.controller.AuthenticationController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class LoginView {

    private AuthenticationController authController;

    public LoginView() {
        this.authController = new AuthenticationController();
    }

    public BorderPane getView() {

        // ── Layout principal : panneau gauche bleu + panneau droit formulaire ──
        BorderPane root = new BorderPane();
        root.setLeft(createBrandPanel());
        root.setCenter(createFormPanel());

        return root;
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

        Label tagline = new Label("Gérez vos équipes et tâches\nde façon simple et efficace.");
        tagline.setStyle(
            "-fx-text-fill: rgba(255,255,255,0.75);" +
            "-fx-font-size: 14px;" +
            "-fx-text-alignment: center;"
        );
        tagline.setWrapText(true);
        tagline.setAlignment(Pos.CENTER);

        // Séparateur décoratif
        Region sep = new Region();
        sep.setMinHeight(2);
        sep.setMaxWidth(60);
        sep.setStyle("-fx-background-color: rgba(255,255,255,0.35); -fx-background-radius: 2;");

        Label featuresLabel = new Label("✔  Gestion hiérarchique\n✔  Suivi des tâches en temps réel\n✔  Tableau de bord complet");
        featuresLabel.setStyle(
            "-fx-text-fill: rgba(255,255,255,0.65);" +
            "-fx-font-size: 13px;" +
            "-fx-line-spacing: 6;"
        );

        brand.getChildren().addAll(appIcon, appName, tagline, sep, featuresLabel);
        return brand;
    }

    // ── Panneau droit (formulaire) ───────────────────────────────────────────

    private VBox createFormPanel() {
        VBox panel = new VBox(0);
        panel.setAlignment(Pos.CENTER);
        panel.setStyle("-fx-background-color: #F5F7FA;");

        VBox card = new VBox(22);
        card.setAlignment(Pos.TOP_LEFT);
        card.setPrefWidth(380);
        card.setMaxWidth(380);
        card.setPadding(new Insets(40));
        card.setStyle(
            "-fx-background-color: white;" +
            "-fx-background-radius: 12;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.10), 20, 0, 0, 4);"
        );

        // En-tête
        Label title = new Label("Connexion");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        title.setStyle("-fx-text-fill: #1E2A3A;");

        Label subtitle = new Label("Entrez vos identifiants pour accéder au système.");
        subtitle.setStyle("-fx-text-fill: #7F8C8D; -fx-font-size: 13px;");
        subtitle.setWrapText(true);

        // Champs
        VBox emailGroup  = fieldGroup("Email",           createTextField("exemple@entreprise.com", false));
        VBox passGroup   = fieldGroup("Mot de passe",    createTextField("••••••••", true));
        VBox typeGroup   = fieldGroup("Type de compte",  null);

        TextField emailField    = (TextField)    ((VBox) emailGroup.getChildren().get(1)).getChildren().get(0);
        PasswordField passField = (PasswordField)((VBox) passGroup.getChildren().get(1)).getChildren().get(0);

        ComboBox<String> userTypeBox = new ComboBox<>();
        userTypeBox.getItems().addAll("Administrateur", "Employé");
        userTypeBox.setValue("Administrateur");
        userTypeBox.setMaxWidth(Double.MAX_VALUE);
        userTypeBox.setStyle(
            "-fx-background-color: #FAFBFC;" +
            "-fx-border-color: #D5DCE4;" +
            "-fx-border-radius: 6;" +
            "-fx-background-radius: 6;" +
            "-fx-font-size: 13px;" +
            "-fx-padding: 4 8 4 8;"
        );
        typeGroup.getChildren().add(userTypeBox);

        // Message d'erreur (caché par défaut)
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

        // Bouton connexion
        Button loginBtn = new Button("Se connecter");
        loginBtn.setMaxWidth(Double.MAX_VALUE);
        loginBtn.setStyle(
            "-fx-background-color: #1565C0;" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 14px;" +
            "-fx-font-weight: bold;" +
            "-fx-padding: 13 0 13 0;" +
            "-fx-background-radius: 7;" +
            "-fx-cursor: hand;"
        );
        loginBtn.setOnMouseEntered(e ->
            loginBtn.setStyle(
                "-fx-background-color: #0D47A1;" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 14px;" +
                "-fx-font-weight: bold;" +
                "-fx-padding: 13 0 13 0;" +
                "-fx-background-radius: 7;" +
                "-fx-cursor: hand;"
            )
        );
        loginBtn.setOnMouseExited(e ->
            loginBtn.setStyle(
                "-fx-background-color: #1565C0;" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 14px;" +
                "-fx-font-weight: bold;" +
                "-fx-padding: 13 0 13 0;" +
                "-fx-background-radius: 7;" +
                "-fx-cursor: hand;"
            )
        );

        // Lien inscription
        HBox registerRow = new HBox(6);
        registerRow.setAlignment(Pos.CENTER);
        Label noAccount = new Label("Pas encore de compte ?");
        noAccount.setStyle("-fx-text-fill: #7F8C8D; -fx-font-size: 12px;");
        Button registerLink = new Button("S'inscrire");
        registerLink.setStyle(
            "-fx-background-color: transparent;" +
            "-fx-text-fill: #1565C0;" +
            "-fx-font-size: 12px;" +
            "-fx-cursor: hand;" +
            "-fx-underline: true;" +
            "-fx-padding: 0;"
        );
        registerLink.setOnAction(e -> gestionnaire_taches.Main.showRegisterView());
        registerRow.getChildren().addAll(noAccount, registerLink);

        // Action connexion
        loginBtn.setOnAction(e -> {
            String email    = emailField.getText().trim();
            String password = passField.getText();

            if (email.isEmpty() || password.isEmpty()) {
                showError(errorLabel, "Veuillez remplir tous les champs.");
                return;
            }

            if (authController.login(email, password)) {
                gestionnaire_taches.model.User currentUser = authController.getCurrentUser();
                errorLabel.setVisible(false);
                errorLabel.setManaged(false);

                if (currentUser instanceof gestionnaire_taches.model.Administrator) {
                    gestionnaire_taches.Main.showAdminDashboard();
                } else if (currentUser instanceof gestionnaire_taches.model.Employee) {
                    gestionnaire_taches.Main.showEmployeeDashboard(currentUser.getNom());
                }
            } else {
                showError(errorLabel, "Email ou mot de passe incorrect. Veuillez réessayer.");
            }
        });

        // Permettre connexion avec Entrée
        passField.setOnAction(e -> loginBtn.fire());
        emailField.setOnAction(e -> passField.requestFocus());

        card.getChildren().addAll(
            title, subtitle,
            emailGroup, passGroup, typeGroup,
            errorLabel,
            loginBtn,
            registerRow
        );

        panel.getChildren().add(card);
        return panel;
    }

    // ── Helpers ──────────────────────────────────────────────────────────────

    private VBox fieldGroup(String labelText, VBox inputWrapper) {
        VBox group = new VBox(6);

        Label lbl = new Label(labelText);
        lbl.setStyle(
            "-fx-text-fill: #34495E;" +
            "-fx-font-size: 13px;" +
            "-fx-font-weight: bold;"
        );

        group.getChildren().add(lbl);
        if (inputWrapper != null) group.getChildren().add(inputWrapper);
        return group;
    }

    private VBox createTextField(String prompt, boolean isPassword) {
        Control field;
        if (isPassword) {
            PasswordField pf = new PasswordField();
            pf.setPromptText(prompt);
            pf.setMaxWidth(Double.MAX_VALUE);
            pf.setStyle(inputStyle());
            field = pf;
        } else {
            TextField tf = new TextField();
            tf.setPromptText(prompt);
            tf.setMaxWidth(Double.MAX_VALUE);
            tf.setStyle(inputStyle());
            field = tf;
        }

        VBox wrapper = new VBox(field);
        return wrapper;
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
}