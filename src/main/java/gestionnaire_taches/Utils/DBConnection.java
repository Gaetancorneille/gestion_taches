public class DBConnection{
    private static final String URL= "jdbs:mysql://localhost:3306/ gestion_tache?useSSL=false§serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD="";// a modifier selon le config

    private static  Connection Connection= null;
    private static DBConnection( ) {}
    private static  Connection  getConnection() throws SQLException{
        if (connection== null|| connection.isClosed()){
            try{
                Class.forName("com.mysql.cj.jdbc.Drive");
                connection = DriverManager.getConnection(URL,USER,PASSWORD);
            }catch(ClassNotfoundException e){
                System.err.println("pilote JDBC non trouve:"+ e.getmessage());
                throw new SQLException(e);
            }
            
        }
        return connection;
    } 
     public static void closeConnection(){
        if (connection ! = null){
          try{
                 connection.close();
            }catch(SQLException e){
                System.err.println(" erreur lors de la fermeture:"+ e.getmessage());
                
            }
        }
       
     }

}