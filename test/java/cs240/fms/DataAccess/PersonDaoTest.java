package cs240.fms.DataAccess;

import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;

import cs240.fms.Models.Person;
import cs240.fms.ServerFacade.DataHandling.Generator;

import static org.junit.Assert.*;

public class PersonDaoTest {
    PersonDao personDao;
    String personId1;
    String personId2;

    @Before
    public void setUp() throws Exception {
        Connection connection = null;
        Database db = new Database();
        db.createTables(connection);
        connection = db.openConnection(connection);
        this.personDao = new PersonDao(connection);
        Generator g = new Generator();
        personId1 = g.createId();
        personId2 = g.createId();
    }
    @Test
    public void addPerson() throws Exception {
        Person p1 = new Person(personId1,"user1","John","Johnson","m","fatherId","motherId","spouseId");
        assertTrue(personDao.addPerson(p1));
    }
    @Test
    public void addDuplicatePerson() throws Exception {
        Person p1 = new Person(personId1,"user1","John","Johnson","m","fatherId","motherId","spouseId");
        assertTrue(personDao.addPerson(p1));
        Person p2 = new Person(personId1,"user1","John","Johnson","m","fatherId","motherId","spouseId");
        assertFalse(personDao.addPerson(p2));
    }

    @Test
    public void addAllPersons() throws Exception {
        personDao.removeAllPersons("user1");
        personDao.removeAllPersons("user2");
        Person p1 = new Person(personId1,"user1","John","Johnson","m","fatherId","motherId","spouseId");
        Person p2 = new Person(personId2, "user2", "Jayne", "James", "f", "fatherId", "motherId", "spouseId");
        Person [] persons = {p1, p2};
        assertTrue(personDao.addAllPersons(persons));
    }

    @Test
    public void updateMotherId() throws Exception {
        personDao.removeAllPersons("user1");
        personDao.removeAllPersons("user2");
        Person p2 = new Person(personId2, "user2", "Jayne", "James", "f", "fatherId", "motherId", "spouseId");
        personDao.addPerson(p2);
        assertTrue(personDao.updateMotherId("updatedMotherId",p2));
    }

    @Test
    public void updateFatherId() throws Exception {
        personDao.removeAllPersons("user1");
        personDao.removeAllPersons("user2");
        Person p2 = new Person(personId2, "user2", "Jayne", "James", "f", "fatherId", "motherId", "spouseId");
        personDao.addPerson(p2);
        assertTrue(personDao.updateFatherId("updatedFatherId",p2));
    }

    @Test
    public void removePerson() throws Exception {
        personDao.removeAllPersons("user1");
        personDao.removeAllPersons("user2");
        Person p1 = new Person(personId1,"user1","John","Johnson","m","fatherId","motherId","spouseId");
        personDao.addPerson(p1);
        assertTrue(personDao.removePerson(p1));
        assertNull(personDao.getPerson(personId1));
    }

    @Test
    public void removeAllPersons() throws Exception {
        addAllPersons();
        assertTrue(personDao.removeAllPersons("user1"));
        assertTrue(personDao.removeAllPersons("user2"));


    }
    @Test
    public void removeAllPersonsFakeUser() throws Exception {
        addAllPersons();
        assertTrue(personDao.removeAllPersons("fakeuser"));
    }

    @Test
    public void getPerson() throws Exception {
        personDao.removeAllPersons("user1");
        personDao.removeAllPersons("user2");
        Person p1 = new Person(personId1,"user1","John","Johnson","m","fatherId","motherId","spouseId");
        personDao.addPerson(p1);
        assertNotNull(personDao.getPerson(personId1));


    }
    @Test
    public void getNonPerson() throws Exception {
        personDao.removeAllPersons("user1");
        personDao.removeAllPersons("user2");
        assertNull(personDao.getPerson(personId1));

    }
    @Test
    public void getAllPersons() throws Exception {
        personDao.removeAllPersons("user1");
        personDao.removeAllPersons("user2");
        addAllPersons();
        assertNotNull(personDao.getAllPersons("user1"));
        assertNotNull(personDao.getAllPersons("user2"));
    }
    @Test
    public void getNonPersons() throws Exception {
        personDao.removeAllPersons("user1");
        personDao.removeAllPersons("user2");
        addAllPersons();
        Person[] persons = personDao.getAllPersons("fakeuser");
        assertSame(0,persons.length);
    }

}