package gestionnaire_taches.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Classe représentant une tâche à accomplir
 * Une tâche est assignée à un employé et appartient à un service
 * Elle peut contenir plusieurs sous-tâches pour une meilleure organisation
 */
public class Task {
    // Numéro unique qui identifie la tâche
    private int id;
    
    // Titre court et clair de la tâche
    private String titre;
    
    // Description détaillée de ce qu'il faut faire
    private String description;
    
    // Numéro de l'employé à qui la tâche est assignée
    private int employeeId;
    
    // Numéro du service auquel la tâche appartient
    private int serviceId;
    
    // Date et heure de création de la tâche
    private LocalDateTime dateCreation;
    
    // Date limite pour terminer la tâche
    private LocalDate dateLimite;
    
    // Priorité de la tâche (BASSE, NORMALE, HAUTE, URGENTE)
    private Priority priorite;
    
    // Statut actuel de la tâche (A_FAIRE, EN_COURS, TERMINEE, ANNULEE)
    private TaskStatus statut;
    
    // Référence vers l'objet Employee (informations détaillées de l'employé)
    private Employee employee;
    
    // Référence vers l'objet Service (informations détaillées du service)
    private Service service;
    
    // Liste de toutes les sous-tâches de cette tâche
    private List<Subtask> subtasks;
    
    /**
     * Constructeur par défaut
     * Crée une tâche avec les valeurs par défaut :
     * - Statut : A_FAIRE
     * - Priorité : NORMALE
     * - Date de création : maintenant
     * - Liste de sous-tâches vide
     */
    public Task() {
        this.dateCreation = LocalDateTime.now();
        this.statut = TaskStatus.A_FAIRE;
        this.priorite = Priority.NORMALE;
        this.subtasks = new ArrayList<>();
    }
    
    /**
     * Constructeur avec les informations de base
     * @param titre Titre de la tâche
     * @param description Description détaillée de la tâche
     * @param employeeId Numéro de l'employé à qui assigner la tâche
     * @param serviceId Numéro du service auquel la tâche appartient
     */
    public Task(String titre, String description, int employeeId, int serviceId) {
        this();
        this.titre = titre;
        this.description = description;
        this.employeeId = employeeId;
        this.serviceId = serviceId;
    }
    
    /**
     * Constructeur avec tous les paramètres
     * @param id Numéro unique de la tâche
     * @param titre Titre de la tâche
     * @param description Description détaillée de la tâche
     * @param employeeId Numéro de l'employé à qui assigner la tâche
     * @param serviceId Numéro du service auquel la tâche appartient
     */
    public Task(int id, String titre, String description, int employeeId, int serviceId) {
        this(titre, description, employeeId, serviceId);
        this.id = id;
    }

    /**
     * Constructeur complet avec tous les champs modifiables.
     * Utile pour la création rapide à partir d'un formulaire.
     * @param titre Titre de la tâche
     * @param description Description détaillée de la tâche
     * @param employeeId Numéro de l'employé à qui assigner la tâche
     * @param serviceId Numéro du service auquel la tâche appartient
     * @param dateLimite Date limite pour terminer la tâche
     * @param priorite Priorité de la tâche
     * @param statut Statut initial de la tâche
     */
    public Task(String titre, String description, int employeeId, int serviceId,
                LocalDate dateLimite, Priority priorite, TaskStatus statut) {
        this(titre, description, employeeId, serviceId);
        this.dateLimite = dateLimite;
        this.priorite = priorite;
        this.statut = statut;
    }
    
    /**
     * Obtient le numéro unique de la tâche
     * @return Numéro de la tâche
     */
    public int getId() { return id; }
    
    /**
     * Définit le numéro unique de la tâche
     * @param id Numéro de la tâche
     */
    public void setId(int id) { this.id = id; }
    
    /**
     * Obtient le titre de la tâche
     * @return Titre de la tâche
     */
    public String getTitre() { return titre; }
    
    /**
     * Définit le titre de la tâche
     * @param titre Titre de la tâche
     */
    public void setTitre(String titre) { this.titre = titre; }
    
    /**
     * Obtient la description de la tâche
     * @return Description de la tâche
     */
    public String getDescription() { return description; }
    
    /**
     * Définit la description de la tâche
     * @param description Description de la tâche
     */
    public void setDescription(String description) { this.description = description; }
    
    /**
     * Obtient le numéro de l'employé à qui la tâche est assignée
     * @return Numéro de l'employé
     */
    public int getEmployeeId() { return employeeId; }
    
    /**
     * Définit le numéro de l'employé à qui la tâche est assignée
     * @param employeeId Numéro de l'employé
     */
    public void setEmployeeId(int employeeId) { this.employeeId = employeeId; }
    
