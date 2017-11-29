package cs240.fms.ServerFacade;

import cs240.fms.Models.Person;

public class AllPersonsResponse {

    /** contains an array  of Persons */
    private Person[] data;

    /** return the array  of Persons */

   public Person[] getData() {
        return data;
    }
    /** constructor
     * @param data */

   public AllPersonsResponse(Person[] data) {
        this.data = data;
    }

}


