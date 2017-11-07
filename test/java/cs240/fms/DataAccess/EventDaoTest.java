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
        connection = db.openConnection(connection);
        this.eventDao = new EventDao(connection);
        Generator g = new Generator();
        eventId1 = g.createId();
        eventId2 = g.createId();
    }

    @Test
    public void addEventGoodId() throws Exception {
      // boolean added = eventDao.addEvent(eventId1);
        //assertTrue(added);
    }
    @Test
    public void addEventBadId() throws Exception {
        Event badEvent = eventDao.getEvent("-3");
        assertNull(badEvent);
    }

    @Test
    public void addAllEvents() throws Exception {
    }

    @Test
    public void removeEvent() throws Exception {
    }

    @Test
    public void removeAllEvents() throws Exception {
    }

    @Test
    public void getEvent() throws Exception {
    }

    @Test
    public void getAllEvents() throws Exception {
    }

}