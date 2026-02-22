package gestionnaire_taches.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost/gestion_taches?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private static final String USER = "root";
    private static final String PASSWORD = ""; // Modifier en fonction de la configuration
    
    private static Connection connection = null;
    
    private DatabaseConnection() {}
    
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (ClassNotFoundException e) {
                System.err.println("JDBC driver not found: " + e.getMessage());
                throw new SQLException(e);
            }
        }
        return connection;
    }
    
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
    }
    
    public static void beginTransaction() throws SQLException {
        getConnection().setAutoCommit(false);
    }
    
    public static void commit() throws SQLException {
        getConnection().commit();
        getConnection().setAutoCommit(true);
    }
    
    public static void rollback() throws SQLException {
        getConnection().rollback();
        getConnection().setAutoCommit(true);
    }
}