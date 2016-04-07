package UserPackageTest;

import Model.User.Issuer;
import Model.User.UserService;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by Laurens on 6/03/2016.
 */
public class IssuerTest {

    private Issuer issuer;
    private UserService userService;

    @Before
    public void initialization() throws Exception {
        this.userService = new UserService();
        this.issuer = this.userService.createIssuer("TestFirstName1", "TestMiddleName1", "TestLastName1", "TestUserName1");
    }

    @Test
    public void testFirstName(){
        assertEquals(issuer.getFirstName(), "TestFirstName1");

    }

    @Test
    public void testMiddleName(){
        assertEquals(issuer.getMiddleName(), "TestMiddleName1");
    }

    @Test
    public void testLastName(){
        assertEquals(issuer.getLastName(), "TestLastName1");
    }

    @Test
    public void testUserName(){
        assertEquals(issuer.getUserName(), "TestUserName1");
    }
}