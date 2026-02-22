package gestionnaire_taches;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import gestionnaire_taches.util.DatabaseConnection;
import gestionnaire_taches.view.LoginView;
import gestionnaire_taches.view.AdminDashboardView;
import gestionnaire_taches.view.EmployeeDashboardView;
import gestionnaire_taches.view.RegisterView;
import java.sql.SQLException;

/**
 * Classe principale de l'application de gestion de tâches
 * Point d'entrée qui démarre l'interface graphique JavaFX
 */
public class Main extends Application {
    
    // Fenêtre principale de l'application
    private static Stage primaryStage;
    
    // Conteneur principal pour organiser l'interface
    private static BorderPane mainLayout;
    
    // Références aux vues pour navigation
    private static LoginView loginView;
    private static AdminDashboardView adminDashboardView;
    private static EmployeeDashboardView employeeDashboardView;
    private static RegisterView registerView;
    
    /**
     * Méthode appelée au démarrage de l'application
     * @param stage Fenêtre principale fournie par JavaFX
     */
    @Override
    public void start(Stage stage) {
        primaryStage = stage;
        primaryStage.setTitle("Gestion de Tâches - Application Entreprise");
        primaryStage.setMinWidth(1200);
        primaryStage.setMinHeight(700);
        
        // Vérifier la connexion à la base de données
        try {
            DatabaseConnection.getConnection();
            System.out.println("Connexion à la base de données réussie !");
        } catch (SQLException e) {
            System.err.println("Échec de la connexion à la base de données : " + e.getMessage());
            // On continue quand même - l'application doit gérer cela proprement
        }
        
        // Créer la structure principale de l'interface
        mainLayout = new BorderPane();
        
        // Afficher l'écran de connexion
        showLoginView();
        
        Scene scene = new Scene(mainLayout, 1200, 700);
        // scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
        
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    /**
     * Affiche l'écran de connexion
     * Cette méthode peut être appelée depuis n'importe où dans l'application
     */
    public static void showLoginView() {
        try {
            loginView = new LoginView();
            mainLayout.setCenter(loginView.getView());
        } catch (Exception e) {
            System.err.println("Erreur lors de la création de l'écran de connexion : " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Affiche le tableau de bord administrateur
     */
    public static void showAdminDashboard() {
        try {
            adminDashboardView = new AdminDashboardView();
            mainLayout.setCenter(adminDashboardView.getView());
        } catch (Exception e) {
            System.err.println("Erreur lors de la création du tableau de bord administrateur : " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Affiche le tableau de bord employé
     * @param employeeName Nom de l'employé connecté
     */
    public static void showEmployeeDashboard(String employeeName) {
        try {
            employeeDashboardView = new EmployeeDashboardView(employeeName);
            mainLayout.setCenter(employeeDashboardView.getView());
        } catch (Exception e) {
            System.err.println("Erreur lors de la création du tableau de bord employé : " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Affiche l'écran d'inscription
     */
    public static void showRegisterView() {
        try {
            registerView = new RegisterView();
            mainLayout.setCenter(registerView.getView());
        } catch (Exception e) {
            System.err.println("Erreur lors de la création de l'écran d'inscription : " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Retourne le layout principal pour permettre la navigation depuis les vues
     * @return BorderPane principal de l'application
     */
    public static BorderPane getMainLayout() {
        return mainLayout;
    }

    /**
     * Point d'entrée principal de l'application
     * @param args Arguments de la ligne de commande
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    /**
     * Méthode appelée à la fermeture de l'application
     * Nettoie les ressources utilisées
     */
    @Override
    public void stop() {
        // Fermer proprement la connexion à la base de données
        DatabaseConnection.closeConnection();
        System.out.println("Application fermée. Connexion à la base de données terminée.");
    }
}