package cs240.fms.Models;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserTest {
    User user;
    @Before
    public void setUp() throws Exception{
        user = new User("username", "password", "email", "firstname", "lastname", "f", "123456");
    }
    @Test
    public void getUsername() throws Exception {
        assertEquals("username", user.getUsername());

    }

    @Test
    public void getPassword() throws Exception {
        assertEquals("password", user.getPassword());
    }

    @Test
    public void getEmail() throws Exception {
        assertEquals("email", user.getEmail());
    }

    @Test
    public void getFirstName() throws Exception {
        assertEquals("firstname", user.getFirstName());
    }

    @Test
    public void getLastName() throws Exception {
        assertEquals("lastname", user.getLastName());
    }

    @Test
    public void getGender() throws Exception {
        assertEquals("f", user.getGender());
    }

    @Test
    public void getPersonID() throws Exception {
        assertEquals("123456", user.getPersonId());
    }

}