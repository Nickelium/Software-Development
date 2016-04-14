package UseCaseTests.IssuerControllerTest;

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
 * Created by Tom on 11/03/16.
 */
public class UpdateBugReport extends IssuerControllerInit {

    public void setTagToUnderReview() throws Exception {
        User maria = userService.getUser("maria");
        bugReportService.createTest("Test Test", maria, bugReportService.search(new SearchOnTitle("Crash"), maria).get(0));
        bugReportService.createPatch("Test Patch", maria, bugReportService.search(new SearchOnTitle("Crash"), maria).get(0));
    }

    @Test
    public void successfullyUpdatedBugReport() throws Exception {
        setTagToUnderReview();
        String[] simulatedUserInput = {
                "0",
                "Crash",
                "0",
                "Resolved",
                "0"
        };

        ArrayList<String> input = new ArrayList<String>(Arrays.asList(simulatedUserInput));
        IUI ui = new TestUI(input);

        DeveloperController developerController = new DeveloperController(ui, userService, projectService, bugReportService, userService.getUser("major"), developerAssignmentService, tagAssignmentService, mailboxService);
        developerController.getUseCase(5).run();
    }


    @Test(expected = ReportErrorToUserException.class)
    public void unSuccessfullyUpdatedBugReport_IllegalUser() throws Exception {
        setTagToUnderReview();
        String[] simulatedUserInput = {
                "0",
                "Crash",
                "0",
                "Assigned"
        };

        ArrayList<String> input = new ArrayList<String>(Arrays.asList(simulatedUserInput));
        IUI ui = new TestUI(input);

        DeveloperController developerController = new DeveloperController(ui, userService, projectService, bugReportService, userService.getUser("maria"), developerAssignmentService, tagAssignmentService, mailboxService);
        developerController.getUseCase(5).run();
    }

    @Test(expected = ReportErrorToUserException.class)
    public void unSuccessfullyUpdatedBugReport_IllegalTagSwitch() throws Exception {
        String[] simulatedUserInput = {
                "0",
                "Crash",
                "0",
                "UnderReview"
        };

        ArrayList<String> input = new ArrayList<String>(Arrays.asList(simulatedUserInput));
        IUI ui = new TestUI(input);

        DeveloperController developerController = new DeveloperController(ui, userService, projectService, bugReportService, userService.getUser("major"), developerAssignmentService, tagAssignmentService, mailboxService);
        developerController.getUseCase(5).run();
    }
}
