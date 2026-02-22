package gestionnaire_taches.view;

import gestionnaire_taches.dao.impl.AdministratorDAOImpl;
import gestionnaire_taches.dao.impl.EmployeeDAOImpl;
import gestionnaire_taches.dao.impl.ServiceDAOImpl;
import gestionnaire_taches.dao.impl.TaskDAOImpl;
import gestionnaire_taches.model.User;
import gestionnaire_taches.model.Employee;
import gestionnaire_taches.model.Task;
import gestionnaire_taches.model.Service;
import gestionnaire_taches.util.SessionManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AdminDashboardView {

    // Zone de contenu central, remplacée selon le menu sélectionné
    private BorderPane mainView;
    private Button activeMenuButton = null;

    public BorderPane getView() {
        mainView = new BorderPane();
        mainView.setStyle("-fx-background-color: #f0f0f0;");

        mainView.setTop(createTopBar());
        mainView.setLeft(createSideMenu());
        mainView.setCenter(createWelcomeArea());

        return mainView;
    }

    // ─── BARRE SUPÉRIEURE ────────────────────────────────────────────────────

    private HBox createTopBar() {
        HBox topBar = new HBox();
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setPadding(new Insets(15, 30, 15, 30));
        topBar.setStyle("-fx-background-color: #2c3e50;");

        Label pageTitle = new Label("Gestion de Tâches – Administration");
        pageTitle.setStyle("-fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold;");
        HBox.setHgrow(pageTitle, javafx.scene.layout.Priority.ALWAYS);

        // Nom de l'utilisateur connecté
        User currentUser = SessionManager.getInstance().getCurrentUser();
        String userName = currentUser != null ? currentUser.getNom() : "Administrateur";

        Label userLabel = new Label("👤 " + userName);
        userLabel.setStyle("-fx-text-fill: #ecf0f1; -fx-font-size: 14px;");

        Button logoutBtn = new Button("Déconnexion");
        logoutBtn.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-cursor: hand;");
        logoutBtn.setOnAction(e -> gestionnaire_taches.Main.showLoginView());

        HBox rightBox = new HBox(15, userLabel, logoutBtn);
        rightBox.setAlignment(Pos.CENTER_RIGHT);

        topBar.getChildren().addAll(pageTitle, rightBox);
        return topBar;
    }

    // ─── MENU LATÉRAL ────────────────────────────────────────────────────────

    private VBox createSideMenu() {
        VBox sideMenu = new VBox(5);
        sideMenu.setPadding(new Insets(20, 10, 20, 10));
        sideMenu.setMinWidth(220);
        sideMenu.setStyle("-fx-background-color: #34495e;");

        Label logo = new Label("📊 Menu");
        logo.setStyle("-fx-text-fill: #bdc3c7; -fx-font-size: 13px; -fx-padding: 0 0 10 5;");

        Button dashboardBtn = createMenuButton("🏠  Tableau de bord");
        Button servicesBtn  = createMenuButton("🏢  Services");
        Button employeesBtn = createMenuButton("👥  Employés");
        Button tasksBtn     = createMenuButton("✅  Tâches");

        // Actions du menu
        dashboardBtn.setOnAction(e -> {
            setActiveButton(dashboardBtn);
            mainView.setCenter(createWelcomeArea());
        });

        servicesBtn.setOnAction(e -> {
            setActiveButton(servicesBtn);
            List<gestionnaire_taches.model.Service> list = new ServiceDAOImpl().findAll();
            ObservableList<gestionnaire_taches.model.Service> obs = FXCollections.observableArrayList(list);
            mainView.setCenter(new ServiceListView(obs).getView());
        });

        employeesBtn.setOnAction(e -> {
            setActiveButton(employeesBtn);
            List<Employee> list = new EmployeeDAOImpl().findAll();
            ObservableList<Employee> obs = FXCollections.observableArrayList(list);
            mainView.setCenter(new EmployeeListView(obs).getView());
        });

        tasksBtn.setOnAction(e -> {
            setActiveButton(tasksBtn);
            List<Task> list = new TaskDAOImpl().findAll();
            ObservableList<Task> obs = FXCollections.observableArrayList(list);
            mainView.setCenter(new TaskListView(obs).getView());
        });

        // Sélection initiale
        setActiveButton(dashboardBtn);

        sideMenu.getChildren().addAll(logo, dashboardBtn, servicesBtn, employeesBtn, tasksBtn);
        return sideMenu;
    }

    private Button createMenuButton(String text) {
        Button btn = new Button(text);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setAlignment(Pos.CENTER_LEFT);
        btn.setPadding(new Insets(12, 15, 12, 15));
        btn.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-cursor: hand; -fx-font-size: 14px;");
        btn.setOnMouseEntered(e -> {
            if (btn != activeMenuButton)
                btn.setStyle("-fx-background-color: #3d566e; -fx-text-fill: white; -fx-cursor: hand; -fx-font-size: 14px;");
        });
        btn.setOnMouseExited(e -> {
            if (btn != activeMenuButton)
                btn.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-cursor: hand; -fx-font-size: 14px;");
        });
        return btn;
    }

    private void setActiveButton(Button btn) {
        if (activeMenuButton != null)
            activeMenuButton.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-cursor: hand; -fx-font-size: 14px;");
        activeMenuButton = btn;
        btn.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-cursor: hand; -fx-font-size: 14px;");
    }

    // ─── PAGE D'ACCUEIL ──────────────────────────────────────────────────────

    private VBox createWelcomeArea() {
        VBox content = new VBox(25);
        content.setPadding(new Insets(30));
        content.setStyle("-fx-background-color: #ecf0f1;");

        // Message d'accueil personnalisé
        User currentUser = SessionManager.getInstance().getCurrentUser();
        String userName = currentUser != null ? currentUser.getNom() : "Administrateur";
        String heure = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
        String date  = LocalDateTime.now().format(DateTimeFormatter.ofPattern("EEEE dd MMMM yyyy", java.util.Locale.FRENCH));

        VBox welcomeBox = new VBox(6);
        welcomeBox.setPadding(new Insets(20, 25, 20, 25));
        welcomeBox.setStyle("-fx-background-color: white; -fx-background-radius: 10;");

        Label welcomeLabel = new Label("Bonjour, " + userName + " 👋");
        welcomeLabel.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        welcomeLabel.setStyle("-fx-text-fill: #2c3e50;");

        Label dateLabel = new Label(date + "  –  " + heure);
        dateLabel.setStyle("-fx-text-fill: #7f8c8d; -fx-font-size: 14px;");

        Label introLabel = new Label("Voici un aperçu de l'état de votre système de gestion de tâches.");
        introLabel.setStyle("-fx-text-fill: #555; -fx-font-size: 13px;");

        welcomeBox.getChildren().addAll(welcomeLabel, dateLabel, introLabel);

        // Statistiques dynamiques depuis la base de données
        long nbServices  = new ServiceDAOImpl().count();
        long nbEmployees = new EmployeeDAOImpl().count();
        long nbTasks     = new TaskDAOImpl().count();
        long nbAdmins    = new AdministratorDAOImpl().count();

        Label statsTitle = new Label("Vue d'ensemble");
        statsTitle.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        statsTitle.setStyle("-fx-text-fill: #2c3e50;");

        HBox statsCards = new HBox(20);
        statsCards.setAlignment(Pos.CENTER_LEFT);
        statsCards.getChildren().addAll(
            createStatCard("🏢 Services",   String.valueOf(nbServices),  "services actifs",   "#3498db"),
            createStatCard("👥 Employés",   String.valueOf(nbEmployees), "employés inscrits", "#2ecc71"),
            createStatCard("✅ Tâches",     String.valueOf(nbTasks),     "tâches en base",    "#f39c12"),
            createStatCard("🔑 Admins",     String.valueOf(nbAdmins),    "administrateurs",   "#9b59b6")
        );

        // Raccourcis rapides
        Label shortcutsTitle = new Label("Accès rapide");
        shortcutsTitle.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        shortcutsTitle.setStyle("-fx-text-fill: #2c3e50;");

        HBox shortcuts = new HBox(15);
        Button goServices  = createShortcutBtn("🏢 Gérer les services", "#3498db");
        Button goEmployees = createShortcutBtn("👥 Gérer les employés", "#2ecc71");
        Button goTasks     = createShortcutBtn("✅ Gérer les tâches",   "#f39c12");

        goServices.setOnAction(e -> {
            List<gestionnaire_taches.model.Service> list = new ServiceDAOImpl().findAll();
            mainView.setCenter(new ServiceListView(FXCollections.observableArrayList(list)).getView());
        });
        goEmployees.setOnAction(e -> {
            List<Employee> list = new EmployeeDAOImpl().findAll();
            mainView.setCenter(new EmployeeListView(FXCollections.observableArrayList(list)).getView());
        });
        goTasks.setOnAction(e -> {
            List<Task> list = new TaskDAOImpl().findAll();
            mainView.setCenter(new TaskListView(FXCollections.observableArrayList(list)).getView());
        });

        shortcuts.getChildren().addAll(goServices, goEmployees, goTasks);

        content.getChildren().addAll(welcomeBox, statsTitle, statsCards, shortcutsTitle, shortcuts);
        return content;
    }

    private VBox createStatCard(String title, String value, String subtitle, String color) {
        VBox card = new VBox(5);
        card.setStyle("-fx-background-color: " + color + "; -fx-background-radius: 10; -fx-padding: 20;");
        card.setPrefWidth(180);

        Label t = new Label(title);
        t.setStyle("-fx-text-fill: white; -fx-font-size: 14px;");
        Label v = new Label(value);
        v.setStyle("-fx-text-fill: white; -fx-font-size: 30px; -fx-font-weight: bold;");
        Label s = new Label(subtitle);
        s.setStyle("-fx-text-fill: white; -fx-font-size: 12px;");

        card.getChildren().addAll(t, v, s);
        return card;
    }

    private Button createShortcutBtn(String text, String color) {
        Button btn = new Button(text);
        btn.setStyle("-fx-background-color: " + color + "; -fx-text-fill: white; -fx-font-size: 14px; "
                + "-fx-padding: 12 20 12 20; -fx-background-radius: 8; -fx-cursor: hand;");
        return btn;
    }
}