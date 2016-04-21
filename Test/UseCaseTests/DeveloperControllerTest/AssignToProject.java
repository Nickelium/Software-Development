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
public class AssignToProject extends DeveloperControllerInit {
    @Test
    public void successfullyAssignedDeveloperToProject() throws Exception {
        String[] simulatedUserInput = {
                "0",
                "2",
                "0",
                "1"
        };

        ArrayList<String> input = new ArrayList<String>(Arrays.asList(simulatedUserInput));
        IUI ui = new TestUI(input);

        DeveloperController developerController = new DeveloperController(ui, userService, projectService, bugReportService, userService.getUser("major"), developerAssignmentService, tagAssignmentService, mailboxService);
        developerController.getUseCase(9).run();
    }

    @Test
    public void unsuccessfullyAssignedDeveloperToProject() throws Exception {
        try {
            String[] simulatedUserInput = {};

            ArrayList<String> input = new ArrayList<String>(Arrays.asList(simulatedUserInput));
            IUI ui = new TestUI(input);

            DeveloperController developerController = new DeveloperController(ui, userService, projectService, bugReportService, userService.getUser("test1"), developerAssignmentService, tagAssignmentService, mailboxService);
            developerController.getUseCase(9).run();
        } catch (ReportErrorToUserException e) {
            assert e.getMessage().equals("You are not assigned as lead developer in any project. You are not allowed to assign a new developer to any project.");
        }
    }

}
