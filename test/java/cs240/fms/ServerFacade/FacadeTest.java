package cs240.fms.ServerFacade;

import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;

import cs240.fms.DataAccess.Database;
import cs240.fms.DataAccess.EventDao;
import cs240.fms.DataAccess.PersonDao;
import cs240.fms.DataAccess.UserAuthDao;
import cs240.fms.DataAccess.UserDao;

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
    public void login() throws Exception {
    }

    @Test
    public void clear() throws Exception {
    }

    @Test
    public void fill() throws Exception {
    }

    @Test
    public void load() throws Exception {
    }

    @Test
    public void findPerson() throws Exception {
    }

    @Test
    public void findPersons() throws Exception {
    }

    @Test
    public void findEvent() throws Exception {
    }

    @Test
    public void findEvents() throws Exception {
    }

}