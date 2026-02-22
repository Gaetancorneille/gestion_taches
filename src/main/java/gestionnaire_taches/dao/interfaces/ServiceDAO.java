package gestionnaire_taches.dao.interfaces;

import gestionnaire_taches.model.Service;
import java.util.List;

/**
 * Interface pour les opérations sur les services
 * Hérite de GenericDAO et ajoute des méthodes spécifiques aux services
 */
public interface ServiceDAO extends GenericDAO<Service, Integer> {
    
    /**
     * Récupère tous les services gérés par un administrateur spécifique
     * @param administratorId ID de l'administrateur
     * @return Liste des services gérés par cet administrateur
     */
    List<Service> getServicesByAdministrator(Integer administratorId);
    
    /**
     * Récupère tous les services actifs
     * @return Liste des services actifs
     */
    List<Service> getActiveServices();
    
    /**
     * Vérifie si un nom de service est déjà utilisé
     * @param nom Nom du service à vérifier
     * @return true si le nom existe déjà
     */
    boolean nomExists(String nom);
    
    /**
     * Récupère tous les services gérés par un administrateur spécifique par ID
     * @param administratorId ID de l'administrateur
     * @return Liste des services gérés par cet administrateur
     */
    List<Service> findByAdministratorId(int administratorId);
    
    /**
     * Récupère tous les services gérés par un administrateur spécifique par nom
     * @param administratorName Nom de l'administrateur
     * @return Liste des services gérés par cet administrateur
     */
    List<Service> findByAdministratorName(String administratorName);
    
    /**
     * Compte le nombre d'employés dans un service
     * @param serviceId ID du service
     * @return Nombre d'employés dans le service
     */
    int countEmployeesInService(int serviceId);
    
    /**
     * Compte le nombre de tâches actives dans un service
     * @param serviceId ID du service
     * @return Nombre de tâches actives dans le service
     */
    int countActiveTasksInService(int serviceId);
}