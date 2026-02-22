package gestionnaire_taches.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import gestionnaire_taches.dao.interfaces.TaskDAO;
import gestionnaire_taches.model.Priority;
import gestionnaire_taches.model.Task;
import gestionnaire_taches.model.TaskStatus;
import gestionnaire_taches.util.DatabaseConnection;

/**
 * Implémentation de l'interface TaskDAO
 * Gère toutes les opérations de base de données pour les tâches
 */
public class TaskDAOImpl implements TaskDAO {
    
    @Override
    public Task save(Task task) {
        String sql = "INSERT INTO Task (titre, description, employee_id, service_id, dateCreation, dateLimite, priorite, statut) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, task.getTitre());
            stmt.setString(2, task.getDescription());
            stmt.setInt(3, task.getEmployeeId());
            stmt.setInt(4, task.getServiceId());
            stmt.setTimestamp(5, Timestamp.valueOf(task.getDateCreation()));
            stmt.setTimestamp(6, task.getDateLimite() != null ? Timestamp.valueOf(task.getDateLimite().atStartOfDay()) : null);
            stmt.setString(7, task.getPriorite().name());
            stmt.setString(8, task.getStatut().name());

            int affected = stmt.executeUpdate();
            if (affected == 0) {
                throw new SQLException("Échec de la création de la tâche, aucune ligne affectée.");
            }
            try (ResultSet keys = stmt.getGeneratedKeys()) {
                if (keys.next()) {
                    task.setId(keys.getInt(1));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'enregistrement de la tâche : " + e.getMessage());
            e.printStackTrace();
        }
        return task;
    }
    
    @Override
    public Task update(Task task) {
        String sql = "UPDATE Task SET titre = ?, description = ?, employee_id = ?, service_id = ?, dateLimite = ?, priorite = ?, statut = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, task.getTitre());
            stmt.setString(2, task.getDescription());
            stmt.setInt(3, task.getEmployeeId());
            stmt.setInt(4, task.getServiceId());
            stmt.setTimestamp(5, task.getDateLimite() != null ? Timestamp.valueOf(task.getDateLimite().atStartOfDay()) : null);
            stmt.setString(6, task.getPriorite().name());
            stmt.setString(7, task.getStatut().name());
            stmt.setInt(8, task.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur lors de la mise à jour de la tâche : " + e.getMessage());
            e.printStackTrace();
        }
        return task;
    }
    
    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM Task WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression de la tâche : " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    @Override
    public java.util.Optional<Task> findById(Integer id) {
        String sql = "SELECT * FROM Task WHERE id = ?";
        Task task = null;
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    task = mapResultSetToTask(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la recherche de la tâche : " + e.getMessage());
            e.printStackTrace();
        }
        return java.util.Optional.ofNullable(task);
    }
    
    @Override
    public List<Task> findAll() {
        String sql = "SELECT * FROM Task ORDER BY dateCreation";
        List<Task> tasks = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                tasks.add(mapResultSetToTask(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des tâches : " + e.getMessage());
            e.printStackTrace();
        }
        return tasks;
    }
    
    @Override
    public long count() {
        String sql = "SELECT COUNT(*) FROM Task";
        long count = 0;
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                count = rs.getLong(1);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors du comptage des tâches : " + e.getMessage());
            e.printStackTrace();
        }
        return count;
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