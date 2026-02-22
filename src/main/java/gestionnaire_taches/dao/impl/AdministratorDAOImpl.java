package gestionnaire_taches.dao.impl;

import gestionnaire_taches.dao.interfaces.AdministratorDAO;
import gestionnaire_taches.model.Administrator;
import gestionnaire_taches.util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implémentation de l'interface AdministratorDAO
 * Gère toutes les opérations de base de données pour les administrateurs
 */
public class AdministratorDAOImpl implements AdministratorDAO {
    
    /**
     * Enregistre un nouvel administrateur dans la base de données
     * @param administrator Administrateur à enregistrer
     * @return Administrateur enregistré avec son ID généré
     */
    @Override
    public Administrator save(Administrator administrator) {
        String sql = "INSERT INTO Administrator (nom, email, password, isSuperAdmin, dateCreation, actif) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, administrator.getNom());
            stmt.setString(2, administrator.getEmail());
            stmt.setString(3, administrator.getPassword());
            stmt.setBoolean(4, administrator.isSuperAdmin());
            stmt.setTimestamp(5, Timestamp.valueOf(administrator.getDateCreation()));
            stmt.setBoolean(6, administrator.isActif());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Échec de la création de l'administrateur, aucune ligne affectée.");
            }
            
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    administrator.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Échec de la création de l'administrateur, aucun ID obtenu.");
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'enregistrement de l'administrateur : " + e.getMessage());
            e.printStackTrace();
        }
        
        return administrator;
    }
    
    /**
     * Met à jour un administrateur existant dans la base de données
     * @param administrator Administrateur à mettre à jour
     * @return Administrateur mis à jour
     */
    @Override
    public Administrator update(Administrator administrator) {
        String sql = "UPDATE Administrator SET nom = ?, email = ?, isSuperAdmin = ?, actif = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, administrator.getNom());
            stmt.setString(2, administrator.getEmail());
            stmt.setBoolean(3, administrator.isSuperAdmin());
            stmt.setBoolean(4, administrator.isActif());
            stmt.setInt(5, administrator.getId());
            
            stmt.executeUpdate();
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la mise à jour de l'administrateur : " + e.getMessage());
            e.printStackTrace();
        }
        
        return administrator;
    }
    
    /**
     * Supprime un administrateur de la base de données par son ID
     * @param id ID de l'administrateur à supprimer
     */
    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM Administrator WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            stmt.executeUpdate();
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression de l'administrateur : " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Trouve un administrateur par son ID
     * @param id ID de l'administrateur recherché
     * @return Administrateur trouvé ou null si non trouvé
     */
    @Override
    public Optional<Administrator> findById(Integer id) {
        String sql = "SELECT * FROM Administrator WHERE id = ?";
        Administrator administrator = null;
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    administrator = mapResultSetToAdministrator(rs);
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la recherche de l'administrateur : " + e.getMessage());
            e.printStackTrace();
        }
        
        return Optional.ofNullable(administrator);
    }
    
    /**
     * Récupère tous les administrateurs
     * @return Liste de tous les administrateurs
     */
    @Override
    public List<Administrator> findAll() {
        String sql = "SELECT * FROM Administrator ORDER BY nom";
        List<Administrator> administrators = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                administrators.add(mapResultSetToAdministrator(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des administrateurs : " + e.getMessage());
            e.printStackTrace();
        }
        
        return administrators;
    }
    
    /**
     * Compte le nombre total d'administrateurs
     * @return Nombre total d'administrateurs
     */
    @Override
    public long count() {
        String sql = "SELECT COUNT(*) FROM Administrator";
        long count = 0;
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            if (rs.next()) {
                count = rs.getLong(1);
            }
            
        } catch (SQLException e) {
            System.err.println("Erreur lors du comptage des administrateurs : " + e.getMessage());
            e.printStackTrace();
        }
        
        return count;
    }
    
    /**
     * Trouve un administrateur par son adresse email
     * @param email Adresse email de l'administrateur
     * @return Administrateur trouvé ou null si non trouvé
     */
    @Override
    public Optional<Administrator> findByEmail(String email) {
        String sql = "SELECT * FROM Administrator WHERE email = ?";
        Administrator administrator = null;
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, email);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    administrator = mapResultSetToAdministrator(rs);
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la recherche par email : " + e.getMessage());
            e.printStackTrace();
        }
        
        return Optional.ofNullable(administrator);
    }
    
    /**
     * Authentifie un administrateur avec son email et mot de passe
     * @param email Adresse email de l'administrateur
     * @param password Mot de passe (doit être hashé)
     * @return Administrateur authentifié ou null si échec
     */
    @Override
    public Optional<Administrator> authenticate(String email, String password) {
        String sql = "SELECT * FROM Administrator WHERE email = ? AND password = ? AND actif = true";
        Administrator administrator = null;
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, email);
            stmt.setString(2, password);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    administrator = mapResultSetToAdministrator(rs);
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'authentification : " + e.getMessage());
            e.printStackTrace();
        }
        
        return Optional.ofNullable(administrator);
    }
    
    /**
     * Vérifie si un email est déjà utilisé
     * @param email Adresse email à vérifier
     * @return true si l'email existe déjà
     */
    @Override
    public boolean emailExists(String email) {
        String sql = "SELECT COUNT(*) FROM Administrator WHERE email = ?";
        boolean exists = false;
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, email);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    exists = rs.getInt(1) > 0;
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la vérification de l'email : " + e.getMessage());
            e.printStackTrace();
        }
        
        return exists;
    }
    
    /**
     * Met à jour le mot de passe d'un administrateur
     * @param id ID de l'administrateur
     * @param newPassword Nouveau mot de passe hashé
     * @return true si la mise à jour a réussi
     */
    @Override
    public boolean updatePassword(Integer id, String newPassword) {
        String sql = "UPDATE Administrator SET password = ? WHERE id = ?";
        boolean success = false;
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, newPassword);
            stmt.setInt(2, id);
            
            int rowsAffected = stmt.executeUpdate();
            success = rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la mise à jour du mot de passe : " + e.getMessage());
            e.printStackTrace();
        }
        
        return success;
    }
    
    /**
     * Convertit un ResultSet en objet Administrator
     * @param rs ResultSet contenant les données
     * @return Objet Administrator créé
     * @throws SQLException Si une erreur de base de données se produit
     */
    private Administrator mapResultSetToAdministrator(ResultSet rs) throws SQLException {
        Administrator administrator = new Administrator();
        administrator.setId(rs.getInt("id"));
        administrator.setNom(rs.getString("nom"));
        administrator.setEmail(rs.getString("email"));
        administrator.setPassword(rs.getString("password"));
        administrator.setSuperAdmin(rs.getBoolean("isSuperAdmin"));
        administrator.setDateCreation(rs.getTimestamp("dateCreation").toLocalDateTime());
        administrator.setActif(rs.getBoolean("actif"));
        return administrator;
    }
}