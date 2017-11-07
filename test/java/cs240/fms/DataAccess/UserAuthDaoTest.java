package cs240.fms.DataAccess;

import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;

import cs240.fms.Models.UserAuth;
import cs240.fms.ServerFacade.DataHandling.Generator;

import static org.junit.Assert.*;


public class UserAuthDaoTest {
    UserAuthDao uaDao;
    String authToken1;
    String authToken2;

    @Before
    public void setUp() throws Exception {
        Connection connection = null;
        Database db = new Database();
        db.createTables(connection);
        connection = db.openConnection(connection);
        this.uaDao = new UserAuthDao(connection);
        Generator g = new Generator();
        authToken1 = g.createId();
        authToken2 = g.createId();
    }
    @Test
    public void addUserAuth() throws Exception {
        UserAuth userAuth = new UserAuth(authToken1, "user1");
        assertTrue(uaDao.addUserAuth(userAuth));
    }
    @Test
    public void addDuplicateUserAuth() throws Exception {
        UserAuth userAuth = new UserAuth(authToken1, "user1");
        assertTrue(uaDao.addUserAuth(userAuth));
        UserAuth userAuth2 = new UserAuth(authToken1, "user1");
        assertFalse(uaDao.addUserAuth(userAuth2));
    }

    @Test
    public void removeUserAuth() throws Exception {
        UserAuth userAuth = new UserAuth(authToken1, "user1");
        uaDao.addUserAuth(userAuth);
        assertTrue(uaDao.removeUserAuth(userAuth));
    }
    @Test
    public void removeNonUA() throws Exception {
        UserAuth userAuth = new UserAuth(authToken1, "user1");
        assertTrue(uaDao.removeUserAuth(userAuth));
    }

    @Test
    public void getUserAuth() throws Exception {
        UserAuth userAuth1 = new UserAuth(authToken1, "user1");
        uaDao.addUserAuth(userAuth1);
        assertNotNull(uaDao.getUserAuth(authToken1));
    }
    @Test
    public void getNonUserAuth() throws Exception {
        UserAuth userAuth1 = new UserAuth(authToken1, "user1");
        uaDao.addUserAuth(userAuth1);
        assertNull(uaDao.getUserAuth(authToken2));
    }


}