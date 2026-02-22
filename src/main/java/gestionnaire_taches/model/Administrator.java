package gestionnaire_taches.model;

import java.time.LocalDateTime;

/**
 * Classe représentant un administrateur du système
 * Hérite de la classe User et ajoute des fonctionnalités spécifiques aux administrateurs
 * Un administrateur peut gérer des services et des employés
 */
public class Administrator extends User {
    // Indique si c'est un super administrateur (true) ou un administrateur normal (false)
    private boolean isSuperAdmin;

    /**
     * Constructeur par défaut
     * Crée un administrateur normal (pas super admin)
     */
    public Administrator() {
        super();
        this.isSuperAdmin = false;
    }

    /**
     * Constructeur avec nom, email et mot de passe
     * @param nom Nom complet de l'administrateur
     * @param email Adresse email de l'administrateur
     * @param password Mot de passe de l'administrateur
     */
    public Administrator(String nom, String email, String password) {
        super(nom, email, password);
        this.isSuperAdmin = false;
    }

    /**
     * Constructeur avec tous les paramètres
     * @param id Numéro unique de l'administrateur
     * @param nom Nom complet de l'administrateur
     * @param email Adresse email de l'administrateur
     * @param password Mot de passe de l'administrateur
     */
    public Administrator(int id, String nom, String email, String password) {
        super(id, nom, email, password);
        this.isSuperAdmin = false;
    }

    /**
     * Vérifie si l'administrateur est un super administrateur
     * @return true si c'est un super admin, false sinon
     */
    public boolean isSuperAdmin() {
        return this.isSuperAdmin;
    }

    /**
     * Définit si l'administrateur est un super administrateur
     * @param isSuperAdmin true pour un super admin, false pour un admin normal
     */
    public void setSuperAdmin(boolean isSuperAdmin) {
        this.isSuperAdmin = isSuperAdmin;
    }

    /**
     * Vérifie si l'administrateur peut gérer d'autres administrateurs
     * Seuls les super administrateurs ont ce droit
     * @return true si l'administrateur peut gérer d'autres admins
     */
    public boolean canManageAdministrators() {
        return this.isSuperAdmin;
    }

    /**
     * Vérifie si l'administrateur peut gérer tous les services
     * Seuls les super administrateurs ont ce droit
     * @return true si l'administrateur peut gérer tous les services
     */
    public boolean canManageAllServices() {
        return this.isSuperAdmin;
    }

    /**
     * Affiche les informations de l'administrateur
     * @return Texte décrivant l'administrateur avec ses informations principales
     */
    @Override
    public String toString() {
        return "Administrator{" +
                "nom='" + getNom() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", isSuperAdmin=" + isSuperAdmin +
                '}';
    }
}