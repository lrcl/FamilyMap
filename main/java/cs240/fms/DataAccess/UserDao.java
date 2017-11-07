package cs240.fms.DataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import cs240.fms.Models.User;

public class UserDao {
    //connection variable
    Connection connection;

    /** Adds a user row to User table in database
     * @param user
     */
    public boolean addUser(User user) {
        PreparedStatement statement = null;
        try {
            String insert = "insert into User values (?, ?, ?, ?, ?, ?, ?)";
            statement = connection.prepareStatement(insert);
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getFirstName());
            statement.setString(5, user.getLastName());
            statement.setString(6, user.getGender());
            statement.setString(7, user.getPersonId());
            statement.executeUpdate();
            statement.close();

        } catch (SQLException e) {
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    return true;
    }
    public String getPersonIdByLogin(String username, String password) {
        PreparedStatement statement = null;
        ResultSet rs = null;
        String personId = "";
        try {
            //openConnection();
            statement = connection.prepareStatement("select * from User where username = ? and password = ?");
            statement.setString(1, username);
            statement.setString(2, password);
            rs = statement.executeQuery();
            while(rs.next()) {
                personId = rs.getString(1);
                break;
            }
            statement.close();
            rs.close();
            return personId;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        // statement.close();
        // rs.close();
        return null;
    }
    /** adds multiple users to database
     * @param userList
     */
    public boolean addAllUsers(User[] userList) {
        boolean allAdded = true;
        for(User user: userList) {
            if(!addUser(user))
                allAdded = false;
        }
        return allAdded;
    }

    /** Removes a user from row in User table, throws exception if user does not exist
     * @param user
     * @throws Exception
     */
    public void removeUser(User user) throws Exception {
        PreparedStatement statement = null;
       try {
           //openConnection();
           String delete = "delete from User where username = ?";
           statement = connection.prepareStatement(delete);
           statement.setString(1, user.getUsername());
           statement.executeUpdate();
           statement.close();
           //closeConnection(true);
       } catch (SQLException e) {
           e.printStackTrace();
       }
    }

    /** Retrieves a row from User table throws exception if user does not exist
     * @param user
     * @throws Exception
     * @return user
     */
    public User getUser(User user) {
        PreparedStatement statement = null;
        ResultSet rs = null;
        User queriedUser = null;
        try {
            //openConnection();
            String queryUsername = user.getUsername();
            statement = connection.prepareStatement("select * from User where username = ?");
            statement.setString(1, queryUsername);
            rs = statement.executeQuery();

            while (rs.next()) {
                String username = rs.getString(1);
                String password = rs.getString(2);
                String email = rs.getString(3);
                String firstName = rs.getString(4);
                String lastName = rs.getString(5);
                String gender = rs.getString(6);
                String personId = rs.getString(7);
                queriedUser = new User(username, password, email, firstName, lastName, gender, personId);
                //closeConnection(true);
            }
            rs.close();
            statement.close();
            return queriedUser;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public UserDao(Connection connection) {
        this.connection = connection;
    }
    public UserDao() {}

}
