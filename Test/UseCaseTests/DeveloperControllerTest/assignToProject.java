package UseCaseTests.DeveloperControllerTest;

import Controller.IUI;
import Controller.UserController.DeveloperController;
import CustomExceptions.ReportErrorToUserException;
import UseCaseTests.UseCasesUI.TestUI;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Tom on 10/03/16.
 */
public class AssignToProject extends DeveloperTestInitializer {
    @Test
    public void successfullyAssignedDeveloperToProject() throws Exception {
        String[] simulatedUserInput = {
                "0",
                "1",
                "0",
                "1"
        };

        ArrayList<String> input = new ArrayList<String>(Arrays.asList(simulatedUserInput));
        IUI ui = new TestUI(input);

        DeveloperController developerController = new DeveloperController(ui, userService, projectService, bugReportService, userService.getUser("major"), developerAssignmentService, tagAssignmentService, mailboxService);
        developerController.getUseCase(9).run();
    }

    @Test(expected = ReportErrorToUserException.class)
    public void unsuccessfullyAssignedDeveloperToProject() throws Exception {
        String[] simulatedUserInput = {};

        ArrayList<String> input = new ArrayList<String>(Arrays.asList(simulatedUserInput));
        IUI ui = new TestUI(input);

        DeveloperController developerController = new DeveloperController(ui, userService, projectService, bugReportService, userService.getUser("test1"), developerAssignmentService, tagAssignmentService, mailboxService);
        developerController.getUseCase(9).run();

    }
}
