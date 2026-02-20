package gestionnaire_taches.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

/**
 * Page d'accueil du système de gestion des tâches.
 * Affiche les équipes disponibles et des informations générales.
 */
public class index {
    private VBox view;

    public index() {
        initializeView();
    }

    private void initializeView() {
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setStyle("-fx-background-color: #f0f2f5;");
        scrollPane.setFitToWidth(true);

        VBox mainContent = new VBox(20);
        mainContent.setPadding(new Insets(30));
        mainContent.setStyle("-fx-background-color: #f0f2f5;");

        // Section Welcome
        VBox welcomeSection = createWelcomeSection();

        // Section Statistiques
        HBox statsSection = createStatsSection();

        // Section Équipes
        VBox teamsSection = createTeamsSection();

        // Section Raccourcis
        VBox shortcutsSection = createShortcutsSection();

        mainContent.getChildren().addAll(
            welcomeSection,
            new Separator(),
            statsSection,
            new Separator(),
            teamsSection,
            new Separator(),
            shortcutsSection
        );

        scrollPane.setContent(mainContent);
        view = new VBox(scrollPane);
        VBox.setVgrow(scrollPane, Priority.ALWAYS);
    }

    /**
     * Crée la section de bienvenue.
     */
    private VBox createWelcomeSection() {
        VBox welcome = new VBox(15);
        welcome.setPadding(new Insets(25));
        welcome.setStyle("-fx-background-color: white; -fx-border-radius: 8; -fx-border-color: #e0e0e0; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 5, 0, 0, 2);");

        Label welcomeTitle = new Label("Bienvenue dans le Système de Gestion des Tâches");
        welcomeTitle.setStyle("-fx-font-size: 28; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        Label welcomeDesc = new Label(
            """
            G\u00e9rez efficacement vos t\u00e2ches, collaborez avec vos \u00e9quipes et suivez l'avancement de vos projets.
            S\u00e9lectionnez votre \u00e9quipe ci-dessous ou utilisez le menu de navigation pour commencer.""");
        welcomeDesc.setStyle("-fx-font-size: 14; -fx-text-fill: #555555; -fx-wrap-text: true;");
        welcomeDesc.setMaxWidth(800);

        Label currentDateTime = new Label("📅 " + java.time.LocalDateTime.now().format(
            java.time.format.DateTimeFormatter.ofPattern("EEEE dd MMMM yyyy - HH:mm", new java.util.Locale("fr", "FR"))
        ));
        currentDateTime.setStyle("-fx-font-size: 12; -fx-text-fill: #7f8c8d; -fx-italic: true;");

        welcome.getChildren().addAll(welcomeTitle, welcomeDesc, currentDateTime);
        return welcome;
    }

    /**
     * Crée la section de statistiques.
     */
    private HBox createStatsSection() {
        HBox stats = new HBox(15);
        stats.setPadding(new Insets(0, 0, 0, 0));

        VBox stat1 = createStatCard("📊 Tâches Totales", "47", "#3498db");
        VBox stat2 = createStatCard("✅ Complétées", "28", "#27ae60");
        VBox stat3 = createStatCard("⏳ En Cours", "15", "#f39c12");
        VBox stat4 = createStatCard("⚠️ En Retard", "4", "#e74c3c");

        HBox.setHgrow(stat1, Priority.ALWAYS);
        HBox.setHgrow(stat2, Priority.ALWAYS);
        HBox.setHgrow(stat3, Priority.ALWAYS);
        HBox.setHgrow(stat4, Priority.ALWAYS);

        stats.getChildren().addAll(stat1, stat2, stat3, stat4);
        return stats;
    }

    /**
     * Crée une carte de statistique.
     */
    private VBox createStatCard(String title, String value, String color) {
        VBox card = new VBox(10);
        card.setPadding(new Insets(20));
        card.setAlignment(Pos.CENTER);
        card.setStyle("-fx-background-color: white; -fx-border-radius: 8; -fx-border-color: " + color + "; -fx-border-width: 2; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 5, 0, 0, 2);");

        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-font-size: 13; -fx-font-weight: bold; -fx-text-fill: #555555;");

        Label valueLabel = new Label(value);
        valueLabel.setStyle("-fx-font-size: 32; -fx-font-weight: bold; -fx-text-fill: " + color + ";");

        card.getChildren().addAll(titleLabel, valueLabel);
        return card;
    }

