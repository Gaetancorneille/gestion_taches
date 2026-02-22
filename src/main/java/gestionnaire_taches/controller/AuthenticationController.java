package gestionnaire_taches.controller;

import gestionnaire_taches.service.AuthenticationService;
import gestionnaire_taches.model.User;
import gestionnaire_taches.util.SessionManager;

public class AuthenticationController {
    
    private AuthenticationService authService;
    
    public AuthenticationController() {
        this.authService = new AuthenticationService();
    }
    
    /**
     * Attempt to log in a user with email and password
     * @param email the user's email
     * @param password the user's password
     * @return true if login is successful, false otherwise
     */
    public boolean login(String email, String password) {
        return authService.authenticate(email, password);
    }
    
    /**
     * Log out the current user
     */
    public void logout() {
        authService.logout();
    }
    
    /**
     * Register a new administrator
     * @param name the administrator's name
     * @param email the administrator's email
     * @param password the administrator's password
     * @return true if registration is successful, false otherwise
     */
    public boolean registerAdministrator(String name, String email, String password) {
        return authService.registerAdministrator(name, email, password);
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