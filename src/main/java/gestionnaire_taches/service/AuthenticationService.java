package gestionnaire_taches.service;

import gestionnaire_taches.dao.interfaces.AdministratorDAO;
import gestionnaire_taches.dao.interfaces.EmployeeDAO;
import gestionnaire_taches.dao.impl.AdministratorDAOImpl;
import gestionnaire_taches.dao.impl.EmployeeDAOImpl;
import gestionnaire_taches.model.Administrator;
import gestionnaire_taches.model.Employee;
import gestionnaire_taches.model.User;
import gestionnaire_taches.util.SessionManager;
import gestionnaire_taches.util.ValidationUtils;

public class AuthenticationService {
    
    private AdministratorDAO adminDAO;
    private EmployeeDAO employeeDAO;
    
    public AuthenticationService() {
        this.adminDAO = new AdministratorDAOImpl();
        this.employeeDAO = new EmployeeDAOImpl();
    }
    
    /**
     * Authenticate a user with email and password
     * @param email the user's email
     * @param password the user's password
     * @return true if authentication is successful, false otherwise
     */
    public boolean authenticate(String email, String password) {
        if (!ValidationUtils.isValidEmail(email)) {
            System.err.println("Format d'email invalide");
            return false;
        }
        
        if (password == null || password.isEmpty()) {
            System.err.println("Le mot de passe ne peut pas être vide");
            return false;
        }
        
        // Try to authenticate as administrator first
        Administrator admin = adminDAO.authenticate(email, password).orElse(null);
        if (admin != null) {
            SessionManager.getInstance().login(admin);
            return true;
        }
        
        // Try to authenticate as employee
        Employee employee = employeeDAO.authenticate(email, password).orElse(null);
        if (employee != null) {
            SessionManager.getInstance().login(employee);
            return true;
        }
        
        return false;
    }
    
    /**
     * Register a new administrator
     * @param nom the administrator's name
     * @param email the administrator's email
     * @param password the administrator's password
     * @return true if registration is successful, false otherwise
     */
    public boolean registerAdministrator(String nom, String email, String password) {
        if (!ValidationUtils.isValidEmail(email)) {
            System.err.println("Format d'email invalide : " + email);
            return false;
        }
        
        if (!ValidationUtils.isValidPassword(password)) {
            System.err.println("Format de mot de passe invalide. Le mot de passe doit comporter au moins 8 caractères avec majuscule, minuscule et chiffre.");
            return false;
        }
        
        if (!ValidationUtils.isNotNullOrEmpty(nom, "Nom")) {
            return false;
        }
        
        // Check if email already exists
        if (adminDAO.findByEmail(email).isPresent()) {
            System.err.println("Un administrateur avec l'email " + email + " existe déjà");
            return false;
        }
        
        Administrator admin = new Administrator(nom, email, password);
        adminDAO.save(admin);
        return true;
    }
    
    /**
     * Logout the current user
     */
    public void logout() {
        SessionManager.getInstance().logout();
    }
    
    /**
     * Get the currently logged in user
     * @return the current user or null if not logged in
     */
    public User getCurrentUser() {
        return SessionManager.getInstance().getCurrentUser();
    }
    
    /**
     * Check if a user is currently logged in
     * @return true if a user is logged in, false otherwise
     */
    public boolean isLoggedIn() {
        return SessionManager.getInstance().isLoggedIn();
    }
    
    /**
     * Check if the current user is an administrator
     * @return true if the current user is an administrator, false otherwise
     */
    public boolean isAdministrator() {
        return SessionManager.getInstance().isAdministrator();
    }
    
    /**
     * Check if the current user is a super administrator
     * @return true if the current user is a super administrator, false otherwise
     */
    public boolean isSuperAdmin() {
        return SessionManager.getInstance().isSuperAdmin();
    }
}