package UserPackageTest;

import Model.User.Developer;
import Model.User.UserService;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by Laurens on 6/03/2016.
 */
public class DeveloperTest {

    private Developer developer;
    private UserService userService;

    @Before
    public void initialization() throws Exception {
        this.userService = new UserService();
        this.developer = this.userService.createDeveloper("TestFirstName1", "TestMiddleName1", "TestLastName1", "TestUserName1");
    }

    @Test
    public void testFirstName(){
        assertEquals(developer.getFirstName(), "TestFirstName1");
    }

    @Test
    public void testMiddleName(){
        assertEquals(developer.getMiddleName(), "TestMiddleName1");
    }

    @Test
    public void testLastName(){
        assertEquals(developer.getLastName(), "TestLastName1");
    }

    @Test
    public void testUserName(){
        assertEquals(developer.getUserName(), "TestUserName1");
    }
}