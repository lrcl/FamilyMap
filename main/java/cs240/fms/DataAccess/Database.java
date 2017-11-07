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
            //connection.setAutoCommit(false);
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
        connection = openConnection(connection);
        String createUser = "create table if not exists User ("
                + "username text not null primary key,"
                + "password text not null,"
                + "email text not null,"
                + "firstName text not null,"
                + "lastName text not null,"
                + "gender text not null,"
                + "personId text not null,"
                + "foreign key(personId) references Person(personId)"
                + ");";
       String createPerson = "create table if not exists Person ("
               + "personId text not null primary key,"
               + "descendant text not null,"
               + "firstName text not null,"
               + "lastName text not null,"
               + "gender text not null,"
               + "fatherId text,"
               + "motherId text,"
               + "spouseId text"
               + ");";
       String createEvent = "create table if not exists Event ("
               + "eventId text not null primary key,"
               + "username text not null,"
               + "personId text not null,"
               + "latitude real not null,"
               + "longitude real not null,"
               + "country text not null,"
               + "city text not null,"
               + "eventType text not null,"
               + "year integer not null,"
               + "foreign key(username) references User(username),"
               + "foreign key(personId) references Person(personId)"
               + ");";
       String createUserAuth = "create table if not exists UserAuth ("
               + "authToken text not null primary key,"
               + "username text not null"
               + ");";
        try {
            statement = connection.prepareStatement(createPerson);
            statement.executeUpdate();

            statement = connection.prepareStatement(createUser);
            statement.executeUpdate();

            statement = connection.prepareStatement(createEvent);
            statement.executeUpdate();

            statement = connection.prepareStatement(createUserAuth);
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
    public boolean dropAllTables(Connection connection) {
        PreparedStatement statement = null;
        connection = openConnection(connection);
        try{
            statement = connection.prepareStatement("drop table if exists Person");
            statement.executeUpdate();

            statement = connection.prepareStatement("drop table if exists Event");
            statement.executeUpdate();

            statement = connection.prepareStatement("drop table if exists User");
            statement.executeUpdate();

            statement = connection.prepareStatement("drop table if exists UserAuth");
            statement.executeUpdate();

            statement.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public Database() {}
}
