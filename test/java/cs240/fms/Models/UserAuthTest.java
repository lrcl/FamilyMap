package cs240.fms.Models;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserAuthTest {
    UserAuth auth;
    @Before
    public void setUp() {
        auth = new UserAuth("", "username");

    }
    @Test
    public void getAuthToken() throws Exception {
        assertEquals("", auth.getAuthToken());

    }

    @Test
    public void getUsername() throws Exception {
        assertEquals("username", auth.getUsername());
    }

}