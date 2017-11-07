package cs240.fms.DataAccess;

import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;

import static org.junit.Assert.*;

public class DatabaseTest {
    Database db;
    Connection connection;
    @Before
    public void setUp() throws Exception {
        db = new Database();
        connection = null;
    }
    @Test
    public void openConnection() throws Exception {
        connection = db.openConnection(connection);
        assertNotNull(connection);
    }

    @Test
    public void createTables() throws Exception {
        assertTrue(db.createTables(connection));
    }
    @Test
    public void dropAllTables() throws Exception {
        assertTrue(db.dropAllTables(connection));
    }

}