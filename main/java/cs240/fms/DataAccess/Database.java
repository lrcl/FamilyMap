package cs240.fms.DataAccess;


import java.sql.Connection;
import java.sql.*;

public class Database {


    static {
        try {
            final String driver = "org.sqlite.JDBC";
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public Connection openConnection(Connection connection) {
        try {
            final String CONNECTION_URL = "jdbc:sqlite:spellcheck.sqlite";
            connection = DriverManager.getConnection(CONNECTION_URL);
            connection.setAutoCommit(false);
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public void closeConnection(boolean commit, Connection connection) throws Exception {
        try{
            if(commit) {
                connection.commit();
            }
            connection.close();
            connection = null;

        }catch (SQLException e) {
            throw new Exception("closeConnection failed", e);
        }
    }
    public Database() {}
}
