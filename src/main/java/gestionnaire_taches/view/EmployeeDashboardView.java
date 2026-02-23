package gestionnaire_taches.view;

import gestionnaire_taches.dao.impl.TaskDAOImpl;
import gestionnaire_taches.model.Task;
import gestionnaire_taches.model.TaskStatus;
import gestionnaire_taches.util.SessionManager;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * Contenu du tableau de bord employé.
 * Le layout (topbar, sidebar, footer) est géré par AppShell — cette classe
 * ne produit que le contenu central.
 */
public class EmployeeDashboardView {

    private static final String COLOR_BG     = "#F5F7FA";
    private static final String COLOR_WHITE  = "#FFFFFF";
    private static final String COLOR_TITLE  = "#1E2A3A";
    private static final String COLOR_SUBTITLE = "#7F8C8D";
    private static final String COLOR_CARD_1 = "#1565C0";
    private static final String COLOR_CARD_2 = "#2E7D32";
    private static final String COLOR_CARD_3 = "#C62828";

    private final String employeeName;

    public EmployeeDashboardView(String employeeName) {
        this.employeeName = employeeName != null ? employeeName : "Employé";
    }

    /**
     * Retourne le contenu à injecter dans AppShell via shell.navigateTo(...)
     * (plus de BorderPane avec topbar/sidebar recréés)
     */
    public VBox getView() {
        VBox content = new VBox(25);
        content.setPadding(new Insets(30));
        content.setStyle("-fx-background-color: " + COLOR_BG + ";");

        content.getChildren().addAll(
            buildWelcomeCard(),
            sectionLabel("Mes tâches"),
            buildStatsRow(),
            sectionLabel("Accès rapide"),
            buildShortcuts()
        );

        return content;
    }

    // ── Carte de bienvenue ────────────────────────────────────────────────────

    private VBox buildWelcomeCard() {
        VBox card = new VBox(6);
        card.setPadding(new Insets(22, 28, 22, 28));
        card.setStyle(
            "-fx-background-color: " + COLOR_WHITE + ";" +
            "-fx-background-radius: 10;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.06), 8, 0, 0, 2);"
        );

        String heure = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
        String date  = LocalDateTime.now().format(
            DateTimeFormatter.ofPattern("EEEE dd MMMM yyyy", Locale.FRENCH));

        Label welcome = new Label("Bonjour, " + employeeName + " 👋");
        welcome.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        welcome.setStyle("-fx-text-fill: " + COLOR_TITLE + ";");

        Label dateLabel = new Label(capitalize(date) + "  –  " + heure);
        dateLabel.setStyle("-fx-text-fill: " + COLOR_SUBTITLE + "; -fx-font-size: 13px;");

        Label intro = new Label("Voici un aperçu de vos tâches assignées.");
        intro.setStyle("-fx-text-fill: #555; -fx-font-size: 13px;");

        card.getChildren().addAll(welcome, dateLabel, intro);
        return card;
    }

    // ── Statistiques dynamiques ───────────────────────────────────────────────

    private HBox buildStatsRow() {
        // Récupérer les tâches de l'employé connecté
        int employeeId = -1;
        try {
            gestionnaire_taches.model.User u = SessionManager.getInstance().getCurrentUser();
            if (u != null) employeeId = u.getId();
        } catch (Exception ignored) {}

        List<Task> allTasks = new TaskDAOImpl().findAll();
        final int eid = employeeId;
        List<Task> myTasks = eid > 0
            ? allTasks.stream().filter(t -> t.getEmployeeId() == eid).collect(Collectors.toList())
            : allTasks;

        long total     = myTasks.size();
        long terminées = myTasks.stream().filter(t -> t.getStatut() == TaskStatus.TERMINEE).count();
        long enCours   = myTasks.stream().filter(t -> t.getStatut() == TaskStatus.EN_COURS).count();
        long enRetard  = myTasks.stream()
            .filter(t -> t.getDateLimite() != null
                && t.getDateLimite().isBefore(java.time.LocalDate.now())
                && t.getStatut() != TaskStatus.TERMINEE)
            .count();

        HBox row = new HBox(20);
        row.setAlignment(Pos.CENTER_LEFT);
        row.getChildren().addAll(
            statCard("📋", "Assignées",   String.valueOf(total),     "tâches au total",     COLOR_CARD_1),
            statCard("✅", "Terminées",   String.valueOf(terminées), "tâches complétées",   COLOR_CARD_2),
            statCard("⏰", "En retard",   String.valueOf(enRetard),  "dépassement de délai",COLOR_CARD_3)
        );
        return row;
    }

    private VBox statCard(String icon, String title, String value, String subtitle, String color) {
        VBox card = new VBox(6);
        card.setPrefWidth(200);
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

        javafx.scene.control.Button myTasksBtn = shortcutBtn("✅  Voir mes tâches", "#1565C0");
        myTasksBtn.setOnAction(e -> {
            AppShell shell = gestionnaire_taches.Main.getAppShell();
            if (shell != null) {
                List<Task> list = new TaskDAOImpl().findAll();
                shell.navigateTo(new TaskListView(
                    FXCollections.observableArrayList(list)).getView());
            }
        });

        shortcuts.getChildren().add(myTasksBtn);
        return shortcuts;
    }

    private javafx.scene.control.Button shortcutBtn(String text, String color) {
        javafx.scene.control.Button btn = new javafx.scene.control.Button(text);
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