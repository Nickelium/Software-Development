package UseCaseTests;

import Controller.LoginController;
import CustomExceptions.ModelException;
import Model.User.User;
import UseCaseTests.UseCasesUI.TestUI;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Created by Karina on 10.03.2016.
 */
public class LoginTest extends InitializerTest {

    @Test
    public void loginAsAdmin() throws ModelException{
        String[] simulatedUserInput = {
                "1",
                "0"
        };
        ArrayList<String> input = new ArrayList<>(Arrays.asList(simulatedUserInput));
        TestUI ui = new TestUI(input);
        LoginController loginController = new LoginController(ui, userService);
        loginController.run();
        User currentUser = loginController.getCurrentUser();
        assertTrue(currentUser instanceof Model.User.Admin);
    }


    @Test
    public void loginAsIssuer() throws ModelException{
        String[] simulatedUserInput = {
                "2",
                "0"
        };
        ArrayList<String> input = new ArrayList<>(Arrays.asList(simulatedUserInput));
        TestUI ui = new TestUI(input);
        LoginController loginController = new LoginController(ui, userService);
        loginController.run();
        User currentUser = loginController.getCurrentUser();
        assertTrue(currentUser instanceof Model.User.Issuer);
    }

    @Test
    public void loginAsDeveloper() throws ModelException{
        String[] simulatedUserInput = {
                "3",
                "0"
        };
        ArrayList<String> input = new ArrayList<>(Arrays.asList(simulatedUserInput));
        TestUI ui = new TestUI(input);
        LoginController loginController = new LoginController(ui, userService);
        loginController.run();
        User currentUser = loginController.getCurrentUser();
        assertTrue(currentUser instanceof Model.User.Developer);
    }

    @Test(expected = ModelException.class)
    public void loginChoiceFails() throws ModelException{
        String[] simulatedUserInput = {
                "4",
                "0"
        };
        ArrayList<String> input = new ArrayList<>(Arrays.asList(simulatedUserInput));
        TestUI ui = new TestUI(input);
        LoginController loginController = new LoginController(ui, userService);
        loginController.run();
    }


    @Test(expected = ModelException.class)
    public void userChoiceFails() throws ModelException{
        String[] simulatedUserInput = {
                "1",
                "5"
        };
        ArrayList<String> input = new ArrayList<>(Arrays.asList(simulatedUserInput));
        TestUI ui = new TestUI(input);
        LoginController loginController = new LoginController(ui, userService);
        loginController.run();
    }

}
