package UseCaseTests.IssuerControllerTest;

import Controller.LoginController;
import CustomExceptions.ReportErrorToUserException;
import UseCaseTests.InitializerTest;
import UseCaseTests.UseCasesUI.TestUI;
import org.junit.Before;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Karina on 10.03.2016.
 */
public class IssuerControllerInit extends InitializerTest {

    protected LoginController loginController;

    @Before
    public void subInit() throws ReportErrorToUserException {
        String[] simulatedUserInput = {
                "2",
                "0"
        };
        ArrayList<String> input = new ArrayList<>(Arrays.asList(simulatedUserInput));
        TestUI ui = new TestUI(input);
        loginController = new LoginController(ui,userService);
        loginController.login();
    }
}
