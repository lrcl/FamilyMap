package cs240.fms.Models;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


public class EventTest {
    private Event event;
    @Before
    public void setUp() throws Exception{

        event = new Event("987654", "username", "123456", 40.7607790, -111.8910470, "United States", "Salt Lake City", "wedding", 2017);
    }

    @Test
    public void getEventID() throws Exception {
        assertEquals("987654", event.getEventID());
    }

    @Test
    public void getUsername() throws Exception {
        assertEquals("username", event.getUsername());
    }

    @Test
    public void getPersonID() throws Exception {
        assertEquals("123456", event.getPersonID());
    }

    @Test
    public void getLatitude() throws Exception{
        Double expectedLat = 40.7607790;
        Double eventLat = event.getLatitude();
        assertEquals(expectedLat, eventLat);
    }

    @Test
    public void getLongitude() throws Exception{
        Double expectedLong = -111.8910470;
        Double eventLong = event.getLongitude();
        assertEquals(expectedLong, eventLong);
    }

    @Test
    public void getCountry() throws Exception{
        assertEquals("United States", event.getCountry());
    }

    @Test
    public void getCity() throws Exception{
        assertEquals("Salt Lake City", event.getCity());
    }

    @Test
    public void getEventType() throws Exception{
        assertEquals("wedding", event.getEventType());
    }

    @Test
    public void getYear() throws Exception{
        assertEquals(2017, event.getYear());
    }

}