    /**
     * Obtient le numéro du service auquel la tâche appartient
     * @return Numéro du service
     */
    public int getServiceId() { return serviceId; }
    
    /**
     * Définit le numéro du service auquel la tâche appartient
     * @param serviceId Numéro du service
     */
    public void setServiceId(int serviceId) { this.serviceId = serviceId; }
    
    /**
     * Obtient la date de création de la tâche
     * @return Date de création
     */
    public LocalDateTime getDateCreation() { return dateCreation; }
    
    /**
     * Définit la date de création de la tâche
     * @param dateCreation Date de création
     */
    public void setDateCreation(LocalDateTime dateCreation) { this.dateCreation = dateCreation; }
    
    /**
     * Obtient la date limite pour terminer la tâche
     * @return Date limite
     */
    public LocalDate getDateLimite() { return dateLimite; }
    
    /**
     * Définit la date limite pour terminer la tâche
     * @param dateLimite Date limite
     */
    public void setDateLimite(LocalDate dateLimite) { this.dateLimite = dateLimite; }
    
    /**
     * Obtient la priorité de la tâche
     * @return Priorité de la tâche
     */
    public Priority getPriorite() { return priorite; }
    
    /**
     * Définit la priorité de la tâche
     * @param priorite Priorité de la tâche
     */
    public void setPriorite(Priority priorite) { this.priorite = priorite; }
    
    /**
     * Obtient le statut actuel de la tâche
     * @return Statut de la tâche
     */
    public TaskStatus getStatut() { return statut; }
    
    /**
     * Définit le statut de la tâche
     * @param statut Statut de la tâche
     */
    public void setStatut(TaskStatus statut) { this.statut = statut; }
    
    /**
     * Obtient l'objet Employee complet de la tâche
     * @return Objet Employee avec toutes les informations
     */
    public Employee getEmployee() { return employee; }
    
    /**
     * Définit l'objet Employee de la tâche
     * @param employee Objet Employee complet
     */
    public void setEmployee(Employee employee) { this.employee = employee; }
    
    /**
     * Obtient l'objet Service complet de la tâche
     * @return Objet Service avec toutes les informations
     */
    public Service getService() { return service; }
    
    /**
     * Définit l'objet Service de la tâche
     * @param service Objet Service complet
     */
    public void setService(Service service) { this.service = service; }
    
    /**
     * Obtient la liste de toutes les sous-tâches de cette tâche
     * @return Liste des sous-tâches
     */
    public List<Subtask> getSubtasks() { return subtasks; }
    
    /**
     * Définit la liste de toutes les sous-tâches de cette tâche
     * @param subtasks Liste des sous-tâches
     */
    public void setSubtasks(List<Subtask> subtasks) { this.subtasks = subtasks; }
    
    /**
     * Ajoute une sous-tâche à cette tâche
     * @param s Sous-tâche à ajouter
     */
    public void addSubtask(Subtask s) {
        if (s != null) {
            if (this.subtasks == null) this.subtasks = new ArrayList<>();
            this.subtasks.add(s);
            s.setTask(this);
        }
    }
    
    /**
     * Calcule le pourcentage de progression de la tâche
     * Basé sur le nombre de sous-tâches terminées
     * @return Pourcentage de progression (0 à 100)
     */
    public double getCompletionPercentage() {
        if (subtasks == null || subtasks.isEmpty()) {
            return statut == TaskStatus.TERMINEE ? 100.0 : 0.0;
        }
        long completed = subtasks.stream().filter(st -> st.getStatut() == TaskStatus.TERMINEE).count();
        return (completed * 100.0) / subtasks.size();
    }
    
    /**
     * Vérifie si la tâche est en retard
     * Une tâche est en retard si la date limite est dépassée et qu'elle n'est pas terminée
     * @return true si la tâche est en retard
     */
    public boolean isOverdue() {
        return dateLimite != null && LocalDate.now().isAfter(dateLimite) 
                && statut != TaskStatus.TERMINEE && statut != TaskStatus.ANNULEE;
    }
    
    /**
     * Affiche les informations de la tâche
     * @return Texte décrivant la tâche avec ses informations principales
     */
    @Override
    public String toString() {
        return "Task{" + "id=" + id + ", titre='" + titre + "', statut=" + statut + '}';
    }
    
    /**
     * Vérifie si deux tâches sont égales (comparaison par ID)
     * @param o Objet à comparer
     * @return true si les tâches sont identiques
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task)) return false;
        Task task = (Task) o;
        return id == task.id;
    }
    
    /**
     * Calcule le code de hachage de la tâche (basé sur l'ID)
     * @return Code de hachage
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}