package cs240.fms.DataAccess;


import java.security.spec.ECField;
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
    //create tables
    public boolean createTables(Connection connection) {
        PreparedStatement statement = null;
        String createUser = "create table if not exists User (\n"
                + "username text not null primary key, \n"
                + "password text not null, \n"
                + "email text not null, \n"
                + "firstName text not null, \n"
                + "lastName text not null, \n"
                + "gender text not null, \n"
                + "personId text not null foreign key\n"
                + ");";
       // String createPerson = "create table if not exists Person"
        try {
            statement = connection.prepareStatement(createUser);
            statement.executeUpdate();
            statement.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    //drop all tables --can I reuse the statement to do multiple sql statements?
    public void dropAllTables(Connection connection) {
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement("drop if exists Person");
            statement.executeUpdate();

            statement = connection.prepareStatement("drop if exists Event");
            statement.executeUpdate();

            statement = connection.prepareStatement("drop if exists User");
            statement.executeUpdate();

            statement = connection.prepareStatement("drop if exists UserAuth");
            statement.executeUpdate();

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public Database() {}
}
