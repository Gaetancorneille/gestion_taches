package gestionnaire_taches.view;

import gestionnaire_taches.model.User;
import gestionnaire_taches.util.SessionManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

/**
 * AppShell — Layout persistant de l'application.
 *
 * Structure :
 *   ┌─────────────────────────────────────┐
 *   │              TOP BAR                │
 *   ├──────────┬──────────────────────────┤
 *   │          │                          │
 *   │ SIDEBAR  │      CONTENT AREA        │
 *   │          │                          │
 *   ├──────────┴──────────────────────────┤
 *   │              FOOTER                 │
 *   └─────────────────────────────────────┘
 *
 * La sidebar et le footer ne sont JAMAIS recréés,
 * seule la zone centrale (contentArea) est remplacée lors de la navigation.
 */
public class AppShell {

    // ── Noms des contributeurs ──────────────────────────────────────────────
    // TODO : remplace cette liste par les vrais noms de ton équipe
    private static final String[] CONTRIBUTORS = {
        "EMBONG Gaetan",
        "MABONG Verane",
        "DOUANLA Loreille",
        "KAMCHIE Megane",
        "DJOFANG Paul",
        "HESSEL Anne Gloria",
        "ATANGANA Adrienne",
        "DACLEU Dimitri",
        "DONGMO Duhamel",
        "EKWA Charles",
        "NYANGONO Armand",
        "DIFFO Delor",
        "ABESSOLO Sabrina",
        "TIENTCHEU Christian"
    };

    // ── Palette de couleurs ─────────────────────────────────────────────────
    private static final String COLOR_PRIMARY      = "#1565C0"; // Bleu corporate foncé
    private static final String COLOR_PRIMARY_DARK = "#0D47A1"; // Bleu marine
    private static final String COLOR_SIDEBAR_BG   = "#1E2A3A"; // Sidebar sombre
    private static final String COLOR_SIDEBAR_ITEM = "#2C3E50"; // Item hover sidebar
    private static final String COLOR_ACCENT       = "#1976D2"; // Bleu accent
    private static final String COLOR_ACTIVE       = "#1565C0"; // Bouton actif
    private static final String COLOR_BG           = "#F5F7FA"; // Fond général
    private static final String COLOR_TOPBAR       = "#FFFFFF"; // Topbar blanche
    private static final String COLOR_FOOTER       = "#1E2A3A"; // Footer sombre
    private static final String COLOR_TEXT_MUTED   = "#7F8C8D";

    // ── Layout ──────────────────────────────────────────────────────────────
    private final BorderPane shell;
    private final StackPane  contentArea;
    private Button activeMenuButton = null;

    // Type de dashboard affiché (pour reconstruire la sidebar)
    public enum DashboardType { ADMIN, EMPLOYEE }
    private final DashboardType dashboardType;

    public AppShell(DashboardType type) {
        this.dashboardType = type;
        this.contentArea   = new StackPane();
        this.contentArea.setStyle("-fx-background-color: " + COLOR_BG + ";");

        shell = new BorderPane();
        shell.setTop(createTopBar());
        shell.setLeft(createSideMenu());
        shell.setCenter(contentArea);
        shell.setBottom(createFooter());
    }

    /** Retourne le layout global à passer à Main */
    public BorderPane getShell() {
        return shell;
    }

    /**
     * Remplace le contenu central.
     * C'est la seule méthode à appeler depuis les vues et formulaires.
     */
    public void setContent(javafx.scene.Node content) {
        contentArea.getChildren().setAll(content);
    }

    // ── TOP BAR ─────────────────────────────────────────────────────────────

