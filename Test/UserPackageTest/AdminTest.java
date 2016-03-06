package UserPackageTest;

import CustomExceptions.ModelException;
import Model.User.Admin;
import org.junit.*;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by Laurens on 6/03/2016.
 */
public class AdminTest {

    private Admin admin1;
    private Admin admin2;

    @Before
    public void initialization() throws Exception {
        this.admin1 = new Admin("TestFirstName1", "TestMiddleName1", "TestLastName1", "TestUserName1");
        this.admin2 = new Admin("TestFirstName2", "TestLastName2", "TestUserName2");
    }

    @Test
    public void testFirstName(){
        assertEquals(admin1.getFirstName(), "TestFirstName1");
        assertEquals(admin2.getFirstName(), "TestFirstName2");
    }

    @Test
    public void testMiddleName(){
        assertEquals(admin1.getMiddleName(), "TestMiddleName1");
    }

    @Test
    public void testLastName(){
        assertEquals(admin1.getLastName(), "TestLastName1");
        assertEquals(admin2.getLastName(), "TestLastName2");
    }

    @Test
    public void testUserName(){
        assertEquals(admin1.getUserName(), "TestUserName1");
        assertEquals(admin2.getUserName(), "TestUserName2");
    }
}
