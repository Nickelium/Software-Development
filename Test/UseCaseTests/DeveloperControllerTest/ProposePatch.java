package UseCaseTests.DeveloperControllerTest;

import Controller.IUI;
import Controller.UserController.DeveloperController;
import CustomExceptions.ReportErrorToUserException;
import Model.BugReport.SearchMethod.SearchOnTitle;
import Model.User.User;
import UseCaseTests.UseCasesUI.TestUI;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Karina on 14.04.2016.
 */
public class ProposePatch extends DeveloperControllerInit {

    public void addTests() throws Exception {
        User maria = userService.getUser("maria");
        bugReportService.createTest("Test", maria, bugReportService.search(new SearchOnTitle("Crash"), maria).get(0));
    }

    @Test
    public void successfullyProposedPatch() throws Exception {
        addTests();
        String[] simulatedUserInput = {
                "0",
                "Crash",
                "0",
                "Patch test",
                "."
        };
        ArrayList<String> input = new ArrayList<String>(Arrays.asList(simulatedUserInput));
        IUI ui = new TestUI(input);
        DeveloperController developerController = new DeveloperController(ui, userService, projectService, bugReportService, userService.getUser("maria"), developerAssignmentService, tagAssignmentService, mailboxService);
        developerController.getUseCase(12).run();
    }

    @Test(expected = ReportErrorToUserException.class)
    public void unsuccessfullyProposedPatch_UserNotAllowed() throws Exception {
        addTests();
        String[] simulatedUserInput = {
                "0",
                "Crash",
                "0",
                "Patch test",
                "."
        };

        ArrayList<String> input = new ArrayList<String>(Arrays.asList(simulatedUserInput));
        IUI ui = new TestUI(input);

        DeveloperController developerController = new DeveloperController(ui, userService, projectService, bugReportService, userService.getUser("major"), developerAssignmentService, tagAssignmentService, mailboxService);
        developerController.getUseCase(12).run();

    }

    @Test(expected = ReportErrorToUserException.class)
    public void unsuccessfullyProposedPatch_NoTestsYet() throws Exception {
        String[] simulatedUserInput = {
                "0",
                "Crash",
                "0",
                "Patch test",
                "."
        };
        ArrayList<String> input = new ArrayList<String>(Arrays.asList(simulatedUserInput));
        IUI ui = new TestUI(input);
        DeveloperController developerController = new DeveloperController(ui, userService, projectService, bugReportService, userService.getUser("maria"), developerAssignmentService, tagAssignmentService, mailboxService);
        developerController.getUseCase(12).run();
    }


    @Test(expected = ReportErrorToUserException.class)
    public void unsuccessfullyProposedPatch_TagError() throws Exception {
        String[] simulatedUserInput = {
                "0",
                "parse_ewd",
                "0",
                "Patch test",
                "."
        };

        ArrayList<String> input = new ArrayList<String>(Arrays.asList(simulatedUserInput));
        IUI ui = new TestUI(input);

        DeveloperController developerController = new DeveloperController(ui, userService, projectService, bugReportService, userService.getUser("major"), developerAssignmentService, tagAssignmentService, mailboxService);
        developerController.getUseCase(12).run();
    }

}
