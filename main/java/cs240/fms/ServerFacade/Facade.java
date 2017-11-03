package cs240.fms.ServerFacade;


import android.support.annotation.NonNull;

import java.sql.Connection;
import java.util.AbstractQueue;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import cs240.fms.DataAccess.Database;
import cs240.fms.DataAccess.EventDao;
import cs240.fms.DataAccess.PersonDao;
import cs240.fms.DataAccess.UserAuthDao;
import cs240.fms.DataAccess.UserDao;
import cs240.fms.Models.Person;
import cs240.fms.Models.User;
import cs240.fms.Models.UserAuth;
import cs240.fms.ServerFacade.DataHandling.Generator;

public class Facade {

    /** registers a new, non-existing user
     * @param registerInfo
     * @return response
     */
    public RegisterResponse register(RegisterRequest registerInfo) {

        User user = new User(registerInfo.getUsername(), registerInfo.getPassword(),registerInfo.getEmail(),registerInfo.getFirstName(),registerInfo.getLastName(),registerInfo.getGender(),"");
        Database database =  new Database();
        Connection connection = null;
        connection = database.openConnection(connection);
        UserDao userDao = new UserDao(connection);
        boolean added = userDao.addUser(user);
        if(!added) {
            return null;
        }
        /*//create person for this user and add to database
        Generator g = new Generator();
        Person p = g.createPersonFromUser(user);
        PersonDao pd = new PersonDao(connection);
        pd.addPerson(p);
*/
        FillRequest fillInfo = new FillRequest(user.getUsername(), 4);
        //user is generation #0 and can be married or single
        fill(fillInfo);
        LoginRequest lr = new LoginRequest(registerInfo.getUsername(),registerInfo.getPassword());
        RegisterResponse rr = login(lr);
        return rr;
    }
    /** Logs a user in:
     * generates authToken and adds username and authToken to UserAuth table in database
     *
     * @param loginInfo
     * @return response
     */
    public RegisterResponse login(LoginRequest loginInfo) {
        //generate authToken
        String longAuthToken = UUID.randomUUID().toString();
        String authToken = longAuthToken.substring(0,7);
        //add to table
        UserAuth userAuth = new UserAuth(authToken, loginInfo.getUsername());
        Database db = new Database();
        Connection connection = null;
        connection = db.openConnection(connection);
        UserAuthDao uad = new UserAuthDao(connection);
        boolean added = uad.addUserAuth(userAuth);
        if (!added) {
            return null;
        }
        //query for personId
        UserDao userDao = new UserDao(connection);
        String personId = userDao.getPersonIdByLogin(loginInfo.getUsername(), loginInfo.getPassword());
        RegisterResponse rr = new RegisterResponse(authToken, loginInfo.getUsername(), personId);
        return rr;
    }

    /** Clears all information from database
     *
     */
    public void clear() {}

    /** Popluates the database with generated information for the given, existing username
     * @param fillInfo
     */
    public void fill(FillRequest fillInfo) {
        //make sure user is registered?
        //clear all events, and people from db that are associated with the given username
        Database db = new Database();
        Connection connection = null;
        connection = db.openConnection(connection);
        String username = fillInfo.getUsername();
         //retrieve user (check if null?)
        UserDao ud = new UserDao(connection);
        User pseudoUser = new User(fillInfo.getUsername());
        User existingUser = ud.getUser(pseudoUser);
        if(existingUser == null) {
            //do something?
        }
        //remove Persons associated with username
        PersonDao pd = new PersonDao(connection);
        pd.removeAllPersons(username);
        //remove Events associated with username
        EventDao ed = new EventDao(connection);
        ed.removeAllEvents(username);
        //queue
        Generator g = new Generator();
        Person p1 = g.createPersonFromUser(existingUser);
        BlockingQueue<Person> persons = new LinkedBlockingQueue<Person>();




        //generate events for the created couples and for user
        //add events to db
    }
    private ArrayList<Person> addToList(ArrayList<Person> couple) {
        ArrayList<Person> personList = new ArrayList<Person>();
        for(Person person: couple) {
            personList.add(person);
        }
        return personList;
    }

    /** clears all data from database and loads new information about user, person and event
     * @param loadInfo
     */
    public void load(LoadRequest loadInfo) {}

    /** returns the single Person object with the specified ID
     *
     * @param authToken
     * @param personID
     * @return person
     */
    public PersonResponse findPerson(String authToken, String personID) {return null;}

    /** returns all family members of the current user. The current user is determined from the provided auth token
     *
     * @param authToken
     * @return allPersons
     */
    public AllPersonsResponse findPersons(String authToken) {return null;}

    /** returns single Event object with the specified event ID
     * @param eventId
     * @param authToken
     * @return event
     */
    public EventResponse findEvent(String authToken, String eventId) {return null;}

    /** returns all events for all family members of the current user. Current user is determined by the provided auth token
     * @param authToken
     * @return allEvents
     */
    public AllEventsResponse findEvents(String authToken) {return null;}
}


