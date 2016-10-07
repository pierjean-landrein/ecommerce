package ecommerce.DBService;

import javax.xml.transform.Result;
import java.sql.*;


public class DBService {
    private static DBService instance = null;
    private Connection connection = null;

    private DBService(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://192.168.0.49/ecommerce?user=root&password=formation&useSSL=false");
        } catch (ClassNotFoundException e ) {
            System.err.println("Impossible de trouver le driver JDBC"+e.getMessage());
        } catch (SQLException e) {
            System.err.println("Impossible de se connecter a la DB"+e.getMessage());
        }
    }

    public static DBService getInstance() {
        if (instance == null) {
            instance = new DBService();
        }
        return instance;
    }

    public Statement createStatement(){
        Statement st = null;
        try {
            st = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return st;
    }

    public PreparedStatement createPreparedStatement(String requete){
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(requete);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ps;
    }

    public ResultSet executeSelect(String requete) {
        ResultSet rs = null;
        try {
            Statement st = connection.createStatement();
            rs  = st.executeQuery(requete);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public void close(){
        if(connection != null){
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}




