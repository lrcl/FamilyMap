package cs240.fms.ServerFacade;

import cs240.fms.Models.Person;

public class PersonResponse {
   /** Person object */
   private Person person;

   /** Constructor
    *
    * @param person
    */
   public PersonResponse(Person person) {
       this.person = person;
   }
}



