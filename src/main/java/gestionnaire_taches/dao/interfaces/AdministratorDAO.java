package gestionnaire_taches.dao.interfaces;

import gestionnaire_taches.model.Administrator;
import java.util.Optional;
import java.util.List;

/**
 * Interface pour les opérations sur les administrateurs
 * Hérite de GenericDAO et ajoute des méthodes spécifiques aux administrateurs
 */
public interface AdministratorDAO extends GenericDAO<Administrator, Integer> {
    
    /**
     * Trouve un administrateur par son adresse email
     * @param email Adresse email de l'administrateur
     * @return Administrateur trouvé ou null si non trouvé
     */
    Optional<Administrator> findByEmail(String email);
    
    /**
     * Récupère tous les administrateurs
     * @return Liste de tous les administrateurs
     */
    List<Administrator> findAll();
    
    /**
     * Authentifie un administrateur avec son email et mot de passe
     * @param email Adresse email de l'administrateur
     * @param password Mot de passe (doit être hashé)
     * @return Administrateur authentifié ou null si échec
     */
    Optional<Administrator> authenticate(String email, String password);
    
    /**
     * Vérifie si un email est déjà utilisé
     * @param email Adresse email à vérifier
     * @return true si l'email existe déjà
     */
    boolean emailExists(String email);
    
    /**
     * Met à jour le mot de passe d'un administrateur
     * @param id ID de l'administrateur
     * @param newPassword Nouveau mot de passe hashé
     * @return true si la mise à jour a réussi
     */
    boolean updatePassword(Integer id, String newPassword);
}