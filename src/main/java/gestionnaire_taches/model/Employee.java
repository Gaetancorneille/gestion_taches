package gestionnaire_taches.model;

import java.time.LocalDate;
import java.util.List;

/**
 * Classe représentant un employé de l'entreprise
 * Hérite de la classe User et ajoute des informations spécifiques aux employés
 * Un employé appartient à un service et peut se voir assigner des tâches
 */
public class Employee extends User {
    // Numéro du service auquel l'employé appartient
    private int serviceId;
    
    // Intitulé du poste de l'employé (ex: Développeur, Designer, etc.)
    private String poste;
    
    // Date à laquelle l'employé a été embauché
    private LocalDate dateEmbauche;
    
    // Référence vers l'objet Service (informations détaillées du service)
    private Service service;

    /**
     * Constructeur par défaut
     * Crée un employé avec la date d'embauche définie à aujourd'hui
     */
    public Employee() {
        super();
        this.dateEmbauche = LocalDate.now();
    }

    /**
     * Constructeur avec les informations de base
     * @param nom Nom complet de l'employé
     * @param email Adresse email de l'employé
     * @param password Mot de passe de l'employé
     * @param serviceId Numéro du service auquel l'employé appartient
     */
    public Employee(String nom, String email, String password, int serviceId) {
        super(nom, email, password);
        this.serviceId = serviceId;
        this.dateEmbauche = LocalDate.now();
    }

    /**
     * Constructeur avec toutes les informations
     * @param id Numéro unique de l'employé
     * @param nom Nom complet de l'employé
     * @param email Adresse email de l'employé
     * @param password Mot de passe de l'employé
     * @param serviceId Numéro du service auquel l'employé appartient
     * @param poste Intitulé du poste de l'employé
     */
    public Employee(int id, String nom, String email, String password, int serviceId, String poste) {
        super(id, nom, email, password);
        this.serviceId = serviceId;
        this.poste = poste;
        this.dateEmbauche = LocalDate.now();
    }

    /**
     * Obtient le numéro du service de l'employé
     * @return Numéro du service
     */
    public int getServiceId() {
        return serviceId;
    }

    /**
     * Définit le numéro du service de l'employé
     * @param serviceId Numéro du service
     */
    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    /**
     * Obtient l'intitulé du poste de l'employé
     * @return Intitulé du poste
     */
    public String getPoste() {
        return poste;
    }

    /**
     * Définit l'intitulé du poste de l'employé
     * @param poste Intitulé du poste
     */
    public void setPoste(String poste) {
        this.poste = poste;
    }

    /**
     * Obtient la date d'embauche de l'employé
     * @return Date d'embauche
     */
    public LocalDate getDateEmbauche() {
        return dateEmbauche;
    }

    /**
     * Définit la date d'embauche de l'employé
     * @param dateEmbauche Date d'embauche
     */
    public void setDateEmbauche(LocalDate dateEmbauche) {
        this.dateEmbauche = dateEmbauche;
    }

    /**
     * Obtient l'objet Service complet de l'employé
     * @return Objet Service avec toutes les informations
     */
    public Service getService() {
        return service;
    }

    /**
     * Définit l'objet Service de l'employé
     * @param service Objet Service complet
     */
    public void setService(Service service) {
        this.service = service;
    }

    /**
     * Affiche les informations de l'employé
     * @return Texte décrivant l'employé avec ses informations principales
     */
    @Override
    public String toString() {
        return "Employee{" +
                "nom='" + getNom() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", poste='" + poste + '\'' +
                ", serviceId=" + serviceId +
                ", dateEmbauche=" + dateEmbauche +
                '}';
    }
}