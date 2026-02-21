package gestionnaire_taches.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Interface générique pour les opérations CRUD.
 */
public class AdministratorDAO{
    private Connection connection;   //creation d'un cable vers la base de donnees

    public AdministratorDAO(Connection connection){    //branchement du cable
        this.connection=connection;
    }

    /**
     * create
     */

    @Override
    public boolean create(Administrator obj){
        String sql = "INSERT INTO Administrator (nom, email, password, isSuperAdmin, dateCreation, actif) VALUE (?, ?, ?, ?, ?, ?)";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, obj.getnom());  //remplacer par les valeurs
        pstmt.setString(2, obj.getemail());
        pstmt.setString(3, obj.getpassword());
        pstmt.setBoolean(4, obj.getisSuperAdmin());
        pstmt.setTimeStamp(5, obj.getdateCreation());
        pstmt.setBoolean(6, obj.actif());
        return pstmt.executeUpdate() >0;

        catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * read
     */

    @Override
    public Administrator read(int id){
        String sql = "SELECT* FROM administrator WHERE id=?";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setInt(1, id);
        catch (SQLException e){
            e.printStackTrace();
        }
        //executer et retourner la reponse
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()){
            //creer un objet admin avec les donnees recus
            Administrator admin = new Administrator(
                rs.getString("nom"),
                rs.getString("email"),
                rs.getString("passsword"),
                rs.getTimeStamp("dateCreation"),
            );
            admin.setSuperAdmin(rs.getBoolean("isSuperAdmin"));
            admin.setActif(rs.getBoolean("actif"));
            return admin;  //return admin trouve
        }
        return null;
    }

    /**
     * update
     */

    @Override
     public boolean update(Administrator obj){
        String sql = "UPDATE administrator SET nom=?, email=?, password=? isSuperAdmin=?, dateCreation=? actif=? WHERE id=?";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, obj.getnom());  //remplacer par les valeurs
        pstmt.setString(2, obj.getemail());
        pstmt.setString(3, obj.getpassword());
        pstmt.setBoolean(4, obj.getisSuperAdmin());
        pstmt.setTimeStamp(5, obj.getdateCreation());
        pstmt.setBoolean(6, obj.actif());
        pstmt.setInt(7, id);
        return pstmt.executeUpdate() >0;
        catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * delete
     */

    @Override
    public boolean delete(int id){
        String sql = "DELETE FROM administator WHERE id=?";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setInt(1, id);
        return pstmt.executeUpdate() >0;
        catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

}

public interface DAO<T> {
    T getById(int id);
    List<T> getAll();
    boolean create(T obj);
    boolean update(T obj);
    boolean delete(int id);
}
