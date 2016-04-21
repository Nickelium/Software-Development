package UseCaseTests.DeveloperControllerTest;

import Controller.IUI;
import Controller.UserController.DeveloperController;
import CustomExceptions.ReportErrorToUserException;
import UseCaseTests.UseCasesUI.TestUI;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Karina on 14.04.2016.
 */
public class ProposeTest extends DeveloperControllerInit {

    @Test
    public void successfullyProposedTest() throws Exception {
        String[] simulatedUserInput = {
                "0",
                "Crash",
                "0",
                "Test test",
                "."
        };
        ArrayList<String> input = new ArrayList<String>(Arrays.asList(simulatedUserInput));
        IUI ui = new TestUI(input);
        DeveloperController developerController = new DeveloperController(ui, userService, projectService, bugReportService, userService.getUser("maria"), developerAssignmentService, tagAssignmentService, mailboxService);
        developerController.getUseCase(13).run();
    }

    @Test
    public void unsuccessfullyProposedTest_UserNotAllowed() throws Exception {
        try {
            String[] simulatedUserInput = {
                    "0",
                    "Crash",
                    "0",
                    "Test test",
                    "."
            };

            ArrayList<String> input = new ArrayList<String>(Arrays.asList(simulatedUserInput));
            IUI ui = new TestUI(input);

            DeveloperController developerController = new DeveloperController(ui, userService, projectService, bugReportService, userService.getUser("major"), developerAssignmentService, tagAssignmentService, mailboxService);
            developerController.getUseCase(13).run();
        } catch (ReportErrorToUserException e) {
            assert e.getMessage().equals("You are not allowed to add a test.");
        }

    }

    @Test
    public void unsuccessfullyProposedTest_TagError() throws Exception {
        try {
            String[] simulatedUserInput = {
                    "0",
                    "parse_ewd",
                    "0",
                    "Test test",
                    "."
            };

            ArrayList<String> input = new ArrayList<String>(Arrays.asList(simulatedUserInput));
            IUI ui = new TestUI(input);

            DeveloperController developerController = new DeveloperController(ui, userService, projectService, bugReportService, userService.getUser("major"), developerAssignmentService, tagAssignmentService, mailboxService);
            developerController.getUseCase(13).run();
        } catch (ReportErrorToUserException e) {
            assert e.getMessage().equals("The Bug Report doesn't has the tag Assigned, so no test can be added!");
        }
    }

}
