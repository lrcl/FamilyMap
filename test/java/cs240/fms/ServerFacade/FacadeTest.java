package cs240.fms.ServerFacade;

import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;

import cs240.fms.DataAccess.Database;
import cs240.fms.DataAccess.EventDao;
import cs240.fms.DataAccess.PersonDao;
import cs240.fms.DataAccess.UserAuthDao;
import cs240.fms.DataAccess.UserDao;
import cs240.fms.Models.Event;
import cs240.fms.Models.Person;
import cs240.fms.Models.User;

import static org.junit.Assert.*;

public class FacadeTest {
    UserDao userDao;
    UserAuthDao userAuthDao;
    PersonDao personDao;
    EventDao eventDao;
    Facade facade;

    @Before
    public void setUp() throws Exception {
        facade = new Facade();
        Connection connection = null;
        Database db = new Database();
        db.dropAllTables(connection);
        db.createTables(connection);
    }

    @Test
    public void register() throws Exception {
        RegisterRequest registerRequest = new RegisterRequest("user1","123","email1", "Jack", "Black","m");
        assertNotNull(facade.register(registerRequest));
    }
    @Test
    public void registerDuplicateUser() throws Exception {
        RegisterRequest registerRequest = new RegisterRequest("user1","123","email1", "Jack", "Black","m");
        assertNotNull(facade.register(registerRequest));
        RegisterRequest registerRequest1 = new RegisterRequest("user1","123","email1", "Jack", "Black","m");
        assertNull(facade.register(registerRequest1));
    }

    @Test
    public void login() throws Exception {
        register();
        LoginRequest loginRequest = new LoginRequest("user1","123");
        assertNotNull(facade.login(loginRequest));
    }

    @Test
    public void clear() throws Exception {
        facade.clear();
    }

    @Test
    public void fillNonExistentUser() throws Exception {
        FillRequest fillRequest = new FillRequest("user1",2);
        assertFalse(facade.fill(fillRequest));

    }

    @Test
    public void load() throws Exception {
        User user = new User("u","p","e","f","l","m","Id");
        Person person = new Person("Id","u","f","l","f","fId","mId","sId");
        Event event = new Event("Id","u","Id",7.77,8.88,"USA","Ranchville","birth",1990);
        User[] users = {user};
        Person[] persons = {person};
        Event[] events = {event};
        LoadRequest loadRequest = new LoadRequest(users,persons,events);
        assertTrue(facade.load(loadRequest));
    }
    @Test
    public void findPersonFake() throws Exception {
        assertNull(facade.findPerson("fakeAuth","fakeId"));
    }

    @Test
    public void findPersonsBadAuthtoken() throws Exception {
        assertNull(facade.findPersons("fakeAuth"));
    }

    @Test
    public void findEventFake() throws Exception {
        assertNull(facade.findEvent("fakeAuth","fakeId"));
    }

    @Test
    public void findEventsBadAuthtoken() throws Exception {
        assertNull(facade.findEvents("fakeAuth"));
    }

}