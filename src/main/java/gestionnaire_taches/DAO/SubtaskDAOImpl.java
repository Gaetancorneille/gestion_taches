package DAO;

import model.subtask;
import model TaskStatus;
import util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SubtaskDAOImpl implements SubtaskDAO{

    private Connection connection;
    public SubtaskDAOImpl(){
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    @Override
    public void create(Subtask subtask){
        String sql = "INSERT INTO Subtask (titre, description, task_id, ordre, statut, dateCreation) " + "VALUES (?, ?, ?, ?, ?, ?)";

        try(PreparedStatement stmt = connection.prepareStatement(sql)){

            stmt.setString(1, subtask.getTitre());
            stmt.setString(2, subtask.getDescription());
            stmt.setInt(3, subtask.getTaskId());
            stmt.setInt(4, subtask.getOrdre());
            stmt.setString(5, subtask.getStatut().name());
            stmt.setTimestamp(6, subtask.setTimestamp.valueOf(subtask.getDateCreation()));
            stmt.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public Subtask findById(int id){
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

        return subtask;
    }

    @Override
    public List<Subtask> getSubtasksByTask(int taskId){
        String sql = "SELECT * FROM Subtask WHERE task_id = ? ORDER BY ordre";
        List<Subtask> subtasks = new ArrayList<>();

        try(PreparedStatement stmt = Connection.prepareStatement(sql)){
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
    public void update(Subtask subtask){
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
    }

    @Override
    public void delete(int id){
        String sql = "DELETE FROM Subtask WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)){

            stmt.setInt(1, subtask.getId());
            stmt.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
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
}