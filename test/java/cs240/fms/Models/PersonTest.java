package cs240.fms.Models;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


public class PersonTest {
    Person person;
    @Before
    public void setUp() throws Exception {
        person = new Person("123456", "descendant", "firstname", "lastname", "f", "123457", "123458", "123459");
    }

    @Test
    public void getPersonID() throws Exception {
        assertEquals("123456", person.getPersonID());
    }

    @Test
    public void getDescendant() throws Exception {
        assertEquals("descendant", person.getDescendant());
    }

    @Test
    public void getFirstName() throws Exception {
        assertEquals("firstname", person.getFirstName());
    }

    @Test
    public void getLastName() throws Exception {
        assertEquals("lastname", person.getLastName());
    }

    @Test
    public void getGender() throws Exception {
        assertEquals("f", person.getGender());
    }

    @Test
    public void getFatherID() throws Exception {
        assertEquals("123457", person.getFatherID());
    }

    @Test
    public void getMotherID() throws Exception {
        assertEquals("123458", person.getMotherID());
    }

    @Test
    public void getSpouseID() throws Exception {
        assertEquals("123459", person.getSpouseID());
    }

}