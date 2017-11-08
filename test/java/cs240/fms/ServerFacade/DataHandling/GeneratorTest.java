package cs240.fms.ServerFacade.DataHandling;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import cs240.fms.Models.Event;
import cs240.fms.Models.Person;
import cs240.fms.Models.User;

import static org.junit.Assert.*;


public class GeneratorTest {
    Generator g;

    @Before
    public void setUp() throws Exception {
        g = new Generator();
    }

    @Test
    public void loadData() throws Exception {
        assertTrue(g.loadData());
    }

    @Test
    public void createPersonFromUser() throws Exception {
        User user = new User("user1","123","email1","Jonny","Apple","m","personId");
        Person person = g.createPersonFromUser(user);
        assertEquals("personId",person.getPersonID());
    }

    @Test
    public void createPersonFromFile() throws Exception {
        String username = "generatedUser";
        String gender = "f";
        Person person = g.createPersonFromFile(username, gender);
        assertEquals("generatedUser",person.getDescendant());
    }

    @Test
    public void createId() throws Exception {
        String id1 = g.createId();
        String id2 = g.createId();
        assertNotEquals(id1,id2);
    }
    @Test
    //this method was very useful; I went through city,country,
    //lat and longitude and printed them out here to make sure the JSON was being read and accessed correctly
    public void generateLocationInfo() throws Exception {
        Double lat = g.generateLocationInfo();
        System.out.println(lat);
    }
    @Test
    public void createBirth() throws Exception {
        Event event = g.createBirth(0,"user1","personId");
        Event event2 = g.createMarriage(0,"user1","personId");
        assertNotEquals(event.getYear(),event2.getYear());
    }

    @Test
    public void createMarriage() throws Exception {
        Event event = g.createDeathDate(0,"user1","personId");
        Event event2 = g.createMarriage(0,"user1","personId");
        assertNotEquals(event.getYear(),event2.getYear());
    }

    @Test
    public void createDeathDate() throws Exception {
        Event event = g.createDeathDate(0,"user1","personId");
        Event event2 = g.createBirth(0,"user1","personId");
        assertNotEquals(event.getYear(),event2.getYear());
    }
}