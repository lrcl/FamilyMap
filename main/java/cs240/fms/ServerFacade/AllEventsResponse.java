package cs240.fms.ServerFacade;

import java.util.ArrayList;
import cs240.fms.Models.Event;

public class AllEventsResponse {
    /**
     * an array list of Event objects
     */
    private ArrayList<Event> allEvents;

    /**
     * get allEvents
     *@return allEvents array list
     */
    public ArrayList<Event> getAllEvents() {
        return this.allEvents;
    }
    /**
     * constructor
     * @param allEvents
     */
    public AllEventsResponse(ArrayList<Event> allEvents) {
        this.allEvents = allEvents;

    }

}
