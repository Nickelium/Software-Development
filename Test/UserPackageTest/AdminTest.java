package UserPackageTest;

import Model.User.Admin;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by Laurens on 6/03/2016.
 */
public class AdminTest {

    private Admin admin1;

    @Before
    public void initialization() throws Exception {
        this.admin1 = new Admin("TestFirstName1", "TestMiddleName1", "TestLastName1", "TestUserName1");
    }

    @Test
    public void testFirstName(){
        assertEquals(admin1.getFirstName(), "TestFirstName1");

    }

    @Test
    public void testMiddleName(){
        assertEquals(admin1.getMiddleName(), "TestMiddleName1");
    }

    @Test
    public void testLastName(){
        assertEquals(admin1.getLastName(), "TestLastName1");
    }

    @Test
    public void testUserName(){
        assertEquals(admin1.getUserName(), "TestUserName1");
    }
}