    private HBox createTopBar() {
        HBox topBar = new HBox();
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setPadding(new Insets(0, 25, 0, 0));
        topBar.setMinHeight(60);
        topBar.setStyle(
            "-fx-background-color: " + COLOR_TOPBAR + ";" +
            "-fx-border-color: #E0E6ED;" +
            "-fx-border-width: 0 0 1 0;"
        );

        // Zone titre (couleur primaire)
        HBox brandBox = new HBox();
        brandBox.setMinWidth(220);
        brandBox.setAlignment(Pos.CENTER_LEFT);
        brandBox.setPadding(new Insets(0, 20, 0, 20));
        brandBox.setStyle("-fx-background-color: " + COLOR_PRIMARY + ";");

        Label brandLabel = new Label("TaskManager Pro");
        brandLabel.setStyle(
            "-fx-text-fill: white;" +
            "-fx-font-size: 16px;" +
            "-fx-font-weight: bold;"
        );
        brandBox.getChildren().add(brandLabel);

        // Spacer
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // Infos utilisateur
        User currentUser = SessionManager.getInstance().getCurrentUser();
        String userName = currentUser != null ? currentUser.getNom() : "Utilisateur";
        String roleLabel = dashboardType == DashboardType.ADMIN ? "Administrateur" : "Employé";

        VBox userInfo = new VBox(1);
        userInfo.setAlignment(Pos.CENTER_RIGHT);
        Label nameLabel = new Label(userName);
        nameLabel.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: #2C3E50;");
        Label roleTag = new Label(roleLabel);
        roleTag.setStyle("-fx-font-size: 11px; -fx-text-fill: " + COLOR_TEXT_MUTED + ";");
        userInfo.getChildren().addAll(nameLabel, roleTag);

        // Avatar cercle
        Label avatar = new Label(userName.substring(0, 1).toUpperCase());
        avatar.setMinSize(36, 36);
        avatar.setMaxSize(36, 36);
        avatar.setAlignment(Pos.CENTER);
        avatar.setStyle(
            "-fx-background-color: " + COLOR_ACCENT + ";" +
            "-fx-background-radius: 18;" +
            "-fx-text-fill: white;" +
            "-fx-font-weight: bold;" +
            "-fx-font-size: 14px;"
        );

        // Bouton déconnexion
        Button logoutBtn = new Button("Déconnexion");
        logoutBtn.setStyle(
            "-fx-background-color: transparent;" +
            "-fx-text-fill: #E74C3C;" +
            "-fx-border-color: #E74C3C;" +
            "-fx-border-radius: 4;" +
            "-fx-background-radius: 4;" +
            "-fx-cursor: hand;" +
            "-fx-font-size: 12px;" +
            "-fx-padding: 6 12 6 12;"
        );
        logoutBtn.setOnMouseEntered(e ->
            logoutBtn.setStyle(
                "-fx-background-color: #E74C3C;" +
                "-fx-text-fill: white;" +
                "-fx-border-color: #E74C3C;" +
                "-fx-border-radius: 4;" +
                "-fx-background-radius: 4;" +
                "-fx-cursor: hand;" +
                "-fx-font-size: 12px;" +
                "-fx-padding: 6 12 6 12;"
            )
        );
        logoutBtn.setOnMouseExited(e ->
            logoutBtn.setStyle(
                "-fx-background-color: transparent;" +
                "-fx-text-fill: #E74C3C;" +
                "-fx-border-color: #E74C3C;" +
                "-fx-border-radius: 4;" +
                "-fx-background-radius: 4;" +
                "-fx-cursor: hand;" +
                "-fx-font-size: 12px;" +
                "-fx-padding: 6 12 6 12;"
            )
        );
        logoutBtn.setOnAction(e -> gestionnaire_taches.Main.showLoginView());

        HBox rightBox = new HBox(15, userInfo, avatar, logoutBtn);
        rightBox.setAlignment(Pos.CENTER);

        topBar.getChildren().addAll(brandBox, spacer, rightBox);
        return topBar;
    }

    // ── SIDEBAR ─────────────────────────────────────────────────────────────

