package cs240.fms.ServerFacade;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import cs240.fms.Models.Event;

import static org.junit.Assert.*;


public class AllEventsResponseTest {
    AllEventsResponse eventsResponse;
    @Before
    public void setUp() {
        ArrayList<Event> eventList = new ArrayList<Event>();
        eventsResponse = new AllEventsResponse(eventList);
    }
    @Test
    public void getEventsAdded() throws Exception {
        assertNotNull(this.eventsResponse.getAllEvents());

    }

}