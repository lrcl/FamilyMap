package cs240.fms.ServerFacade;


import java.sql.Connection;
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
        if(userDao.getUser(user.getUsername()) != null)
            //username is taken
            return null;
        //create personId
        Generator g = new Generator();
        user.setPersonId(g.createId());
        boolean added = userDao.addUser(user);
        if(!added) {
            return null;
        }
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
        //check if user is registered
        UserDao userDao = new UserDao(connection);
        if(userDao.getUser(loginInfo.getUsername()) == null)
            //user is non-existent
            return null;
        UserAuthDao uad = new UserAuthDao(connection);
        boolean added = uad.addUserAuth(userAuth);
        if (!added) {
            return null;
        }
        //query for personId
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
    public boolean fill(FillRequest fillInfo) {

        if(fillInfo.getGenerations() < 0) {
            //invalid generation parameter
            return false;
        }
        Database db = new Database();
        Connection connection = null;
        connection = db.openConnection(connection);
        String username = fillInfo.getUsername();

         //retrieve user (check if null)
        UserDao ud = new UserDao(connection);
        User existingUser = ud.getUser(fillInfo.getUsername());
        if(existingUser == null) {
            return false;
        }
        //remove Persons associated with username
        PersonDao pd = new PersonDao(connection);
        pd.removeAllPersons(username);
        //remove Events associated with username
        EventDao ed = new EventDao(connection);
        ed.removeAllEvents(username);

        //generate p1 and p1's events
        Generator g = new Generator();
        g.loadData();
        Person p1 = g.createPersonFromUser(existingUser);
        Event p1Birth = g.createBirth(0, username, p1.getPersonID());
        Event p1Marriage = g.createMarriage(0, username, p1.getPersonID());
        try {
            ed.addEvent(p1Birth);
            ed.addEvent(p1Marriage);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        //using a queue, create and match each generation of people with events
        int sum = peopleSum(fillInfo.getGenerations());
        if(matchPeopleWithEvents(g, pd, ed, username, p1, sum))
            return false;

        return true;
    }
    private boolean matchPeopleWithEvents(Generator g, PersonDao pd, EventDao ed, String username, Person p1, int sum){

        BlockingQueue<Person> personQueue = new LinkedBlockingQueue<Person>();
        personQueue.add(p1);
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
                return false;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }

        }
        return true;
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
    public boolean load(LoadRequest loadInfo) {
        //check for valid input
        if(loadInfo.getEvents() == null)
            return false;
        if(loadInfo.getUsers() == null)
            return false;
        if(loadInfo.getPersons() == null)
            return false;

        boolean allAdded = true;
        //clear all data from database
        clear();
       //open connection
        Database db = new Database();
        Connection connection = null;
        connection = db.openConnection(connection);

        UserDao ud = new UserDao(connection);
        if(!ud.addAllUsers(loadInfo.getUsers()))
            allAdded = false;

        PersonDao pd = new PersonDao(connection);
        if(!pd.addAllPersons(loadInfo.getPersons()))
            allAdded = false;

        EventDao ed = new EventDao(connection);
        if(!ed.addAllEvents(loadInfo.getEvents()))
            allAdded = false;

        return allAdded;
    }

    /** returns the single Person object with the specified ID
     *
     * @param authToken
     * @param personId
     * @return person
     */
    public Person findPerson(String authToken, String personId) {

        Database db = new Database();
        Connection connection = null;
        connection = db.openConnection(connection);
        PersonDao pDao = new PersonDao(connection);
        //check personId
        Person person = pDao.getPerson(personId);
        if(person == null)
            return null;
        //check authToken
        UserAuthDao uaDao = new UserAuthDao(connection);
        UserAuth ua = uaDao.getUserAuth(authToken);
        if(ua == null)
            return null;
        //check if requested person belongs to user
        if(!(ua.getUsername().equals(person.getDescendant())))
            return null;
        return person;
    }

    /** returns all family members of the current user. The current user is determined from the provided auth token
     *
     * @param authToken
     * @return allPersons
     */
    public Person[] findPersons(String authToken) {

        Database db = new Database();
        Connection connection = null;
        connection = db.openConnection(connection);
        //check authToken
        UserAuthDao uaDao = new UserAuthDao(connection);
        UserAuth ua = uaDao.getUserAuth(authToken);
        if(ua == null)
            return null;
        PersonDao pDao = new PersonDao(connection);
        Person[] personArray = pDao.getAllPersons(ua.getUsername());

        return personArray;
    }

    /** returns single Event object with the specified event ID
     * @param eventId
     * @param authToken
     * @return event
     */
    public Event findEvent(String authToken, String eventId) {
        Database db = new Database();
        Connection connection = null;
        connection = db.openConnection(connection);
        EventDao eDao = new EventDao(connection);
        //check authToken
        UserAuthDao uaDao = new UserAuthDao(connection);
        UserAuth ua = uaDao.getUserAuth(authToken);
        if(ua == null)
            return null;
        //check eventId
        Event event = eDao.getEvent(eventId);
        if(event == null)
            return null;
        //check if username belongs to event
        if(!(ua.getUsername().equals(event.getUsername())))
            return null;
        return event;

    }

    /** returns all events for all family members of the current user. Current user is determined by the provided auth token
     * @param authToken
     * @return allEvents
     */
    public Event[] findEvents(String authToken) {
        Database db = new Database();
        Connection connection = null;
        connection = db.openConnection(connection);
        EventDao eDao = new EventDao(connection);
        //check authToken
        UserAuthDao uaDao = new UserAuthDao(connection);
        UserAuth ua = uaDao.getUserAuth(authToken);
        if(ua == null)
            return null;
        Event[] events = eDao.getAllEvents(ua.getUsername());

        return events;
    }
}


