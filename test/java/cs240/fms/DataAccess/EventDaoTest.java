package cs240.fms.DataAccess;

import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;

import cs240.fms.Models.Event;
import cs240.fms.ServerFacade.DataHandling.Generator;

import static org.junit.Assert.*;


public class EventDaoTest {

    EventDao eventDao;
    String eventId1;
    String eventId2;
    @Before
    public void setUp() throws Exception {
        Connection connection = null;
        Database db = new Database();
        db.createTables(connection);
        connection = db.openConnection(connection);
        this.eventDao = new EventDao(connection);
        Generator g = new Generator();
        eventId1 = g.createId();
        eventId2 = g.createId();

    }

    @Test
    public void addGoodEvent() throws Exception {

        Event event = new Event(eventId1,"username1","personId1",33.333, 44.444,"USA","ABC","birth",1990);
        boolean added = eventDao.addEvent(event);
        assertTrue(added);
    }

    @Test
    public void getEventBadId() throws Exception {
        Event badEvent = eventDao.getEvent("-3");
        assertNull(badEvent);
    }
    @Test
    public void getEventGoodId() throws Exception {
        Event goodEvent = eventDao.getEvent(eventId1);
        assertNull(goodEvent);
    }
    @Test
    public void addAllEvents() throws Exception {
        Event event = new Event(eventId1,"username1","personId1",33.333, 44.444,"USA","ABC","birth",1990);
        Event event2 = new Event(eventId2, "username2", "personId2", 44.444, 55.555, "USA", "DEF", "marriage", 2010);
        Event [] events = {event, event2};
        assertTrue(eventDao.addAllEvents(events));

    }

    @Test
    public void removeEvent() throws Exception {
        eventDao.removeAllEvents("username1");
        eventDao.removeAllEvents("username2");
        addGoodEvent();
        assertTrue(eventDao.removeEvent(eventId1));
    }
    @Test
    public void removeNonEvent() throws Exception {
        eventDao.removeAllEvents("username1");
        eventDao.removeAllEvents("username2");
        assertTrue(eventDao.removeEvent(eventId1));

    }
    @Test
    public void removeAllEvents() throws Exception {
        addAllEvents();
        assertTrue(eventDao.removeAllEvents("username1"));

    }
    @Test
    public void getAllEvents() throws Exception {
        addAllEvents();
        assertNotNull(eventDao.getAllEvents("username1"));
        assertNotNull(eventDao.getAllEvents("username2"));

    }
    @Test
    public void getNonExistentEvents() throws Exception {
        eventDao.removeAllEvents("username1");
        eventDao.removeAllEvents("username2");
        Event[] events = eventDao.getAllEvents("username2");
        System.out.println(events.length);
    }

}