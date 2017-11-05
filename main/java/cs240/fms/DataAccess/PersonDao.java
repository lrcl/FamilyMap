package cs240.fms.DataAccess;

import cs240.fms.Models.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class PersonDao {
    //connection variable
    private Connection connection;

    /** Adds a person row to Person table in database
     * @param person
     */
    public void addPerson(Person person) {
        //should I check if this person already is in database?
        PreparedStatement statement = null;
        try {
            //openConnection();
            String insert = "insert into Person values (?, ?, ?, ?, ?, ?, ?, ?)";
            statement = connection.prepareStatement(insert);
            statement.setString(1, person.getPersonID());
            statement.setString(2, person.getDescendant());
            statement.setString(3, person.getFirstName());
            statement.setString(4, person.getLastName());
            statement.setString(5, person.getGender());
            statement.setString(6, person.getFatherID());
            statement.setString(7, person.getMotherID());
            statement.setString(8, person.getSpouseID());
            statement.executeUpdate();
            statement.close();
            //closeConnection(true);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /** Adds multiple persons to the database
     * @param personList
     */
    public void addAllPersons(Person[] personList) throws Exception{
        for(Person person: personList) {
            addPerson(person);
        }
    }
    //updates existing person in database
    public void updateMotherId(String motherId, Person person) {
        PreparedStatement statement = null;
        try {
            String update = "update Person set motherId = ? where personId = ?";
            statement = connection.prepareStatement(update);
            statement.setString(1, motherId);
            statement.setString(2, person.getPersonID());
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //updates existing person in database
    public void updateFatherId(String fatherId, Person person) {
        PreparedStatement statement = null;
        try{
            String update = "update Person set father Id = ? where personId = ?";
            statement = connection.prepareStatement(update);
            statement.setString(1, fatherId);
            statement.setString(2, person.getPersonID());
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /** Removes a Person from row in Person table, throws exception if Person does not exist
     * @param person
     * @throws Exception
     */
    public void removePerson(Person person) {
        PreparedStatement statement = null;
        try {
            String delete = "delete from Person where personId = ?";
            statement = connection.prepareStatement(delete);
            statement.setString(1, person.getPersonID());
            statement.executeUpdate();
            statement.close();
            //closeConnection(true);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    //removes all Persons from database that are associated with the given username
    public void removeAllPersons(String username) {
        PreparedStatement statement = null;
        try{
            String delete = "delete from Person where descendant = ?";
            statement = connection.prepareStatement(delete);
            statement.setString(1, username);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /** Retrieves a row from Person table by personId
     * @param person
     * @throws Exception if person does not exist
     * @return person
     */
    public Person getPerson(Person person) throws Exception {
        PreparedStatement statement = null;
        ResultSet rs = null;
        Person queriedPerson = null;
        try {
            //openConnection();
            String queryPersonId = person.getPersonID();
            statement = connection.prepareStatement("select * from Person where personId = ?");
            statement.setString(1, queryPersonId);
            rs = statement.executeQuery();
            while(rs.next()) {
                String personId = rs.getString(1);
                String descendant = rs.getString(2);
                String firstName = rs.getString(3);
                String lastName = rs.getString(4);
                String gender = rs.getString(5);
                String fatherId = rs.getString(6); //could be null?
                String motherId = rs.getString(7);
                String spouseId = rs.getString(8);
                queriedPerson = new Person(personId, descendant, firstName, lastName, gender, fatherId, motherId, spouseId);
                //closeConnection(true);
            }
            statement.close();
            rs.close();
            return queriedPerson;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public PersonDao(Connection connection) {

        this.connection = connection;
    }
    public PersonDao() {}

}
