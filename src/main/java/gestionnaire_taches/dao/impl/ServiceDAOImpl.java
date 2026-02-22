package gestionnaire_taches.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import gestionnaire_taches.dao.interfaces.ServiceDAO;
import gestionnaire_taches.model.Service;
import gestionnaire_taches.util.DatabaseConnection;

/**
 * Implémentation de l'interface ServiceDAO
 * Gère toutes les opérations de base de données pour les services
 */
public class ServiceDAOImpl implements ServiceDAO {
    
    @Override
    public Service save(Service service) {
        String sql = "INSERT INTO Service (nom, description, administrator_id, dateCreation, actif) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, service.getNom());
            stmt.setString(2, service.getDescription());
            stmt.setInt(3, service.getAdministratorId());
            stmt.setTimestamp(4, Timestamp.valueOf(service.getDateCreation()));
            stmt.setBoolean(5, service.isActif());

            int affected = stmt.executeUpdate();
            if (affected == 0) {
                throw new SQLException("Échec de la création du service, aucune ligne affectée.");
            }
            try (ResultSet keys = stmt.getGeneratedKeys()) {
                if (keys.next()) {
                    service.setId(keys.getInt(1));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'enregistrement du service : " + e.getMessage());
            e.printStackTrace();
        }
        return service;
    }
    
    @Override
    public Service update(Service service) {
        String sql = "UPDATE Service SET nom = ?, description = ?, administrator_id = ?, actif = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, service.getNom());
            stmt.setString(2, service.getDescription());
            stmt.setInt(3, service.getAdministratorId());
            stmt.setBoolean(4, service.isActif());
            stmt.setInt(5, service.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur lors de la mise à jour du service : " + e.getMessage());
            e.printStackTrace();
        }
        return service;
    }
    
    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM Service WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression du service : " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    @Override
    public java.util.Optional<Service> findById(Integer id) {
        String sql = "SELECT * FROM Service WHERE id = ?";
        Service service = null;
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    service = mapResultSetToService(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la recherche du service : " + e.getMessage());
            e.printStackTrace();
        }
        return java.util.Optional.ofNullable(service);
    }
    
    @Override
    public List<Service> findAll() {
        String sql = "SELECT * FROM Service ORDER BY nom";
        List<Service> services = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                services.add(mapResultSetToService(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des services : " + e.getMessage());
            e.printStackTrace();
        }
        return services;
    }
    
    @Override
    public long count() {
        String sql = "SELECT COUNT(*) FROM Service";
        long count = 0;
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                count = rs.getLong(1);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors du comptage des services : " + e.getMessage());
            e.printStackTrace();
        }
        return count;
    }
    
    @Override
    public List<Service> getServicesByAdministrator(Integer administratorId) {
        // réutilise la méthode déjà existante
        return findByAdministratorId(administratorId);
    }
    
    @Override
    public List<Service> getActiveServices() {
        String sql = "SELECT * FROM Service WHERE actif = TRUE ORDER BY nom";
        List<Service> services = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                services.add(mapResultSetToService(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des services actifs : " + e.getMessage());
            e.printStackTrace();
        }
        return services;
    }
    
    @Override
    public boolean nomExists(String nom) {
        String sql = "SELECT COUNT(*) FROM Service WHERE nom = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, nom);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la vérification de l'existence du nom : " + e.getMessage());
            e.printStackTrace();
        }
        
        return false;
    }
    
    @Override
    public List<Service> findByAdministratorId(int administratorId) {
        String sql = "SELECT * FROM Service WHERE administrator_id = ? ORDER BY nom";
        List<Service> services = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, administratorId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    services.add(mapResultSetToService(rs));
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des services par administrateur : " + e.getMessage());
            e.printStackTrace();
        }
        
        return services;
    }
    
    @Override
    public List<Service> findByAdministratorName(String administratorName) {
        String sql = "SELECT s.* FROM Service s JOIN Administrator a ON s.administrator_id = a.id WHERE a.nom LIKE ? ORDER BY s.nom";
        List<Service> services = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, "%" + administratorName + "%");
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    services.add(mapResultSetToService(rs));
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des services par nom d'administrateur : " + e.getMessage());
            e.printStackTrace();
        }
        
        return services;
    }
    
    @Override
    public int countEmployeesInService(int serviceId) {
        String sql = "SELECT COUNT(*) FROM Employee WHERE service_id = ?";
        int count = 0;
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, serviceId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt(1);
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Erreur lors du comptage des employés dans le service : " + e.getMessage());
            e.printStackTrace();
        }
        
        return count;
    }
    
    @Override
    public int countActiveTasksInService(int serviceId) {
        String sql = "SELECT COUNT(*) FROM Task WHERE service_id = ? AND statut != 'TERMINE'";
        int count = 0;
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, serviceId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt(1);
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Erreur lors du comptage des tâches actives dans le service : " + e.getMessage());
            e.printStackTrace();
        }
        
        return count;
    }
    
    /**
     * Convertit un ResultSet en objet Service
     * @param rs ResultSet contenant les données
     * @return Objet Service créé
     * @throws SQLException Si une erreur de base de données se produit
     */
    private Service mapResultSetToService(ResultSet rs) throws SQLException {
        Service service = new Service();
        service.setId(rs.getInt("id"));
        service.setNom(rs.getString("nom"));
        service.setDescription(rs.getString("description"));
        service.setAdministratorId(rs.getInt("administrator_id"));
        service.setDateCreation(rs.getTimestamp("dateCreation").toLocalDateTime());
        service.setActif(rs.getBoolean("actif"));
        return service;
    }
}