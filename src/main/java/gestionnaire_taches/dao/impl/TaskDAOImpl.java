package gestionnaire_taches.dao.impl;

import gestionnaire_taches.dao.interfaces.TaskDAO;
import gestionnaire_taches.model.Task;
import gestionnaire_taches.model.TaskStatus;
import gestionnaire_taches.model.Priority;
import gestionnaire_taches.util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implémentation de l'interface TaskDAO
 * Gère toutes les opérations de base de données pour les tâches
 */
public class TaskDAOImpl implements TaskDAO {
    
    @Override
    public Task save(Task task) {
        // Implementation de base - à compléter
        return task;
    }
    
    @Override
    public Task update(Task task) {
        // Implementation de base - à compléter
        return task;
    }
    
    @Override
    public void delete(Integer id) {
        // Implementation de base - à compléter
    }
    
    @Override
    public java.util.Optional<Task> findById(Integer id) {
        // Implementation de base - à compléter
        return java.util.Optional.empty();
    }
    
    @Override
    public List<Task> findAll() {
        return new ArrayList<>();
    }
    
    @Override
    public long count() {
        return 0;
    }
    
    @Override
    public List<Task> findByEmployeeId(int employeeId) {
        return new ArrayList<>();
    }
    
    @Override
    public List<Task> findByServiceId(int serviceId) {
        return new ArrayList<>();
    }
    
    @Override
    public List<Task> findByStatus(TaskStatus statut) {
        return new ArrayList<>();
    }
    
    @Override
    public List<Task> findOverdueTasks() {
        return new ArrayList<>();
    }
    
    @Override
    public int countTasksByStatus(int serviceId, TaskStatus statut) {
        return 0;
    }
    
    @Override
    public List<Task> findByEmployeeAndStatus(int employeeId, TaskStatus status) {
        String sql = "SELECT * FROM Task WHERE employee_id = ? AND statut = ? ORDER BY dateLimite";
        List<Task> tasks = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, employeeId);
            stmt.setString(2, status.name());
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    tasks.add(mapResultSetToTask(rs));
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des tâches par employé et statut : " + e.getMessage());
            e.printStackTrace();
        }
        
        return tasks;
    }
    
    @Override
    public List<Task> findByPriority(Priority priority) {
        String sql = "SELECT * FROM Task WHERE priorite = ? ORDER BY dateLimite";
        List<Task> tasks = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, priority.name());
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    tasks.add(mapResultSetToTask(rs));
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des tâches par priorité : " + e.getMessage());
            e.printStackTrace();
        }
        
        return tasks;
    }
    
    /**
     * Convertit un ResultSet en objet Task
     * @param rs ResultSet contenant les données
     * @return Objet Task créé
     * @throws SQLException Si une erreur de base de données se produit
     */
    private Task mapResultSetToTask(ResultSet rs) throws SQLException {
        Task task = new Task();
        task.setId(rs.getInt("id"));
        task.setTitre(rs.getString("titre"));
        task.setDescription(rs.getString("description"));
        task.setEmployeeId(rs.getInt("employee_id"));
        task.setServiceId(rs.getInt("service_id"));
        task.setPriorite(Priority.valueOf(rs.getString("priorite")));
        task.setStatut(TaskStatus.valueOf(rs.getString("statut")));
        task.setDateCreation(rs.getTimestamp("dateCreation").toLocalDateTime());
        task.setDateLimite(rs.getTimestamp("dateLimite").toLocalDateTime().toLocalDate());
        // Completion percentage is calculated based on subtasks, not stored in database
        return task;
    }
}