package UserPackageTest;

import Model.User.Admin;
import Model.User.Developer;
import Model.User.Issuer;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Laurens on 6/03/2016.
 */
public class UserTest {

    private Admin admin;
    private Developer developer;
    private Issuer issuer;

    @Before
    public void initialization() throws Exception {
        this.admin = new Admin("TestFirstName1", "TestMiddleName1", "TestLastName1", "TestUserName1");
        this.developer = new Developer("TestFirstName2", "TestMiddleName2", "TestLastName2", "TestUserName2");
        this.issuer = new Issuer("TestFirstName3", "TestMiddleName3", "TestLastName3", "TestUserName3");
    }

    @Test
    public void testGetFirstName() throws Exception {
        assertEquals(admin.getFirstName(), "TestFirstName1");
        assertEquals(developer.getFirstName(), "TestFirstName2");
        assertEquals(issuer.getFirstName(), "TestFirstName3");
    }

    @Test
    public void testGetMiddleName() throws Exception {
        assertEquals(admin.getMiddleName(), "TestMiddleName1");
        assertEquals(developer.getMiddleName(), "TestMiddleName2");
        assertEquals(issuer.getMiddleName(), "TestMiddleName3");
    }

    @Test
    public void testGetLastName() throws Exception {
        assertEquals(admin.getLastName(), "TestLastName1");
        assertEquals(developer.getLastName(), "TestLastName2");
        assertEquals(issuer.getLastName(), "TestLastName3");
    }

    @Test
    public void testGetUserName() throws Exception {
        assertEquals(admin.getUserName(), "TestUserName1");
        assertEquals(developer.getUserName(), "TestUserName2");
        assertEquals(issuer.getUserName(), "TestUserName3");
    }

    @Test
    public void testIsValidUserName() throws Exception {
        String validUserName = "ImValid";
        assertTrue(admin.isValidUserName(validUserName));
        assertFalse(admin.isValidUserName(""));
        assertFalse(admin.isValidUserName(null));
    }
}