    private VBox createSideMenu() {
        VBox sidebar = new VBox(4);
        sidebar.setMinWidth(220);
        sidebar.setMaxWidth(220);
        sidebar.setPadding(new Insets(20, 0, 20, 0));
        sidebar.setStyle("-fx-background-color: " + COLOR_SIDEBAR_BG + ";");

        // Section label
        Label sectionLabel = new Label("NAVIGATION");
        sectionLabel.setStyle(
            "-fx-text-fill: #5D7A8A;" +
            "-fx-font-size: 10px;" +
            "-fx-font-weight: bold;" +
            "-fx-padding: 0 0 8 20;"
        );

        sidebar.getChildren().add(sectionLabel);

        if (dashboardType == DashboardType.ADMIN) {
            buildAdminMenu(sidebar);
        } else {
            buildEmployeeMenu(sidebar);
        }

        return sidebar;
    }

    private void buildAdminMenu(VBox sidebar) {
        Button dashboardBtn = createSidebarButton("🏠", "Tableau de bord");
        Button servicesBtn  = createSidebarButton("🏢", "Services");
        Button employeesBtn = createSidebarButton("👥", "Employés");
        Button tasksBtn     = createSidebarButton("✅", "Tâches");

        dashboardBtn.setOnAction(e -> {
            setActiveSidebarButton(dashboardBtn);
            setContent(new AdminDashboardView(this).getView());
        });

        servicesBtn.setOnAction(e -> {
            setActiveSidebarButton(servicesBtn);
            java.util.List<gestionnaire_taches.model.Service> list =
                new gestionnaire_taches.dao.impl.ServiceDAOImpl().findAll();
            setContent(new ServiceListView(
                javafx.collections.FXCollections.observableArrayList(list)).getView());
        });

        employeesBtn.setOnAction(e -> {
            setActiveSidebarButton(employeesBtn);
            java.util.List<gestionnaire_taches.model.Employee> list =
                new gestionnaire_taches.dao.impl.EmployeeDAOImpl().findAll();
            setContent(new EmployeeListView(
                javafx.collections.FXCollections.observableArrayList(list)).getView());
        });

        tasksBtn.setOnAction(e -> {
            setActiveSidebarButton(tasksBtn);
            java.util.List<gestionnaire_taches.model.Task> list =
                new gestionnaire_taches.dao.impl.TaskDAOImpl().findAll();
            setContent(new TaskListView(
                javafx.collections.FXCollections.observableArrayList(list)).getView());
        });

        setActiveSidebarButton(dashboardBtn);
        setContent(new AdminDashboardView(this).getView());

        sidebar.getChildren().addAll(dashboardBtn, servicesBtn, employeesBtn, tasksBtn);
    }

    private void buildEmployeeMenu(VBox sidebar) {
        Button dashboardBtn = createSidebarButton("🏠", "Tableau de bord");
        Button myTasksBtn   = createSidebarButton("✅", "Mes tâches");

        dashboardBtn.setOnAction(e -> {
            setActiveSidebarButton(dashboardBtn);
            gestionnaire_taches.model.User u = gestionnaire_taches.util.SessionManager.getInstance().getCurrentUser();
            String name = u != null ? u.getNom() : "Employé";
            setContent(new EmployeeDashboardView(name).getView());
        });

        myTasksBtn.setOnAction(e -> {
            setActiveSidebarButton(myTasksBtn);
            java.util.List<gestionnaire_taches.model.Task> list =
                new gestionnaire_taches.dao.impl.TaskDAOImpl().findAll();
            setContent(new TaskListView(
                javafx.collections.FXCollections.observableArrayList(list)).getView());
        });

        setActiveSidebarButton(dashboardBtn);
        sidebar.getChildren().addAll(dashboardBtn, myTasksBtn);
    }

