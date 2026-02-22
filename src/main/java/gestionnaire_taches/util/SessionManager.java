package gestionnaire_taches.util;

import gestionnaire_taches.model.User;
import java.time.LocalDateTime;

public class SessionManager {
    private static SessionManager instance;
    private User currentUser;
    private LocalDateTime loginTime;
    
    private SessionManager() {}
    
    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }
    
    public void login(User user) {
        this.currentUser = user;
        this.loginTime = LocalDateTime.now();
    }
    
    public void logout() {
        this.currentUser = null;
        this.loginTime = null;
    }
    
    public User getCurrentUser() {
        return currentUser;
    }
    
    public boolean isLoggedIn() {
        return currentUser != null;
    }
    
    public boolean isAdministrator() {
        return currentUser != null && currentUser instanceof gestionnaire_taches.model.Administrator;
    }
    
    public boolean isSuperAdmin() {
        if (currentUser != null && currentUser instanceof gestionnaire_taches.model.Administrator) {
            gestionnaire_taches.model.Administrator admin = (gestionnaire_taches.model.Administrator) currentUser;
            return admin.isSuperAdmin();
        }
        return false;
    }
    
    public LocalDateTime getLoginTime() {
        return loginTime;
    }
}