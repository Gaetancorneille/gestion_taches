package gestionnaire_taches.dao.interfaces;

import gestionnaire_taches.model.Task;
import gestionnaire_taches.model.TaskStatus;
import gestionnaire_taches.model.Priority;
import java.util.List;

/**
 * Interface pour les opérations sur les tâches
 * Hérite de GenericDAO et ajoute des méthodes spécifiques aux tâches
 */
public interface TaskDAO extends GenericDAO<Task, Integer> {
    
    /**
     * Récupère toutes les tâches assignées à un employé spécifique
     * @param employeeId ID de l'employé
     * @return Liste des tâches de cet employé
     */
    List<Task> findByEmployeeId(int employeeId);
    
    /**
     * Récupère toutes les tâches d'un service spécifique
     * @param serviceId ID du service
     * @return Liste des tâches de ce service
     */
    List<Task> findByServiceId(int serviceId);
    
    /**
     * Récupère toutes les tâches avec un statut spécifique
     * @param statut Statut recherché
     * @return Liste des tâches avec ce statut
     */
    List<Task> findByStatus(TaskStatus statut);
    
    /**
     * Récupère les tâches en retard
     * @return Liste des tâches en retard
     */
    List<Task> findOverdueTasks();
    
    /**
     * Compte le nombre de tâches par statut
     * @param statut Statut à compter
     * @return Nombre de tâches avec ce statut
     */
    int countTasksByStatus(int serviceId, TaskStatus statut);
    
    /**
     * Récupère les tâches d'un employé avec un statut spécifique
     * @param employeeId ID de l'employé
     * @param status Statut recherché
     * @return Liste des tâches correspondantes
     */
    List<Task> findByEmployeeAndStatus(int employeeId, TaskStatus status);
    
    /**
     * Récupère les tâches avec une priorité spécifique
     * @param priority Priorité recherchée
     * @return Liste des tâches avec cette priorité
     */
    List<Task> findByPriority(Priority priority);
}