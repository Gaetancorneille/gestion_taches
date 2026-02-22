package gestionnaire_taches.model;

import java.time.LocalDateTime;

/**
 * Classe de base pour tous les utilisateurs de l'application
 * Contient les informations communes à tous les types d'utilisateurs
 * Cette classe est abstraite, ce qui signifie qu'on ne peut pas créer directement un objet User
 * On doit créer des objets des classes qui héritent de User (Administrator, Employee)
 */
public abstract class User {
    // Numéro unique qui identifie chaque utilisateur
    private int id;
    
    // Nom complet de la personne (prénom et nom)
    private String nom;
    
    // Adresse email utilisée pour se connecter
    private String email;
    
    // Mot de passe pour se connecter (stocké de manière sécurisée)
    private String password;
    
    // Date et heure de création du compte utilisateur
    private LocalDateTime dateCreation;
    
    // Indique si le compte est actif (true) ou désactivé (false)
    private boolean actif;
    
    /**
     * Constructeur par défaut
     * Crée un utilisateur avec des valeurs vides
     * La date de création est automatiquement définie à maintenant
     * Le compte est activé par défaut
     */
    public User() {
        this.dateCreation = LocalDateTime.now();
        this.actif = true;
    }
    
    /**
     * Constructeur avec nom, email et mot de passe
     * @param nom Nom complet de l'utilisateur
     * @param email Adresse email de l'utilisateur
     * @param password Mot de passe de l'utilisateur
     */
    public User(String nom, String email, String password) {
        this();
        this.nom = nom;
        this.email = email;
        this.password = password;
    }
    
    /**
     * Constructeur avec tous les paramètres
     * @param id Numéro unique de l'utilisateur
     * @param nom Nom complet de l'utilisateur
     * @param email Adresse email de l'utilisateur
     * @param password Mot de passe de l'utilisateur
     */
    public User(int id, String nom, String email, String password) {
        this(nom, email, password);
        this.id = id;
    }
    
    // Méthodes pour obtenir les informations de l'utilisateur (getters)
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getNom() {
        return nom;
    }
    
    public void setNom(String nom) {
        this.nom = nom;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public LocalDateTime getDateCreation() {
        return dateCreation;
    }
    
    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }
    
    public boolean isActif() {
        return actif;
    }
    
    public void setActif(boolean actif) {
        this.actif = actif;
    }
    
    /**
     * Méthode pour afficher les informations de l'utilisateur
     * @return Texte décrivant l'utilisateur avec ses informations principales
     */
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", email='" + email + '\'' +
                ", actif=" + actif +
                '}';
    }
}