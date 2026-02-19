package gestionnaire_taches.DAO;

import java.util.List;

/**
 * Interface générique pour les opérations CRUD.
 */
public class UtilisateurDAO

public interface DAO<T> {
    T getById(int id);
    List<T> getAll();
    boolean create(T obj);
    boolean update(T obj);
    boolean delete(int id);
}
