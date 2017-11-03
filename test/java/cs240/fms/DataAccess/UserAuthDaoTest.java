package cs240.fms.DataAccess;

import org.junit.Before;
import org.junit.Test;

import cs240.fms.Models.UserAuth;

import static org.junit.Assert.*;

public class UserAuthDaoTest {
    UserAuthDao userAuthDao;
    int attributeCount = 0;
    @Before
    public void setUp() throws Exception {
        userAuthDao = new UserAuthDao();
        attributeCount = 2;
    }

    @Test
    public void addUserAuth() throws Exception {

    }

    @Test
    public void removeUserAuth() throws Exception {
    }

    @Test
    public void getUserAuth() throws Exception {
    }

}