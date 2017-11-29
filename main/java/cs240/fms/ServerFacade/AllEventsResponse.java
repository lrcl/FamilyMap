package cs240.fms.ServerFacade;

import cs240.fms.Models.Event;

public class AllEventsResponse {
    /**
     * an array list of Event objects
     */
    private Event[] data;

    /**
     * get allEvents
     *@return allEvents array list
     */
    public Event[] getData() {
        return this.data;
    }
    /**
     * constructor
     * @param data
     */
    public AllEventsResponse(Event[] data) {
        this.data = data;

    }

}
