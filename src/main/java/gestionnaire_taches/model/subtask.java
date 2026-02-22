package gestionnaire_taches.model;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Classe représentant une sous-tâche
 * Une sous-tâche est une partie d'une tâche principale
 * Elle permet de diviser une grande tâche en étapes plus petites et gérables
 */
public class Subtask {
    // Numéro unique qui identifie la sous-tâche
    private int id;
    
    // Titre court et clair de la sous-tâche
    private String titre;
    
    // Description détaillée de ce qu'il faut faire
    private String description;
    
    // Numéro de la tâche principale à laquelle cette sous-tâche appartient
    private int taskId;
    
    // Numéro d'ordre pour organiser les sous-tâches dans l'ordre d'exécution
    private int ordre;
    
    // Statut actuel de la sous-tâche (A_FAIRE, EN_COURS, TERMINEE)
    private TaskStatus statut;
    
    // Date et heure de création de la sous-tâche
    private LocalDateTime dateCreation;
    
    // Référence vers l'objet Task (tâche principale)
    private Task task;
    
    /**
     * Constructeur par défaut
     * Crée une sous-tâche avec les valeurs par défaut :
     * - Statut : A_FAIRE
     * - Ordre : 0
     * - Date de création : maintenant
     */
    public Subtask() {
        this.dateCreation = LocalDateTime.now();
        this.statut = TaskStatus.A_FAIRE;
        this.ordre = 0;
    }
    
    /**
     * Constructeur avec les informations de base
     * @param titre Titre de la sous-tâche
     * @param description Description détaillée de la sous-tâche
     * @param taskId Numéro de la tâche principale
     */
    public Subtask(String titre, String description, int taskId) {
        this();
        this.titre = titre;
        this.description = description;
        this.taskId = taskId;
    }
    
    /**
     * Constructeur avec tous les paramètres
     * @param id Numéro unique de la sous-tâche
     * @param titre Titre de la sous-tâche
     * @param description Description détaillée de la sous-tâche
     * @param taskId Numéro de la tâche principale
     */
    public Subtask(int id, String titre, String description, int taskId) {
        this(titre, description, taskId);
        this.id = id;
    }
    
    /**
     * Obtient le numéro unique de la sous-tâche
     * @return Numéro de la sous-tâche
     */
    public int getId() {
        return id;
    }
    
    /**
     * Définit le numéro unique de la sous-tâche
     * @param id Numéro de la sous-tâche
     */
    public void setId(int id) {
        this.id = id;
    }
    
    /**
     * Obtient le titre de la sous-tâche
     * @return Titre de la sous-tâche
     */
    public String getTitre() {
        return titre;
    }
    
    /**
     * Définit le titre de la sous-tâche
     * @param titre Titre de la sous-tâche
     */
    public void setTitre(String titre) {
        this.titre = titre;
    }
    
    /**
     * Obtient la description de la sous-tâche
     * @return Description de la sous-tâche
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * Définit la description de la sous-tâche
     * @param description Description de la sous-tâche
     */
    public void setDescription(String description) {
        this.description = description;
    }
    
    /**
     * Obtient le numéro de la tâche principale
     * @return Numéro de la tâche principale
     */
    public int getTaskId() {
        return taskId;
    }
    
    /**
     * Définit le numéro de la tâche principale
     * @param taskId Numéro de la tâche principale
     */
    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }
    
    /**
     * Obtient le numéro d'ordre de la sous-tâche
     * @return Numéro d'ordre
     */
    public int getOrdre() {
        return ordre;
    }
    
    /**
     * Définit le numéro d'ordre de la sous-tâche
     * @param ordre Numéro d'ordre
     */
    public void setOrdre(int ordre) {
        this.ordre = ordre;
    }
    
    /**
     * Obtient le statut actuel de la sous-tâche
     * @return Statut de la sous-tâche
     */
    public TaskStatus getStatut() {
        return statut;
    }
    
    /**
     * Définit le statut de la sous-tâche
     * @param statut Statut de la sous-tâche
     */
    public void setStatut(TaskStatus statut) {
        this.statut = statut;
    }
    
    /**
     * Obtient la date de création de la sous-tâche
     * @return Date de création
     */
    public LocalDateTime getDateCreation() {
        return dateCreation;
    }
    
    /**
     * Définit la date de création de la sous-tâche
     * @param dateCreation Date de création
     */
    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }
    
    /**
     * Obtient l'objet Task complet de la sous-tâche
     * @return Objet Task avec toutes les informations
     */
    public Task getTask() {
        return task;
    }
    
    /**
     * Définit l'objet Task de la sous-tâche
     * @param task Objet Task complet
     */
    public void setTask(Task task) {
        this.task = task;
    }
    
    /**
     * Vérifie si la sous-tâche est terminée
     * @return true si la sous-tâche est terminée
     */
    public boolean isCompleted() {
        return statut == TaskStatus.TERMINEE;
    }
    
    /**
     * Affiche les informations de la sous-tâche
     * @return Texte décrivant la sous-tâche avec ses informations principales
     */
    @Override
    public String toString() {
        return "Subtask{" +
                "id=" + id +
                ", titre='" + titre + '\'' +
                ", statut=" + statut +
                ", ordre=" + ordre +
                '}';
    }
    
    /**
     * Vérifie si deux sous-tâches sont égales (comparaison par ID)
     * @param o Objet à comparer
     * @return true si les sous-tâches sont identiques
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Subtask)) return false;
        Subtask subtask = (Subtask) o;
        return id == subtask.id;
    }
    
    /**
     * Calcule le code de hachage de la sous-tâche (basé sur l'ID)
     * @return Code de hachage
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}