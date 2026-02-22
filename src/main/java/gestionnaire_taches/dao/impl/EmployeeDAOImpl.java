package gestionnaire_taches.dao.impl;

import gestionnaire_taches.dao.interfaces.EmployeeDAO;
import gestionnaire_taches.model.Employee;
import gestionnaire_taches.util.DatabaseConnection;
import org.mindrot.jbcrypt.BCrypt;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implémentation de l'interface EmployeeDAO
 * Gère toutes les opérations de base de données pour les employés
 */
public class EmployeeDAOImpl implements EmployeeDAO {
    
    @Override
    public Employee save(Employee employee) {
        String sql = "INSERT INTO Employee (nom, email, password, service_id, poste, dateEmbauche, dateCreation, actif) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, employee.getNom());
            stmt.setString(2, employee.getEmail());
            // Hashage BCrypt du mot de passe avant insertion
            String hashedPassword = BCrypt.hashpw(employee.getPassword(), BCrypt.gensalt());
            stmt.setString(3, hashedPassword);
            stmt.setInt(4, employee.getServiceId());
            stmt.setString(5, employee.getPoste());
            stmt.setDate(6, employee.getDateEmbauche() != null ? Date.valueOf(employee.getDateEmbauche()) : null);
            stmt.setTimestamp(7, Timestamp.valueOf(employee.getDateCreation()));
            stmt.setBoolean(8, employee.isActif());
            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Échec de la création de l'employé, aucune ligne affectée.");
            }
            
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    employee.setId(generatedKeys.getInt(1));
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'enregistrement de l'employé : " + e.getMessage());
            e.printStackTrace();
        }
        
        return employee;
    }
    
    @Override
    public Employee update(Employee employee) {
        String sql = "UPDATE Employee SET nom = ?, email = ?, service_id = ?, poste = ?, actif = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, employee.getNom());
            stmt.setString(2, employee.getEmail());
            stmt.setInt(3, employee.getServiceId());
            stmt.setString(4, employee.getPoste());
            stmt.setBoolean(5, employee.isActif());
            stmt.setInt(6, employee.getId());
            
            stmt.executeUpdate();
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la mise à jour de l'employé : " + e.getMessage());
            e.printStackTrace();
        }
        
        return employee;
    }
    
    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM Employee WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            stmt.executeUpdate();
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression de l'employé : " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    @Override
    public Optional<Employee> findById(Integer id) {
        String sql = "SELECT * FROM Employee WHERE id = ?";
        Employee employee = null;
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    employee = mapResultSetToEmployee(rs);
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la recherche de l'employé : " + e.getMessage());
            e.printStackTrace();
        }
        
        return Optional.ofNullable(employee);
    }
    
    @Override
    public List<Employee> findAll() {
        String sql = "SELECT * FROM Employee ORDER BY nom";
        List<Employee> employees = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                employees.add(mapResultSetToEmployee(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des employés : " + e.getMessage());
            e.printStackTrace();
        }
        
        return employees;
    }
    
    @Override
    public long count() {
        String sql = "SELECT COUNT(*) FROM Employee";
        long count = 0;
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            if (rs.next()) {
                count = rs.getLong(1);
            }
            
        } catch (SQLException e) {
            System.err.println("Erreur lors du comptage des employés : " + e.getMessage());
            e.printStackTrace();
        }
        
        return count;
    }
    
    @Override
    public Optional<Employee> findByEmail(String email) {
        String sql = "SELECT * FROM Employee WHERE email = ?";
        Employee employee = null;
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, email);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    employee = mapResultSetToEmployee(rs);
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la recherche par email : " + e.getMessage());
            e.printStackTrace();
        }
        
        return Optional.ofNullable(employee);
    }
    
    @Override
    public Optional<Employee> authenticate(String email, String password) {
        // On récupère d'abord l'employé par email, puis on vérifie le mot de passe avec BCrypt
        String sql = "SELECT * FROM Employee WHERE email = ? AND actif = true";
        Employee employee = null;
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, email);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Employee candidate = mapResultSetToEmployee(rs);
                    // Vérification BCrypt : comparaison du mot de passe en clair avec le hash stocké
                    if (BCrypt.checkpw(password, candidate.getPassword())) {
                        employee = candidate;
                    }
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'authentification de l'employé : " + e.getMessage());
            e.printStackTrace();
        }
        
        return Optional.ofNullable(employee);
    }
    
    @Override
    public List<Employee> getEmployeesByService(Integer serviceId) {
        return new ArrayList<>();
    }
    
    @Override
    public boolean emailExists(String email) {
        String sql = "SELECT COUNT(*) FROM Employee WHERE email = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, email);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la vérification de l'email : " + e.getMessage());
            e.printStackTrace();
        }
        
        return false;
    }
    
    @Override
    public boolean updatePassword(Integer id, String newPassword) {
        String sql = "UPDATE Employee SET password = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, newPassword);
            stmt.setInt(2, id);
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la mise à jour du mot de passe : " + e.getMessage());
            e.printStackTrace();
        }
        
        return false;
    }
    
    @Override
    public List<Employee> findByServiceId(int serviceId) {
        String sql = "SELECT * FROM Employee WHERE service_id = ? ORDER BY nom";
        List<Employee> employees = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, serviceId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    employees.add(mapResultSetToEmployee(rs));
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des employés par service : " + e.getMessage());
            e.printStackTrace();
        }
        
        return employees;
    }
    
    @Override
    public List<Employee> findByServiceName(String serviceName) {
        String sql = "SELECT e.* FROM Employee e JOIN Service s ON e.service_id = s.id WHERE s.nom LIKE ? ORDER BY e.nom";
        List<Employee> employees = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, "%" + serviceName + "%");
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    employees.add(mapResultSetToEmployee(rs));
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des employés par nom de service : " + e.getMessage());
            e.printStackTrace();
        }
        
        return employees;
    }
    
    /**
     * Convertit un ResultSet en objet Employee
     * @param rs ResultSet contenant les données
     * @return Objet Employee créé
     * @throws SQLException Si une erreur de base de données se produit
     */
    private Employee mapResultSetToEmployee(ResultSet rs) throws SQLException {
        Employee employee = new Employee();
        employee.setId(rs.getInt("id"));
        employee.setNom(rs.getString("nom"));
        employee.setEmail(rs.getString("email"));
        employee.setPassword(rs.getString("password"));
        employee.setServiceId(rs.getInt("service_id"));
        employee.setDateCreation(rs.getTimestamp("dateCreation").toLocalDateTime());
        employee.setActif(rs.getBoolean("actif"));
        return employee;
    }
}