    /**
     * Crée la section des équipes.
     */
    private VBox createTeamsSection() {
        VBox section = new VBox(20);
        section.setPadding(new Insets(0));

        Label title = new Label("Équipes Disponibles");
        title.setStyle("-fx-font-size: 22; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        HBox teamsContainer = new HBox(20);
        teamsContainer.setPadding(new Insets(0));

        VBox team1Card = createTeamCard(
            "👥 Équipe 1",
            "Développement Backend",
            "Chef : Jean Dupont",
            "Membres : 5",
            "Tâches actives : 8",
            "#9b59b6"
        );

        VBox team2Card = createTeamCard(
            "👥 Équipe 2",
            "Développement Frontend",
            "Chef : Marie Martin",
            "Membres : 4",
            "Tâches actives : 6",
            "#e67e22"
        );

        VBox team3Card = createTeamCard(
            "👥 Équipe 3",
            "Quality Assurance",
            "Chef : Pierre Bernard",
            "Membres : 3",
            "Tâches actives : 5",
            "#1abc9c"
        );

        HBox.setHgrow(team1Card, Priority.ALWAYS);
        HBox.setHgrow(team2Card, Priority.ALWAYS);
        HBox.setHgrow(team3Card, Priority.ALWAYS);

        teamsContainer.getChildren().addAll(team1Card, team2Card, team3Card);

        section.getChildren().addAll(title, teamsContainer);
        return section;
    }

    /**
     * Crée une carte d'équipe.
     */
    private VBox createTeamCard(String teamName, String description, String chef, String members, String tasks, String color) {
        VBox card = new VBox(12);
        card.setPadding(new Insets(20));
        card.setStyle("-fx-background-color: white; -fx-border-radius: 8; -fx-border-color: " + color + "; -fx-border-width: 2; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 5, 0, 0, 2);");

        Label teamTitle = new Label(teamName);
        teamTitle.setStyle("-fx-font-size: 16; -fx-font-weight: bold; -fx-text-fill: " + color + ";");

        Label descLabel = new Label(description);
        descLabel.setStyle("-fx-font-size: 12; -fx-text-fill: #7f8c8d; -fx-wrap-text: true;");

        Separator sep = new Separator();
        sep.setStyle("-fx-padding: 5 0 5 0;");

        Label chefLabel = new Label(chef);
        chefLabel.setStyle("-fx-font-size: 11; -fx-text-fill: #555555;");

        Label membersLabel = new Label(members);
        membersLabel.setStyle("-fx-font-size: 11; -fx-text-fill: #555555;");

        Label tasksLabel = new Label(tasks);
        tasksLabel.setStyle("-fx-font-size: 11; -fx-text-fill: #555555;");

        Button viewBtn = new Button("Voir l'équipe →");
        viewBtn.setMaxWidth(Double.MAX_VALUE);
        viewBtn.setPadding(new Insets(8, 0, 8, 0));
        viewBtn.setStyle("-fx-background-color: " + color + "; -fx-text-fill: white; -fx-font-weight: bold;");
        viewBtn.setCursor(javafx.scene.Cursor.HAND);
        viewBtn.setOnMouseEntered(e -> viewBtn.setStyle("-fx-background-color: " + darkenColor(color) + "; -fx-text-fill: white; -fx-font-weight: bold;"));
        viewBtn.setOnMouseExited(e -> viewBtn.setStyle("-fx-background-color: " + color + "; -fx-text-fill: white; -fx-font-weight: bold;"));

        card.getChildren().addAll(teamTitle, descLabel, sep, chefLabel, membersLabel, tasksLabel, viewBtn);
        return card;
    }

    /**
     * Crée la section des raccourcis.
     */
    private VBox createShortcutsSection() {
        VBox section = new VBox(15);
        section.setPadding(new Insets(25));
        section.setStyle("-fx-background-color: white; -fx-border-radius: 8; -fx-border-color: #e0e0e0;");

        Label title = new Label("Raccourcis Utiles");
        title.setStyle("-fx-font-size: 16; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        GridPane shortcuts = new GridPane();
        shortcuts.setHgap(15);
        shortcuts.setVgap(15);

        Button btn1 = createShortcutButton("📝", "Créer une nouvelle tâche", "#3498db");
        Button btn2 = createShortcutButton("📂", "Voir tous les projets", "#9b59b6");
        Button btn3 = createShortcutButton("👥", "Gérer les équipes", "#e67e22");
        Button btn4 = createShortcutButton("📊", "Afficher les rapports", "#1abc9c");
        Button btn5 = createShortcutButton("🔔", "Notifications", "#e74c3c");
        Button btn6 = createShortcutButton("⚙️", "Préférences", "#95a5a6");

        shortcuts.add(btn1, 0, 0);
        shortcuts.add(btn2, 1, 0);
        shortcuts.add(btn3, 2, 0);
        shortcuts.add(btn4, 0, 1);
        shortcuts.add(btn5, 1, 1);
        shortcuts.add(btn6, 2, 1);

        section.getChildren().addAll(title, shortcuts);
        return section;
    }

    /**
     * Crée un bouton de raccourci.
     */
    private Button createShortcutButton(String icon, String text, String color) {
        Button btn = new Button(icon + "\n" + text);
        btn.setMinWidth(150);
        btn.setMinHeight(80);
        btn.setWrapText(true);
        btn.setStyle("-fx-background-color: " + color + "; -fx-text-fill: white; -fx-font-size: 12; -fx-font-weight: bold;");
        btn.setCursor(javafx.scene.Cursor.HAND);
        btn.setOnMouseEntered(e -> btn.setStyle("-fx-background-color: " + darkenColor(color) + "; -fx-text-fill: white; -fx-font-size: 12; -fx-font-weight: bold;"));
        btn.setOnMouseExited(e -> btn.setStyle("-fx-background-color: " + color + "; -fx-text-fill: white; -fx-font-size: 12; -fx-font-weight: bold;"));
        
        return btn;
    }

    /**
     * Assombrit une couleur.
     */
    private String darkenColor(String color) {
        return switch(color) {
            case "#3498db" -> "#2980b9";
            case "#9b59b6" -> "#8e44ad";
            case "#e67e22" -> "#d35400";
            case "#1abc9c" -> "#16a085";
            case "#e74c3c" -> "#c0392b";
            case "#95a5a6" -> "#7f8c8d";
            case "#27ae60" -> "#229954";
            case "#f39c12" -> "#d68910";
            default -> color;
        };
    }

    public VBox getView() {
        return view;
    }
}