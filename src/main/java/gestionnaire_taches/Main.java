package gestionnaire_taches;

import java.sql.SQLException;

import gestionnaire_taches.util.DatabaseConnection;
import gestionnaire_taches.view.AppShell;
import gestionnaire_taches.view.LoginView;
import gestionnaire_taches.view.RegisterView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Classe principale de l'application de gestion de tâches.
 *
 * Architecture de navigation :
 *   - Avant connexion : LoginView / RegisterView (plein écran, sans shell)
 *   - Après connexion : AppShell persistant (topbar + sidebar + footer)
 *                       → seul le centre change selon la navigation
 */
public class Main extends Application {

    private static Stage primaryStage;

    // Shell persistant (topbar + sidebar + footer)
    // Une instance par session — recréé uniquement à la connexion
    private static AppShell currentShell;

    @Override
    public void start(Stage stage) {
        primaryStage = stage;
        primaryStage.setTitle("TaskManager Pro");
        primaryStage.setMinWidth(1200);
        primaryStage.setMinHeight(700);

        // Vérifier la connexion à la base de données
        try {
            DatabaseConnection.getConnection();
            System.out.println("✔ Connexion à la base de données réussie !");
        } catch (SQLException e) {
            System.err.println("✘ Échec de la connexion à la base de données : " + e.getMessage());
        }

        // Démarrer sur l'écran de connexion
        showLoginView();

        primaryStage.show();
    }

    // ── Vues avant connexion (sans shell) ────────────────────────────────────

    /** Affiche l'écran de connexion (plein écran, sans sidebar) */
    public static void showLoginView() {
        try {
            currentShell = null; // Libérer le shell précédent
            LoginView loginView = new LoginView();
            Scene scene = new Scene(loginView.getView(), 1200, 700);
            primaryStage.setScene(scene);
        } catch (Exception e) {
            System.err.println("Erreur LoginView : " + e.getMessage());
            e.printStackTrace();
        }
    }

    /** Affiche l'écran d'inscription */
    public static void showRegisterView() {
        try {
            RegisterView registerView = new RegisterView();
            Scene scene = new Scene(registerView.getView(), 1200, 700);
            primaryStage.setScene(scene);
        } catch (Exception e) {
            System.err.println("Erreur RegisterView : " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ── Vues après connexion (avec shell persistant) ─────────────────────────

    /**
     * Initialise le shell Admin et affiche le tableau de bord.
     * Appelé une seule fois à la connexion d'un admin.
     */
    public static void showAdminDashboard() {
        try {
            currentShell = new AppShell(AppShell.DashboardType.ADMIN);
            Scene scene = new Scene(currentShell.getShell(), 1200, 700);
            primaryStage.setScene(scene);
        } catch (Exception e) {
            System.err.println("Erreur AdminDashboard : " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Initialise le shell Employé et affiche le tableau de bord.
     * Appelé une seule fois à la connexion d'un employé.
     */
    public static void showEmployeeDashboard(String employeeName) {
        try {
            currentShell = new AppShell(AppShell.DashboardType.EMPLOYEE);
            Scene scene = new Scene(currentShell.getShell(), 1200, 700);
            primaryStage.setScene(scene);
        } catch (Exception e) {
            System.err.println("Erreur EmployeeDashboard : " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ── Navigation depuis les formulaires ────────────────────────────────────

    /**
     * Retourne le shell actif pour permettre la navigation depuis les formulaires.
     *
     * Usage dans TaskFormView (bouton Annuler / Enregistrer) :
     *
     *   AppShell shell = gestionnaire_taches.Main.getAppShell();
     *   if (shell != null) {
     *       shell.navigateTo(new TaskListView(...).getView());
     *   }
     *
     * Remplace l'ancien : gestionnaire_taches.Main.getMainLayout().setCenter(...)
     */
    public static AppShell getAppShell() {
        return currentShell;
    }

    // ── Cycle de vie ──────────────────────────────────────────────────────────

    @Override
    public void stop() {
        DatabaseConnection.closeConnection();
        System.out.println("Application fermée proprement.");
    }

    public static void main(String[] args) {
        launch(args);
    }
}