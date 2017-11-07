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
    public boolean addPerson(Person person) {
        //should I check if this person already is in database?
        PreparedStatement statement = null;
        try {
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

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    /** Adds multiple persons to the database
     * @param personList
     */
    public boolean addAllPersons(Person[] personList) {
        boolean allAdded = true;
        for(Person person: personList) {
            if(!addPerson(person))
                allAdded = false;
        }
        return allAdded;
    }
    //updates existing person in database
    public boolean updateMotherId(String motherId, Person person) {
        PreparedStatement statement = null;
        try {
            String update = "update Person set motherId = ? where personId = ?";
            statement = connection.prepareStatement(update);
            statement.setString(1, motherId);
            statement.setString(2, person.getPersonID());
            statement.executeUpdate();
            statement.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    //updates existing person in database
    public boolean updateFatherId(String fatherId, Person person) {
        PreparedStatement statement = null;
        try{
            String update = "update Person set fatherId = ? where personId = ?";
            statement = connection.prepareStatement(update);
            statement.setString(1, fatherId);
            statement.setString(2, person.getPersonID());
            statement.executeUpdate();
            statement.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return true;
        }
    }
    /** Removes a Person from row in Person table, throws exception if Person does not exist
     * @param person
     * @throws Exception
     */
    public boolean removePerson(Person person) {
        PreparedStatement statement = null;
        try {
            String delete = "delete from Person where personId = ?";
            statement = connection.prepareStatement(delete);
            statement.setString(1, person.getPersonID());
            statement.executeUpdate();
            statement.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
    //removes all Persons from database that are associated with the given username
    public boolean removeAllPersons(String username) {
        PreparedStatement statement = null;
        try{
            String delete = "delete from Person where descendant = ?";
            statement = connection.prepareStatement(delete);
            statement.setString(1, username);
            statement.executeUpdate();
            statement.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    /** Retrieves a row from Person table by personId
     * @param queryPersonId
     * @throws Exception if person does not exist
     * @return person
     */
    public Person getPerson(String queryPersonId) {
        PreparedStatement statement = null;
        ResultSet rs = null;
        Person queriedPerson = null;
        try {
            statement = connection.prepareStatement("select * from Person where personId = ?");
            statement.setString(1, queryPersonId);
            rs = statement.executeQuery();
            while(rs.next()) {
                String personId = rs.getString(1);
                String descendant = rs.getString(2);
                String firstName = rs.getString(3);
                String lastName = rs.getString(4);
                String gender = rs.getString(5);
                String fatherId = rs.getString(6); //could be null
                String motherId = rs.getString(7);
                String spouseId = rs.getString(8);
                queriedPerson = new Person(personId, descendant, firstName, lastName, gender, fatherId, motherId, spouseId);
                break; //do I need this?
            }
            statement.close();
            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return queriedPerson;
    }
    //get all Person objects associated with the given username
    public Person[] getAllPersons(String username) {
        ArrayList<Person> personList = new ArrayList<Person>();
        PreparedStatement statement = null;
        ResultSet rs =  null;
        try {
            statement = connection.prepareStatement("select * from Person where descendant = ?");
            statement.setString(1, username);
            rs = statement.executeQuery();
            while(rs.next()) {
                String personId = rs.getString(1);
                String descendant = rs.getString(2);
                String firstName = rs.getString(3);
                String lastName = rs.getString(4);
                String gender = rs.getString(5);
                String fatherId = rs.getString(6); //could be null
                String motherId = rs.getString(7);
                String spouseId = rs.getString(8);
                Person person = new Person(personId,descendant,firstName,lastName,gender,fatherId,motherId,spouseId);
                personList.add(person);
            }
            Person[] personArray = personList.toArray(new Person[personList.size()]);
            return personArray;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public PersonDao(Connection connection) {

        this.connection = connection;
    }
    public PersonDao() {}

}
