package gestionnaire_taches.dao.interfaces;

import gestionnaire_taches.model.Employee;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Interface pour les opérations sur les employés
 * Hérite de GenericDAO et ajoute des méthodes spécifiques aux employés
 */
public interface EmployeeDAO extends GenericDAO<Employee, Integer> {
    
    /**
     * Trouve un employé par son adresse email
     * @param email Adresse email de l'employé
     * @return Employé trouvé ou null si non trouvé
     */
    Optional<Employee> findByEmail(String email);
    
    /**
     * Authentifie un employé avec son email et mot de passe
     * @param email Adresse email de l'employé
     * @param password Mot de passe (doit être hashé)
     * @return Employé authentifié ou null si échec
     */
    Optional<Employee> authenticate(String email, String password);
    
    /**
     * Récupère tous les employés d'un service spécifique
     * @param serviceId ID du service
     * @return Liste des employés du service
     */
    List<Employee> getEmployeesByService(Integer serviceId);
    
    /**
     * Vérifie si un email est déjà utilisé
     * @param email Adresse email à vérifier
     * @return true si l'email existe déjà
     */
    boolean emailExists(String email);
    
    /**
     * Met à jour le mot de passe d'un employé
     * @param id ID de l'employé
     * @param newPassword Nouveau mot de passe hashé
     * @return true si la mise à jour a réussi
     */
    boolean updatePassword(Integer id, String newPassword);
    
    /**
     * Récupère tous les employés d'un service spécifique par ID
     * @param serviceId ID du service
     * @return Liste des employés du service
     */
    List<Employee> findByServiceId(int serviceId);
    
    /**
     * Récupère tous les employés d'un service spécifique par nom
     * @param serviceName Nom du service
     * @return Liste des employés du service
     */
    List<Employee> findByServiceName(String serviceName);

    /**
     * Récupère tous les employés
     * @return Liste des employés
     */
    @Override
    public List<Employee> findAll();
}