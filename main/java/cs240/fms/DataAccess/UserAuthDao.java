package cs240.fms.DataAccess;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import cs240.fms.Models.UserAuth;

public class UserAuthDao {

    //connection variable
    Connection connection;

    /** Adds an userAuth row to UserAuth table in database
     * @param userAuth
     */
    public boolean addUserAuth(UserAuth userAuth) {
        PreparedStatement statement = null;
        try {
            String insert = "insert into UserAuth values (?, ?)";
            statement = connection.prepareStatement(insert);
            statement.setString(1, userAuth.getAuthToken());
            statement.setString(2, userAuth.getUsername());
            statement.executeUpdate();
            statement.close();
            return true;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        //closeConnection(false);
    }

    /** Removes an UserAuth from row in UserAuth table throws exception if UserAuth does not exist
     * @param userAuth
     * @throws Exception
     */
    public void removeUserAuth(UserAuth userAuth) throws Exception {
        PreparedStatement statement = null;
        try {
            //openConnection();
            String delete = "delete from UserAuth where authToken = ?";
            statement = connection.prepareStatement(delete);
            statement.setString(1, userAuth.getAuthToken());
            statement.executeUpdate();
            statement.close();
            //closeConnection(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        statement.close();
        //closeConnection(false);
    }

    /** Retrieves a row from UserAuth table throws exception if UserAuth does not exist
     * @param queryAuthToken
     * @throws Exception
     * @return userAuth
     */
    public UserAuth getUserAuth(String queryAuthToken) {
        PreparedStatement statement = null;
        ResultSet rs = null;
        UserAuth queriedUserAuth = null;
        try {
            statement = connection.prepareStatement("select * from UserAuth where authToken = ?");
            statement.setString(1, queryAuthToken);
            rs = statement.executeQuery();
            while(rs.next()) {
                String authToken = rs.getString(1);
                String username = rs.getString(2);
                queriedUserAuth = new UserAuth(authToken, username);
                break;
            }
            rs.close();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return queriedUserAuth;
    }
    public UserAuthDao(Connection connection) {
        this.connection = connection;
    }
    public UserAuthDao() {}

}
