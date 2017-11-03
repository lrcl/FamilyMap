package cs240.fms.ServerFacade;

import cs240.fms.Models.Person;
import cs240.fms.Models.Event;
import cs240.fms.Models.User;
import java.util.ArrayList;


public class LoadRequest {
   /** Array list of User objects to be loaded into database */
   private ArrayList<User> users;
   /** Array list of Person objects to be loaded into database */
   private ArrayList<Person> persons;
   /** Array list of Event objects to be loaded into database */
   private ArrayList<Event> events;

   /**Constructor
    * @param users
    * @param persons
    * @param events
    */
   public LoadRequest(ArrayList<User> users, ArrayList<Person> persons, ArrayList<Event> events) {
       this.users = users;
       this.persons = persons;
       this.events = events;
   }

}

