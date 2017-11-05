package cs240.fms.ServerFacade;


import android.support.annotation.NonNull;

import java.sql.Connection;
import java.sql.SQLException;
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
import cs240.fms.Models.Event;
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
    public void clear() {
        Database db = new Database();
        Connection connection = null;
        connection = db.openConnection(connection);
        db.dropAllTables(connection);
    }

    /** Popluates the database with generated information for the given, existing username
     * @param fillInfo
     */
    public void fill(FillRequest fillInfo) {

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
        //queue up: generate and connect people
        Generator g = new Generator();
        g.loadData();
        Person p1 = g.createPersonFromUser(existingUser);
        BlockingQueue<Person> personQueue = new LinkedBlockingQueue<Person>();
        personQueue.add(p1);
        //create events for person1
        //get needed total person count based on #of desired generations
        int sum = peopleSum(fillInfo.getGenerations());
        int i = 1;
        int gen = 1;
        while(i < sum) {
            try {
                Person child = personQueue.take();
                pd.addPerson(child);

                Person mother = g.createPersonFromFile(username, "f");
                Person father = g.createPersonFromFile(username, "m");

                //match spouseIds
                mother.setSpouseID(father.getPersonID());
                father.setSpouseID(mother.getPersonID());

                //birth events for each parent
                Event birth1 = g.createBirth(gen, username, mother.getPersonID());
                ed.addEvent(birth1);
                Event birth2 = g.createBirth(gen, username, father.getPersonID());
                ed.addEvent(birth2);
                //mother and father have same marriage event
                Event marriage = g.createMarriage(gen, username, mother.getPersonID());
                Event marriage2 = new Event(marriage);
                marriage2.setPersonID(father.getPersonID());
                ed.addEvent(marriage);
                ed.addEvent(marriage2);
                //death events
                Event death1 = g.createDeathDate(gen,username,mother.getPersonID());
                ed.addEvent(death1);
                Event death2 = g.createDeathDate(gen,username,father.getPersonID());
                ed.addEvent(death2);

                //update child info
                pd.updateMotherId(mother.getPersonID(), child);
                pd.updateFatherId(father.getPersonID(), child);
                //add to database
                pd.addPerson(mother);
                pd.addPerson(father);
                //put on queue
                personQueue.add(mother);
                personQueue.add(father);
                i += 2;
                if((double)i > Math.pow(2,(double)gen)){
                    gen++;
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (SQLException es) {
                es.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        //generate events for the created couples and for user
        //add events to db
    }
    private int peopleSum (int generations) {
        double total = 0;
        double gen = (double)generations;
        for(int i = 0; i < gen+1; i++) {
            total += Math.pow(2,gen);
        }
        int sum = (int)total;
        return sum;
    }

    /** clears all data from database and loads new information about user, person and event
     * @param loadInfo
     */
    public void load(LoadRequest loadInfo) {

    }

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


