package UseCaseTests;

import Controller.LoginController;
import CustomExceptions.ReportErrorToUserException;
import Model.User.User;
import UseCaseTests.UseCasesUI.TestUI;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertTrue;

/**
 * Created by Karina on 10.03.2016.
 */
public class LoginTest extends InitializerTest {

    @Test
    public void loginAsAdmin() throws ReportErrorToUserException {
        User currentUser = Logger.adminLogger(userService);
        assertTrue(currentUser instanceof Model.User.Admin);
    }



    @Test
    public void loginAsIssuer() throws ReportErrorToUserException {
        User currentUser = Logger.issuerLogger(userService);
        assertTrue(currentUser instanceof Model.User.Issuer);
    }


    @Test
    public void loginAsDeveloper() throws ReportErrorToUserException {
        User currentUser = Logger.developerLogger(userService);
        assertTrue(currentUser instanceof Model.User.Developer);
    }


    @Test(expected = ReportErrorToUserException.class)
    public void loginChoiceFails() throws ReportErrorToUserException {
        String[] simulatedUserInput = {
                "4",
                "0"
        };
        ArrayList<String> input = new ArrayList<>(Arrays.asList(simulatedUserInput));
        TestUI ui = new TestUI(input);
        LoginController loginController = new LoginController(ui, userService);
        loginController.login();
    }


    @Test(expected = ReportErrorToUserException.class)
    public void userChoiceFails() throws ReportErrorToUserException {
        String[] simulatedUserInput = {
                "1",
                "5"
        };
        ArrayList<String> input = new ArrayList<>(Arrays.asList(simulatedUserInput));
        TestUI ui = new TestUI(input);
        LoginController loginController = new LoginController(ui, userService);
        loginController.login();
    }

}
