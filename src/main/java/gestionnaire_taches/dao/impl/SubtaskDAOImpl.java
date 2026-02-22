package gestionnaire_taches.dao.impl;

import gestionnaire_taches.model.Subtask;
import gestionnaire_taches.model.TaskStatus;
import gestionnaire_taches.util.DatabaseConnection;
import gestionnaire_taches.dao.interfaces.SubtaskDAO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SubtaskDAOImpl implements SubtaskDAO{

    private Connection connection;
    public SubtaskDAOImpl(){
        try {
            this.connection = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to establish database connection", e);
        }
    }

    @Override
    public Subtask save(Subtask subtask){
        String sql = "INSERT INTO Subtask (titre, description, task_id, ordre, statut, dateCreation) " + "VALUES (?, ?, ?, ?, ?, ?)";

        try(PreparedStatement stmt = connection.prepareStatement(sql)){

            stmt.setString(1, subtask.getTitre());
            stmt.setString(2, subtask.getDescription());
            stmt.setInt(3, subtask.getTaskId());
            stmt.setInt(4, subtask.getOrdre());
            stmt.setString(5, subtask.getStatut().name());
            stmt.setTimestamp(6, Timestamp.valueOf(subtask.getDateCreation()));
            stmt.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
        return subtask;
    }

    @Override
    public Optional<Subtask> findById(Integer id){
        String sql = "SELECT * FROM Subtask WHERE id = ?";
        Subtask subtask = null;

        try (PreparedStatement stmt = connection.prepareStatement(sql)){

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()){
                subtask = mapResultSetToSubtask(rs);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }

        return Optional.ofNullable(subtask);
    }

    @Override
    public List<Subtask> getSubtasksByTask(Integer taskId){
        String sql = "SELECT * FROM Subtask WHERE task_id = ? ORDER BY ordre";
        List<Subtask> subtasks = new ArrayList<>();

        try(PreparedStatement stmt = this.connection.prepareStatement(sql)){
            stmt.setInt(1, taskId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()){
                subtasks.add(mapResultSetToSubtask(rs));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }

        return subtasks;
    }

    @Override
    public Subtask update(Subtask subtask){
        String sql = "UPDATE Subtask SET titre = ?, description = ?, ordre = ?, statut = ? WHERE id = ?";

        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setString(1, subtask.getTitre());
            stmt.setString(2, subtask.getDescription());
            stmt.setInt(3, subtask.getOrdre());
            stmt.setString(4, subtask.getStatut().name());
            stmt.setInt(5, subtask.getId());
            stmt.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
        return subtask;
    }

    @Override
    public void delete(Integer id){
        String sql = "DELETE FROM Subtask WHERE id = ?";

        try (PreparedStatement stmt = this.connection.prepareStatement(sql)){

            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
    
    @Override
    public List<Subtask> findByTaskId(int taskId) {
        String sql = "SELECT * FROM Subtask WHERE task_id = ? ORDER BY ordre";
        List<Subtask> subtasks = new ArrayList<>();
        
        try(PreparedStatement stmt = this.connection.prepareStatement(sql)){
            stmt.setInt(1, taskId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()){
                subtasks.add(mapResultSetToSubtask(rs));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        
        return subtasks;
    }
    
    @Override
    public boolean updateOrder(Integer id, Integer ordre) {
        String sql = "UPDATE Subtask SET ordre = ? WHERE id = ?";
        
        try(PreparedStatement stmt = this.connection.prepareStatement(sql)){
            stmt.setInt(1, ordre);
            stmt.setInt(2, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public List<Subtask> getSubtasksByTaskOrdered(Integer taskId) {
        String sql = "SELECT * FROM Subtask WHERE task_id = ? ORDER BY ordre";
        List<Subtask> subtasks = new ArrayList<>();
        
        try(PreparedStatement stmt = this.connection.prepareStatement(sql)){
            stmt.setInt(1, taskId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()){
                subtasks.add(mapResultSetToSubtask(rs));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        
        return subtasks;
    }
    
    @Override
    public int countCompletedSubtasks(Integer taskId) {
        String sql = "SELECT COUNT(*) FROM Subtask WHERE task_id = ? AND statut = 'TERMINE'";
        int count = 0;
        
        try(PreparedStatement stmt = this.connection.prepareStatement(sql)){
            stmt.setInt(1, taskId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()){
                count = rs.getInt(1);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        
        return count;
    }
    
    //Méthode pour convertir un rs en objet Subtask
    private Subtask mapResultSetToSubtask(ResultSet rs) throws SQLException{
        
        Subtask subtask = new Subtask();
        
        subtask.setId(rs.getInt("id"));
        subtask.setTitre(rs.getString("titre"));
        subtask.setDescription(rs.getString("description"));
        subtask.setTaskId(rs.getInt("task_id"));
        subtask.setOrdre(rs.getInt("ordre"));
        subtask.setStatut(TaskStatus.valueOf(rs.getString("statut")));
        subtask.setDateCreation(rs.getTimestamp("dateCreation").toLocalDateTime());
        
        return subtask;
    }
    
    @Override
    public long count() {
        String sql = "SELECT COUNT(*) FROM Subtask";
        long count = 0;
        
        try(PreparedStatement stmt = this.connection.prepareStatement(sql)){
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()){
                count = rs.getLong(1);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        
        return count;
    }
    
    @Override
    public List<Subtask> findAll() {
        String sql = "SELECT * FROM Subtask ORDER BY task_id, ordre";
        List<Subtask> subtasks = new ArrayList<>();
        
        try(PreparedStatement stmt = this.connection.prepareStatement(sql)){
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()){
                subtasks.add(mapResultSetToSubtask(rs));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        
        return subtasks;
    }
}