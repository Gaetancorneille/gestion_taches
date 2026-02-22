package gestionnaire_taches.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class EmployeeDashboardView {

    private String employeeName;

    public EmployeeDashboardView(String employeeName) {
        this.employeeName = employeeName != null ? employeeName : "Employé";
    }

    public BorderPane getView() {
        BorderPane mainView = new BorderPane();
        mainView.setStyle("-fx-background-color: #f0f0f0;");

        // Créer la barre supérieure
        HBox topBar = createTopBar();
        mainView.setTop(topBar);

        // Créer le menu latéral
        VBox sideMenu = createSideMenu();
        mainView.setLeft(sideMenu);

        // Créer la zone de contenu
        VBox contentArea = createContentArea();
        mainView.setCenter(contentArea);

        return mainView;
    }

    private HBox createTopBar() {
        HBox topBar = new HBox();
        topBar.setAlignment(Pos.CENTER_RIGHT);
        topBar.setPadding(new Insets(15, 30, 15, 30));
        topBar.setStyle("-fx-background-color: #2c3e50; -fx-spacing: 20;");

        // Titre de la page
        Label pageTitle = new Label("Tableau de bord employé");
        pageTitle.setStyle("-fx-text-fill: white; -fx-font-size: 18px;");
        HBox.setHgrow(pageTitle, javafx.scene.layout.Priority.ALWAYS);

        // Profil utilisateur
        HBox profileBox = new HBox(15);
        profileBox.setAlignment(Pos.CENTER_RIGHT);

        Label userName = new Label(employeeName);
        userName.setStyle("-fx-text-fill: white;");

        Button logoutBtn = new Button("Déconnexion");
        logoutBtn.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white;");
        logoutBtn.setOnAction(e -> {
            // Logout and return to login screen
            gestionnaire_taches.Main.showLoginView();
        });

        profileBox.getChildren().addAll(userName, logoutBtn);

        topBar.getChildren().addAll(pageTitle, profileBox);

        return topBar;
    }

    private VBox createSideMenu() {
        VBox sideMenu = new VBox(10);
        sideMenu.setPadding(new Insets(30, 20, 30, 20));
        sideMenu.setMinWidth(200);
        sideMenu.setStyle("-fx-background-color: #34495e; -fx-border-color: #2c3e50;");

        // Logo
        Label logo = new Label("📊 Mon Espace");
        logo.setStyle("-fx-text-fill: white; -fx-font-size: 18px; -fx-alignment: center;");
        logo.setPadding(new Insets(0, 0, 20, 0));

        // Menu items
        Button dashboardBtn = createMenuItem("🏠 Tableau de bord", true);
        Button tasksBtn = createMenuItem("✅ Mes tâches", false);
        Button profileBtn = createMenuItem("👤 Mon profil", false);

        VBox menuBox = new VBox(5);
        menuBox.getChildren().addAll(dashboardBtn, tasksBtn, profileBtn);

        sideMenu.getChildren().addAll(logo, menuBox);

        return sideMenu;
    }

    private Button createMenuItem(String text, boolean active) {
        Button btn = new Button(text);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setAlignment(Pos.CENTER_LEFT);
        btn.setPadding(new Insets(12, 15, 12, 15));
        
        if (active) {
            btn.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");
        } else {
            btn.setStyle("-fx-background-color: transparent; -fx-text-fill: white;");
        }
        
        btn.setOnMouseEntered(e -> {
            if (!active) {
                btn.setStyle("-fx-background-color: #3d566e; -fx-text-fill: white;");
            }
        });
        
        btn.setOnMouseExited(e -> {
            if (!active) {
                btn.setStyle("-fx-background-color: transparent; -fx-text-fill: white;");
            }
        });

        return btn;
    }

    private VBox createContentArea() {
        VBox contentArea = new VBox(20);
        contentArea.setPadding(new Insets(20));
        contentArea.setStyle("-fx-background-color: #ecf0f1;");

        // Section titre
        Label sectionTitle = new Label("Mes tâches");
        sectionTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        // Statistiques personnelles
        HBox statsCards = new HBox(20);
        statsCards.setAlignment(Pos.CENTER);

        statsCards.getChildren().addAll(
            createStatCard("📋 Tâches assignées", "3", "Tâches en cours", "#3498db"),
            createStatCard("✅ Tâches complétées", "1", "Tâches terminées", "#2ecc71"),
            createStatCard("⏰ En retard", "0", "Tâches en souffrance", "#e74c3c")
        );

        contentArea.getChildren().addAll(sectionTitle, statsCards);

        return contentArea;
    }

    private VBox createStatCard(String title, String value, String subtitle, String color) {
        VBox card = new VBox(5);
        card.setStyle("-fx-background-color: " + color + "; -fx-background-radius: 10; -fx-padding: 20; -fx-spacing: 10;");
        card.setPrefWidth(200);

        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-text-fill: white; -fx-font-size: 16px;");

        Label valueLabel = new Label(value);
        valueLabel.setStyle("-fx-text-fill: white; -fx-font-size: 32px; -fx-font-weight: bold;");

        Label subtitleLabel = new Label(subtitle);
        subtitleLabel.setStyle("-fx-text-fill: white; -fx-font-size: 12px;");

        card.getChildren().addAll(titleLabel, valueLabel, subtitleLabel);

        return card;
    }
}