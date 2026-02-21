package gestionnaire_taches.dao;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO implements DAO<Employee> {
    private Connection connection;   //creation d'un cable vers la base de donnees

    public EmployeeDAO(Connection connection){    //branchement du cable
        this.connection=connection;
    }


    @Override
    public boolean create(Employee obj) {
        String sql = "INSERT INTO Employee (nom, service_id, password, service_id, poste, dateEmbauche, actif) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, obj.getNom());
            pstmt.setString(2, obj.getservice_id());
            pstmt.setString(3, obj.getpassword());
            pstmt.setInt(4, obj.getservice_id());
            pstmt.setString(5, obj.getposte());
            pstmt.setDate(6, obj.getdateEmbauch());
            pstmt.setBoolean(7, obj.getactif());
            return pstmt.executeUpdate() > 0;
        catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Employee ajoute");
        return false;
    }

    @Override
    public Employee read(int id){
        String sql = "SELECT* FROM Employee WHERE id=?";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setInt(1, id);
        catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Employee> readByService (int serviceId){
        //liste vide pour stocker les employees
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT* FROM Employee WHERE service_id=?";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setInt(1, serviceId);
        catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean update(Employee obj) {
        String sql = "UPDATE Employee SET nom=?, email=?, password=?, service_id=?, poste=?, dateEmbauche=?, actif=?, WHERE id=?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, obj.getnom());
            pstmt.setString(2, obj.getemail());
            pstmt.setString(3, obj.getpassword());
            pstmt.setInt(4, obj.getservice_id());
            pstmt.setString(5, obj.getposte());
            pstmt.setDate(6, obj.getdateEmbauche());
            pstmt.setBoolean(7, obj.getactif());
            pstmt.setInt(8, obj.getId());
            return pstmt.executeUpdate() > 0;
        catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Employee modifie");
        return false;
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM Employee WHERE id=?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Employee suprime");
        return false;
    }

    private Employee mapResultSetToEmployee(ResultSet rs) throws SQLException {
        Employee user = new Employee();
        user.setId(rs.getInt("id"));
        user.setNom(rs.getString("nom"));
        user.setemail(rs.getString("email"));
        user.setpassword(rs.getString("password"));
        user.setservice_id(rs.getInt("service_id"));
        user.setposte(rs.getString("poste"));
        user.setdateEmbauche(rs.getDate("dateEmbauche"));
        user.setdateactif(rs.getBoolean("actif"));
        return user;
    }
}