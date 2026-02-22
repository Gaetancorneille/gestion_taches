package gestionnaire_taches.dao.interfaces;

import java.util.List;
import java.util.Optional;

/**
 * Interface générique pour toutes les opérations de base de données
 * Toutes les autres interfaces DAO vont hériter de cette interface
 * @param <T> Type de l'entité (Administrator, Employee, Service, etc.)
 * @param <ID> Type de l'identifiant (généralement Integer)
 */
public interface GenericDAO<T, ID> {
    
    /**
     * Enregistre une nouvelle entité dans la base de données
     * @param entity Entité à enregistrer
     * @return Entité enregistrée avec son ID généré
     */
    T save(T entity);
    
    /**
     * Met à jour une entité existante dans la base de données
     * @param entity Entité à mettre à jour
     * @return Entité mise à jour
     */
    T update(T entity);
    
    /**
     * Supprime une entité de la base de données par son ID
     * @param id ID de l'entité à supprimer
     */
    void delete(ID id);
    
    /**
     * Trouve une entité par son ID
     * @param id ID de l'entité recherchée
     * @return Entité trouvée ou null si non trouvée
     */
    Optional<T> findById(ID id);
    
    /**
     * Récupère toutes les entités de ce type
     * @return Liste de toutes les entités
     */
    List<T> findAll();
    
    /**
     * Compte le nombre total d'entités
     * @return Nombre total d'entités
     */
    long count();
}