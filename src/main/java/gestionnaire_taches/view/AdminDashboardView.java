package gestionnaire_taches.view;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import gestionnaire_taches.dao.impl.AdministratorDAOImpl;
import gestionnaire_taches.dao.impl.EmployeeDAOImpl;
import gestionnaire_taches.dao.impl.ServiceDAOImpl;
import gestionnaire_taches.dao.impl.TaskDAOImpl;
import gestionnaire_taches.model.Employee;
import gestionnaire_taches.model.Task;
import gestionnaire_taches.model.User;
import gestionnaire_taches.util.SessionManager;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Contenu du tableau de bord administrateur.
 * Ne contient PAS la sidebar ni la topbar — géré par AppShell.
 */
public class AdminDashboardView {

    private static final String COLOR_BG      = "#F5F7FA";
    private static final String COLOR_WHITE    = "#FFFFFF";
    private static final String COLOR_TITLE    = "#1E2A3A";
    private static final String COLOR_SUBTITLE = "#7F8C8D";
    private static final String COLOR_CARD_1   = "#1565C0";
    private static final String COLOR_CARD_2   = "#2E7D32";
    private static final String COLOR_CARD_3   = "#E65100";
    private static final String COLOR_CARD_4   = "#6A1B9A";

    private final AppShell shell;

    public AdminDashboardView(AppShell shell) {
        this.shell = shell;
    }

    public VBox getView() {
        VBox content = new VBox(25);
        content.setPadding(new Insets(30));
        content.setStyle("-fx-background-color: " + COLOR_BG + ";");

        // ── En-tête de bienvenue ──────────────────────────────────────────
        content.getChildren().add(buildWelcomeCard());

        // ── Statistiques ──────────────────────────────────────────────────
        Label statsTitle = sectionLabel("Vue d'ensemble");
        content.getChildren().add(statsTitle);
        content.getChildren().add(buildStatsRow());

        // ── Accès rapide ──────────────────────────────────────────────────
        Label shortcutsTitle = sectionLabel("Accès rapide");
        content.getChildren().add(shortcutsTitle);
        content.getChildren().add(buildShortcuts());

        return content;
    }

    // ── Carte de bienvenue ───────────────────────────────────────────────────

    private VBox buildWelcomeCard() {
        VBox card = new VBox(6);
        card.setPadding(new Insets(22, 28, 22, 28));
        card.setStyle(
            "-fx-background-color: " + COLOR_WHITE + ";" +
            "-fx-background-radius: 10;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.06), 8, 0, 0, 2);"
        );

        User currentUser = SessionManager.getInstance().getCurrentUser();
        String userName = currentUser != null ? currentUser.getNom() : "Administrateur";
        String heure = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
        String date  = LocalDateTime.now().format(
            DateTimeFormatter.ofPattern("EEEE dd MMMM yyyy", Locale.FRENCH));

        Label welcomeLabel = new Label("Bonjour, " + userName + " 👋");
        welcomeLabel.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        welcomeLabel.setStyle("-fx-text-fill: " + COLOR_TITLE + ";");

        Label dateLabel = new Label(capitalize(date) + "  –  " + heure);
        dateLabel.setStyle("-fx-text-fill: " + COLOR_SUBTITLE + "; -fx-font-size: 13px;");

        Label introLabel = new Label("Voici un aperçu de l'état de votre système de gestion de tâches.");
        introLabel.setStyle("-fx-text-fill: #555; -fx-font-size: 13px;");

        card.getChildren().addAll(welcomeLabel, dateLabel, introLabel);
        return card;
    }

    // ── Statistiques ─────────────────────────────────────────────────────────

    private HBox buildStatsRow() {
        long nbServices  = new ServiceDAOImpl().count();
        long nbEmployees = new EmployeeDAOImpl().count();
        long nbTasks     = new TaskDAOImpl().count();
        long nbAdmins    = new AdministratorDAOImpl().count();

        HBox row = new HBox(20);
        row.setAlignment(Pos.CENTER_LEFT);
        row.getChildren().addAll(
            statCard("🏢", "Services",    String.valueOf(nbServices),  "services actifs",   COLOR_CARD_1),
            statCard("👥", "Employés",    String.valueOf(nbEmployees), "employés inscrits", COLOR_CARD_2),
            statCard("✅", "Tâches",      String.valueOf(nbTasks),     "tâches en base",    COLOR_CARD_3),
            statCard("🔑", "Admins",      String.valueOf(nbAdmins),    "administrateurs",   COLOR_CARD_4)
        );
        return row;
    }

    private VBox statCard(String icon, String title, String value, String subtitle, String color) {
        VBox card = new VBox(6);
        card.setPrefWidth(190);
        card.setPadding(new Insets(20, 22, 20, 22));
        card.setStyle(
            "-fx-background-color: " + color + ";" +
            "-fx-background-radius: 10;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.12), 8, 0, 0, 3);"
        );

        Label iconLabel = new Label(icon + "  " + title);
        iconLabel.setStyle("-fx-text-fill: rgba(255,255,255,0.85); -fx-font-size: 12px;");

        Label valueLabel = new Label(value);
        valueLabel.setFont(Font.font("Arial", FontWeight.BOLD, 32));
        valueLabel.setStyle("-fx-text-fill: white;");

        Label subtitleLabel = new Label(subtitle);
        subtitleLabel.setStyle("-fx-text-fill: rgba(255,255,255,0.7); -fx-font-size: 11px;");

        card.getChildren().addAll(iconLabel, valueLabel, subtitleLabel);
        return card;
    }

    // ── Raccourcis rapides ────────────────────────────────────────────────────

    private HBox buildShortcuts() {
        HBox shortcuts = new HBox(15);

        Button goServices  = shortcutBtn("🏢  Gérer les services",  "#1565C0");
        Button goEmployees = shortcutBtn("👥  Gérer les employés",  "#2E7D32");
        Button goTasks     = shortcutBtn("✅  Gérer les tâches",    "#E65100");

        goServices.setOnAction(e -> {
            java.util.List<gestionnaire_taches.model.Service> list =
                new ServiceDAOImpl().findAll();
            shell.navigateTo(new ServiceListView(
                FXCollections.observableArrayList(list)).getView());
        });
        goEmployees.setOnAction(e -> {
            List<Employee> list = new EmployeeDAOImpl().findAll();
            shell.navigateTo(new EmployeeListView(
                FXCollections.observableArrayList(list)).getView());
        });
        goTasks.setOnAction(e -> {
            List<Task> list = new TaskDAOImpl().findAll();
            shell.navigateTo(new TaskListView(
                FXCollections.observableArrayList(list)).getView());
        });

        shortcuts.getChildren().addAll(goServices, goEmployees, goTasks);
        return shortcuts;
    }

    private Button shortcutBtn(String text, String color) {
        Button btn = new Button(text);
        btn.setStyle(
            "-fx-background-color: " + color + ";" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 13px;" +
            "-fx-padding: 12 22 12 22;" +
            "-fx-background-radius: 8;" +
            "-fx-cursor: hand;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.12), 6, 0, 0, 2);"
        );
        return btn;
    }

    // ── Utilitaires ───────────────────────────────────────────────────────────

    private Label sectionLabel(String text) {
        Label lbl = new Label(text);
        lbl.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        lbl.setStyle("-fx-text-fill: " + COLOR_TITLE + ";");
        return lbl;
    }

    private String capitalize(String s) {
        if (s == null || s.isEmpty()) return s;
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }
}