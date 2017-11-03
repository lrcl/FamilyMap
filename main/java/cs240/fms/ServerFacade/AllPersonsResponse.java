package cs240.fms.ServerFacade;

import cs240.fms.Models.Person;
import java.util.ArrayList;

public class AllPersonsResponse {
    /** contains an array list of Persons */
    private ArrayList<Person> allPersons;

    /** return the array list of Persons
     *
     */
    public ArrayList<Person> getAllPersons() {
        return null;
    }
    /** constructor
     * @param allPersons
     */
    public AllPersonsResponse(ArrayList<Person> allPersons) {
        this.allPersons = allPersons;
    }

}
