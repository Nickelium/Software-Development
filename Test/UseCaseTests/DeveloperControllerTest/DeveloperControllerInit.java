package UseCaseTests.DeveloperControllerTest;

import Controller.LoginController;
import CustomExceptions.ModelException;
import UseCaseTests.InitializerTest;
import UseCaseTests.UseCasesUI.TestUI;
import org.junit.Before;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Tom on 10/03/16.
 */
public class DeveloperControllerInit extends InitializerTest {
    protected LoginController loginController;

    @Before
    public void subInit() throws ModelException {
        String[] simulatedUserInput = {
                "3",
                "0"
        };
        ArrayList<String> input = new ArrayList<>(Arrays.asList(simulatedUserInput));
        TestUI ui = new TestUI(input);
        loginController = new LoginController(ui, userService);
        loginController.run();
    }

}