    private Button createSidebarButton(String icon, String label) {
        HBox content = new HBox(12);
        content.setAlignment(Pos.CENTER_LEFT);
        Label iconLabel = new Label(icon);
        iconLabel.setStyle("-fx-font-size: 15px;");
        Label textLabel = new Label(label);
        textLabel.setStyle("-fx-text-fill: #BDC3C7; -fx-font-size: 13px;");
        content.getChildren().addAll(iconLabel, textLabel);

        Button btn = new Button();
        btn.setGraphic(content);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setAlignment(Pos.CENTER_LEFT);
        btn.setPadding(new Insets(11, 15, 11, 20));
        btn.setStyle(
            "-fx-background-color: transparent;" +
            "-fx-cursor: hand;" +
            "-fx-border-width: 0;"
        );

        btn.setOnMouseEntered(e -> {
            if (btn != activeMenuButton)
                btn.setStyle("-fx-background-color: " + COLOR_SIDEBAR_ITEM + "; -fx-cursor: hand;");
        });
        btn.setOnMouseExited(e -> {
            if (btn != activeMenuButton)
                btn.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");
        });

        return btn;
    }

    private void setActiveSidebarButton(Button btn) {
        if (activeMenuButton != null) {
            activeMenuButton.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");
        }
        activeMenuButton = btn;
        btn.setStyle(
            "-fx-background-color: " + COLOR_ACTIVE + ";" +
            "-fx-cursor: hand;" +
            "-fx-border-width: 0 0 0 3;" +
            "-fx-border-color: white;"
        );
    }

    // ── FOOTER ──────────────────────────────────────────────────────────────

    private HBox createFooter() {
        HBox footer = new HBox();
        footer.setAlignment(Pos.CENTER);
        footer.setMinHeight(52);
        footer.setPadding(new Insets(8, 20, 8, 20));
        footer.setStyle("-fx-background-color: " + COLOR_FOOTER + ";");

        // Séparation en deux lignes pour 14 noms
        String[] line1 = { CONTRIBUTORS[0], CONTRIBUTORS[1], CONTRIBUTORS[2],
                            CONTRIBUTORS[3], CONTRIBUTORS[4], CONTRIBUTORS[5],
                            CONTRIBUTORS[6] };
        String[] line2 = { CONTRIBUTORS[7], CONTRIBUTORS[8], CONTRIBUTORS[9],
                            CONTRIBUTORS[10], CONTRIBUTORS[11], CONTRIBUTORS[12],
                            CONTRIBUTORS[13] };

        String row1 = String.join("  ·  ", line1);
        String row2 = String.join("  ·  ", line2);

        VBox textBox = new VBox(3);
        textBox.setAlignment(Pos.CENTER);

        Label footerLine1 = new Label(row1);
        footerLine1.setStyle("-fx-text-fill: #7F9AAA; -fx-font-size: 11px;");

        Label footerLine2 = new Label(row2);
        footerLine2.setStyle("-fx-text-fill: #7F9AAA; -fx-font-size: 11px;");

        Label copyright = new Label("© 2025 TaskManager Pro");
        copyright.setStyle("-fx-text-fill: #5D7A8A; -fx-font-size: 10px;");

        // Copyright à gauche, noms au centre
        Region spacerLeft = new Region();
        HBox.setHgrow(spacerLeft, Priority.ALWAYS);
        Region spacerRight = new Region();
        HBox.setHgrow(spacerRight, Priority.ALWAYS);

        textBox.getChildren().addAll(footerLine1, footerLine2);
        footer.getChildren().addAll(copyright, spacerLeft, textBox, spacerRight);
        return footer;
    }

    // ── MÉTHODE UTILITAIRE PUBLIQUE ──────────────────────────────────────────

    /**
     * Navigue vers une vue depuis n'importe quel formulaire.
     * Exemple d'usage dans TaskFormView :
     *   AppShell shell = gestionnaire_taches.Main.getAppShell();
     *   shell.setContent(new TaskListView(...).getView());
     */
    public void navigateTo(javafx.scene.Node view) {
        setContent(view);
    }
}