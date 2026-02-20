package gestionnaire_taches.DAO;

import gestionnaire_taches.model.Administrator;
import gestionnaire_taches.Utils.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implémentation du DAO pour la classe Administrator.
 * Gère les opérations CRUD dans la base de données.
 */
public class AdministratorDAO implements GenericDAO<Administrator, Integer> {

    // --- implémentation de l'interface -----------------------------------------

    @Override
    public Administrator findById(Integer id) {          // méthode définie dans GenericDAO
        if (id == null) {
            return null;
        }
        String query = "SELECT * FROM administrator WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToAdministrator(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération de l'administrateur : " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Administrator> findAll() {
        List<Administrator> administrators = new ArrayList<>();
        String query = "SELECT * FROM administrator";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                administrators.add(mapResultSetToAdministrator(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des administrateurs : " + e.getMessage());
        }
        return administrators;
    }

    @Override
    public boolean save(Administrator admin) {
        if (admin == null) {
            return false;
        }
        if (admin.getId() <= 0) {
            return create(admin);
        } else {
            return update(admin);
        }
    }

    @Override
    public boolean delete(Integer id) {
        if (id == null) {
            return false;
        }
        return delete(id.intValue());   // délégation vers la version primitive
    }

    // --- méthodes supplémentaires / compatibilité -------------------------------

    /** ancienne signature – déléguée à findById */
    public Administrator getById(int id) {
        return findById(id);
    }

    /** ancienne signature – déléguée à findAll */
    public List<Administrator> getAll() {
        return findAll();
    }

    /** création explicite (appelée depuis save) */
    public boolean create(Administrator admin) {
        String query = "INSERT INTO administrator (nom, email, password, role, departement, isSuperAdmin) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, admin.getName());
            stmt.setString(2, admin.getMail());
            stmt.setString(3, admin.getPassword());
            stmt.setString(4, admin.getRole());
            stmt.setString(5, admin.getDepartement());
            stmt.setBoolean(6, admin.isSuperAdmin());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erreur lors de la création de l'administrateur : " + e.getMessage());
        }
        return false;
    }

    /** mise à jour explicite (appelée depuis save) */
    @Override
    public boolean update(Administrator admin) {
        String query = "UPDATE administrator SET nom = ?, email = ?, password = ?, role = ?, departement = ?, isSuperAdmin = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, admin.getName());
            stmt.setString(2, admin.getMail());
            stmt.setString(3, admin.getPassword());
            stmt.setString(4, admin.getRole());
            stmt.setString(5, admin.getDepartement());
            stmt.setBoolean(6, admin.isSuperAdmin());
            stmt.setInt(7, admin.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erreur lors de la mise à jour de l'administrateur : " + e.getMessage());
        }
        return false;
    }

    /** suppression explicite par identifiant primitif */
    public boolean delete(int id) {
        String query = "DELETE FROM administrator WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression de l'administrateur : " + e.getMessage());
        }
        return false;
    }

    /** recherche par email */
    public Administrator getByEmail(String email) {
        String query = "SELECT * FROM administrator WHERE email = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToAdministrator(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la recherche par email : " + e.getMessage());
        }
        return null;
    }

    /** mapping interne ResultSet → Administrator */
    private Administrator mapResultSetToAdministrator(ResultSet rs) throws SQLException {
        Administrator admin = new Administrator(
            rs.getInt("id"),
            rs.getString("nom"),
            rs.getString("email"),
            rs.getString("password"),
            rs.getString("role"),
            rs.getString("departement")
        );
        admin.setSuperAdmin(rs.getBoolean("isSuperAdmin"));
        return admin;
    }
}