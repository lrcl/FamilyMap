package cs240.fms.DataAccess;

import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;

import cs240.fms.Models.User;

import static org.junit.Assert.*;


public class UserDaoTest {
    UserDao userDao;
    User user;

    @Before
    public void setUp() throws Exception {
        Connection connection = null;
        Database db = new Database();
        db.dropAllTables(connection);
        db.createTables(connection);
        connection = db.openConnection(connection);
        this.userDao = new UserDao(connection);
    }

    @Test
    public void addUser() throws Exception {
        user = new User("user1","123","email","First","Last","m","personId");
        assertTrue(userDao.addUser(user));
    }
    @Test
    public void addDuplicateUser() throws Exception {
        User user = new User("user1","123","email","First","Last","m","personId");
        assertTrue(userDao.addUser(user));
        User user2 = new User("user1","123","email","First","Last","m","personId");
        assertFalse(userDao.addUser(user2));
    }

    @Test
    public void getPersonIdByLogin() throws Exception {
        User user = new User("user1","123","email","James","Brown","m","personId1");
        User user2 = new User("user2","456","email2","Jenny","Green","f","personId2");
        userDao.addUser(user);
        userDao.addUser(user2);
        assertEquals("personId1",userDao.getPersonIdByLogin("user1","123"));
    }
    @Test
    public void getPersonIdByNonExistingLogin() throws Exception {
        User user = new User("user1","123","email","James","Brown","m","personId1");
        User user2 = new User("user2","456","email2","Jenny","Green","f","personId2");
        userDao.addUser(user);
        userDao.addUser(user2);
        assertEquals("",userDao.getPersonIdByLogin("fakeuser","890"));
    }
    @Test
    public void addAllUsers() throws Exception {
        User user = new User("user1","123","email","James","Brown","m","personId1");
        User user2 = new User("user2","456","email2","Jenny","Green","f","personId2");
        User[] users = {user, user2};
        assertTrue(userDao.addAllUsers(users));
    }

    @Test
    public void removeUser() throws Exception {
        User user = new User("user1","123","email","James","Brown","m","personId1");
        User user2 = new User("user2","456","email2","Jenny","Green","f","personId2");
        userDao.addUser(user);
        userDao.addUser(user2);
        assertTrue(userDao.removeUser(user));
    }
    @Test
    public void removeFakeUser() throws  Exception {
        User user = new User("user1","123","email","James","Brown","m","personId1");
        User user2 = new User("user2","456","email2","Jenny","Green","f","personId2");
        userDao.addUser(user);
        assertTrue(userDao.removeUser(user2));
    }

    @Test
    public void getUser() throws Exception {
        User user = new User("user1","123","email","James","Brown","m","personId1");
        User user2 = new User("user2","456","email2","Jenny","Green","f","personId2");
        userDao.addUser(user);
        userDao.addUser(user2);
        assertEquals(user.getUsername(), (userDao.getUser("user1")).getUsername());
    }
    @Test
    public void getFakeUser() throws Exception {
        User user = new User("user1","123","email","James","Brown","m","personId1");
        User user2 = new User("user2","456","email2","Jenny","Green","f","personId2");
        userDao.addUser(user);
        assertNull(userDao.getUser("fakeuser"));
    }
}