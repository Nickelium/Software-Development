package UserPackageTest;

import Model.User.*;
import Model.Wrapper.ListWrapper;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Laurens on 6/03/2016.
 */
public class UserServiceTest {

    private ListWrapper<User> userList = new ListWrapper<>();
    private Admin admin;
    private Developer developer;
    private Issuer issuer;
    private UserService service;

    @Before
    public void initialization() throws Exception {

        // Create ListWrapper for reference
        this.service = new UserService();
        this.admin = this.service.createAdmin("adminFirstName", "adminMiddleName", "adminLastName", "adminUserName");
        this.developer = this.service.createDeveloper("developerFirstName", "developerMiddleName", "developerLastName", "developerUserName");
        this.issuer = this.service.createIssuer("issuerFirstName", "issuerMiddleName", "issuerLastName", "issuerUserName");
        userList.insert(admin);
        userList.insert(developer);
        userList.insert(issuer);
    }

    @Test
    public void testGetUserList() throws Exception {
        List<User> allUsers = service.getUserList();
        for(User u : allUsers){
            if(u instanceof Admin)
                assertEquals(u.getUserName(),admin.getUserName());
            else if(u instanceof Developer)
                assertEquals(u.getUserName(), developer.getUserName());
            else if(u instanceof Issuer)
                assertEquals(u.getUserName(), issuer.getUserName());
        }
    }

    @Test
    public void testGetAdministrators() throws Exception {
        List<User> allAdmins = service.getAdministrators();
        for(User a : allAdmins){
            assertEquals(a.getUserName(), admin.getUserName());
        }
    }

    @Test
    public void testGetIssuers() throws Exception {
        List<User> allIssuers = service.getIssuers();
        for(User i : allIssuers){
            if(i instanceof Developer)
                assertEquals(i.getUserName(), developer.getUserName());
            else if(i instanceof Issuer)
                assertEquals(i.getUserName(), issuer.getUserName());
        }
    }

    @Test
    public void testGetDevelopers() throws Exception {
        List<User> allDevelopers = service.getDevelopers();
        for(User d : allDevelopers){
            assertEquals(d.getUserName(), developer.getUserName());
        }
    }

    @Test
    public void testCreateAdmin() throws Exception {
        service.createAdmin("testFirst", "testMiddle", "testLast", "testAdminName");
        List<User> containsNewAdmin = service.getAdministrators();
        for(User a : containsNewAdmin){
            if(a instanceof Admin && a.getUserName() == "testAdminName")
                assertEquals(a.getUserName(), "testAdminName");
        }
    }

    @Test
    public void testCreateIssuer() throws Exception {
        service.createIssuer("testFirst", "testMiddle", "testLast", "testIssuerName");
        List<User> containsNewIssuer = service.getIssuers();
        for(User i : containsNewIssuer){
            if(i instanceof Issuer && !(i instanceof Developer)&& i.getUserName() == "testIssuerName")
                assertEquals(i.getUserName(), "testIssuerName");
        }
    }

    @Test
    public void testCreateDeveloper() throws Exception {
        service.createDeveloper("testFirst", "testMiddle", "testLast", "testDeveloperName");
        List<User> containsNewDeveloper = service.getDevelopers();
        for(User d : containsNewDeveloper){
            if(d instanceof Developer && !(d instanceof Issuer))
                assertEquals(d.getUserName(), "testDeveloperName");
        }
    }

    @Test
    public void testIsValidUserName_FAL() throws Exception {
        assertFalse(service.isValidUserName("adminUserName"));
    }

    @Test
    public void testIsValidUserName_TRU() throws Exception {
        assertTrue(service.isValidUserName("ImAUniqueName"));
    }

    @Test
    public void testGetUser() throws Exception {
        User userInList = service.getUser("adminUserName");
        assertEquals(userInList.getFirstName(), admin.getFirstName());
    }
}