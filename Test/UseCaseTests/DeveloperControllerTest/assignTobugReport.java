package UseCaseTests.DeveloperControllerTest;

import Controller.IUI;
import Controller.UserController.DeveloperController;
import UseCaseTests.UseCasesUI.TestUI;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Tom on 10/03/16.
 */
public class assignTobugReport extends DeveloperControllerInit {
    @Test
    public void successfullyAssignedDeveloperToBugReport() throws Exception {
        String[] simulatedUserInput = {
                "0",
                "1",
                "0",
                "1"
        };

        ArrayList<String> input = new ArrayList<String>(Arrays.asList(simulatedUserInput));
        IUI ui = new TestUI(input);

        DeveloperController developerController = new DeveloperController(ui, userService, projectService, bugReportService, loginController.getCurrentUser(), developerAssignmentService, tagAssignmentService);
        developerController.callUseCase(5);
    }
}
