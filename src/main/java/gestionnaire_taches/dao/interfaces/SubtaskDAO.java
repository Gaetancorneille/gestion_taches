package gestionnaire_taches.dao.interfaces;

import gestionnaire_taches.model.Subtask;
import java.util.List;

/**
 * Interface pour les opérations sur les sous-tâches
 * Hérite de GenericDAO et ajoute des méthodes spécifiques aux sous-tâches
 */
public interface SubtaskDAO extends GenericDAO<Subtask, Integer> {
    
    /**
     * Récupère toutes les sous-tâches d'une tâche spécifique
     * @param taskId ID de la tâche principale
     * @return Liste des sous-tâches de cette tâche
     */
    List<Subtask> getSubtasksByTask(Integer taskId);
    
    /**
     * Récupère les sous-tâches d'une tâche triées par ordre
     * @param taskId ID de la tâche principale
     * @return Liste des sous-tâches triées par ordre d'exécution
     */
    List<Subtask> getSubtasksByTaskOrdered(Integer taskId);
    
    /**
     * Compte le nombre de sous-tâches terminées pour une tâche
     * @param taskId ID de la tâche principale
     * @return Nombre de sous-tâches terminées
     */
    int countCompletedSubtasks(Integer taskId);
    
    /**
     * Met à jour l'ordre d'une sous-tâche
     * @param id ID de la sous-tâche
     * @param ordre Nouvel ordre
     * @return true si la mise à jour a réussi
     */
    boolean updateOrder(Integer id, Integer ordre);
    
    /**
     * Récupère toutes les sous-tâches d'une tâche spécifique par ID
     * @param taskId ID de la tâche principale
     * @return Liste des sous-tâches de cette tâche
     */
    List<Subtask> findByTaskId(int taskId);
}