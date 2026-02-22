package gestionnaire_taches.dao.impl;

import gestionnaire_taches.dao.interfaces.EmployeeDAO;
import gestionnaire_taches.model.Employee;
import gestionnaire_taches.util.DatabaseConnection;
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
        // Implementation de base - à compléter
        return employee;
    }
    
    @Override
    public Employee update(Employee employee) {
        // Implementation de base - à compléter
        return employee;
    }
    
    @Override
    public void delete(Integer id) {
        // Implementation de base - à compléter
    }
    
    @Override
    public Optional<Employee> findById(Integer id) {
        // Implementation de base - à compléter
        return Optional.empty();
    }
    
    @Override
    public List<Employee> findAll() {
        return new ArrayList<>();
    }
    
    @Override
    public long count() {
        return 0;
    }
    
    @Override
    public Optional<Employee> findByEmail(String email) {
        // Implementation de base - à compléter
        return Optional.empty();
    }
    
    @Override
    public Optional<Employee> authenticate(String email, String password) {
        // Implementation de base - à compléter
        return Optional.empty();
    }
    
    @Override
    public List<Employee> getEmployeesByService(Integer serviceId) {
        return new ArrayList<>();
    }
    
    @Override
    public boolean emailExists(String email) {
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