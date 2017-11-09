package cs240.fms.ServerFacade;

import cs240.fms.Models.Person;
import cs240.fms.Models.Event;
import cs240.fms.Models.User;
import java.util.ArrayList;


public class LoadRequest {
   /** Array of User objects to be loaded into database */
   private User[] users;
   /** Array of Person objects to be loaded into database */
   private Person[] persons;
   /** Array of Event objects to be loaded into database */
   private Event[] events;

    public User[] getUsers() {
        return users;
    }

    public Person[] getPersons() {
        return persons;
    }

    public Event[] getEvents() {
        return events;
    }

    /**Constructor
    * @param users
    * @param persons
    * @param events
    */
   public LoadRequest(User[] users, Person[] persons, Event[] events) {
       this.users = users;
       this.persons = persons;
       this.events = events;
   }

}

