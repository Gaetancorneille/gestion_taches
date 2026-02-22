package gestionnaire_taches.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import gestionnaire_taches.model.Task;
import gestionnaire_taches.model.Employee;

/**
 * Classe représentant un service de l'entreprise
 * Un service est géré par un administrateur et contient des employés
 * Il peut aussi avoir des tâches qui lui sont associées
 */
public class Service {
    // Numéro unique qui identifie le service
    private int id;
    
    // Nom du service (ex: Informatique, Ressources Humaines, etc.)
    private String nom;
    
    // Description détaillée de ce que fait le service
    private String description;
    
    // Numéro de l'administrateur qui gère ce service
    private int administratorId;
    
    // Date et heure de création du service
    private LocalDateTime dateCreation;
    
    // Indique si le service est actif (true) ou archivé (false)
    private boolean actif;
    
    // Référence vers l'objet Administrator (informations détaillées de l'administrateur)
    private Administrator administrator;
    
    // Liste de tous les employés qui appartiennent à ce service
    private List<Employee> employees;
    
    // Liste de toutes les tâches associées à ce service
    private List<Task> tasks;
    
    /**
     * Constructeur par défaut
     * Crée un service actif avec des listes vides pour les employés et tâches
     * La date de création est automatiquement définie à maintenant
     */
    public Service() {
        this.dateCreation = LocalDateTime.now();
        this.actif = true;
        this.employees = new ArrayList<>();
        this.tasks = new ArrayList<>();
    }
    
    /**
     * Constructeur avec les informations de base
     * @param nom Nom du service
     * @param description Description détaillée du service
     * @param administratorId Numéro de l'administrateur qui gère ce service
     */
    public Service(String nom, String description, int administratorId) {
        this();
        this.nom = nom;
        this.description = description;
        this.administratorId = administratorId;
    }
    
    /**
     * Constructeur avec tous les paramètres
     * @param id Numéro unique du service
     * @param nom Nom du service
     * @param description Description détaillée du service
     * @param administratorId Numéro de l'administrateur qui gère ce service
     */
    public Service(int id, String nom, String description, int administratorId) {
        this(nom, description, administratorId);
        this.id = id;
    }
    
    /**
     * Obtient le numéro unique du service
     * @return Numéro du service
     */
    public int getId() {
        return id;
    }
    
    /**
     * Définit le numéro unique du service
     * @param id Numéro du service
     */
    public void setId(int id) {
        this.id = id;
    }
    
    /**
     * Obtient le nom du service
     * @return Nom du service
     */
    public String getNom() {
        return nom;
    }
    
    /**
     * Définit le nom du service
     * @param nom Nom du service
     */
    public void setNom(String nom) {
        this.nom = nom;
    }
    
    /**
     * Obtient la description du service
     * @return Description du service
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * Définit la description du service
     * @param description Description du service
     */
    public void setDescription(String description) {
        this.description = description;
    }
    
    /**
     * Obtient le numéro de l'administrateur qui gère ce service
     * @return Numéro de l'administrateur
     */
    public int getAdministratorId() {
        return administratorId;
    }
    
    /**
     * Définit le numéro de l'administrateur qui gère ce service
     * @param administratorId Numéro de l'administrateur
     */
    public void setAdministratorId(int administratorId) {
        this.administratorId = administratorId;
    }
    
    /**
     * Obtient la date de création du service
     * @return Date de création
     */
    public LocalDateTime getDateCreation() {
        return dateCreation;
    }
    
    /**
     * Définit la date de création du service
     * @param dateCreation Date de création
     */
    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }
    
    /**
     * Vérifie si le service est actif
     * @return true si le service est actif, false s'il est archivé
     */
    public boolean isActif() {
        return actif;
    }
    
    /**
     * Définit si le service est actif ou archivé
     * @param actif true pour actif, false pour archivé
     */
    public void setActif(boolean actif) {
        this.actif = actif;
    }
    
    /**
     * Obtient l'objet Administrator complet du service
     * @return Objet Administrator avec toutes les informations
     */
    public Administrator getAdministrator() {
        return administrator;
    }
    
    /**
     * Définit l'objet Administrator du service
     * @param administrator Objet Administrator complet
     */
    public void setAdministrator(Administrator administrator) {
        this.administrator = administrator;
    }
    
    /**
     * Obtient la liste de tous les employés du service
     * @return Liste des employés
     */
    public List<Employee> getEmployees() {
        return employees;
    }
    
    /**
     * Définit la liste de tous les employés du service
     * @param employees Liste des employés
     */
    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
    
    /**
     * Ajoute un employé à la liste des employés du service
     * @param employee Employé à ajouter
     */
    public void addEmployee(Employee employee) {
        if (this.employees == null) {
            this.employees = new ArrayList<>();
        }
        this.employees.add(employee);
    }
    
    /**
     * Supprime un employé de la liste des employés du service
     * @param employee Employé à supprimer
     */
    public void removeEmployee(Employee employee) {
        if (this.employees != null) {
            this.employees.remove(employee);
        }
    }
    
    /**
     * Obtient la liste de toutes les tâches du service
     * @return Liste des tâches
     */
    public List<Task> getTasks() {
        return tasks;
    }
    
    /**
     * Définit la liste de toutes les tâches du service
     * @param tasks Liste des tâches
     */
    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
    
    /**
     * Ajoute une tâche à la liste des tâches du service
     * @param task Tâche à ajouter
     */
    public void addTask(Task task) {
        if (this.tasks == null) {
            this.tasks = new ArrayList<>();
        }
        this.tasks.add(task);
    }
    
    /**
     * Obtient le nombre d'employés dans le service
     * @return Nombre d'employés
     */
    public int getEmployeeCount() {
        return employees != null ? employees.size() : 0;
    }
    
    /**
     * Obtient le nombre de tâches actives (non terminées) dans le service
     * @return Nombre de tâches actives
     */
    public int getActiveTasksCount() {
        if (tasks == null) return 0;
        return (int) tasks.stream()
                .filter(t -> t.getStatut() != TaskStatus.TERMINEE && t.getStatut() != TaskStatus.ANNULEE)
                .count();
    }
    
    /**
     * Affiche les informations du service
     * @return Texte décrivant le service avec ses informations principales
     */
    @Override
    public String toString() {
        return "Service{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", actif=" + actif +
                '}';
    }
    
    /**
     * Vérifie si deux services sont égaux (comparaison par ID)
     * @param o Objet à comparer
     * @return true si les services sont identiques
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Service)) return false;
        Service service = (Service) o;
        return id == service.id;
    }
    
    /**
     * Calcule le code de hachage du service (basé sur l'ID)
     * @return Code de hachage
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}