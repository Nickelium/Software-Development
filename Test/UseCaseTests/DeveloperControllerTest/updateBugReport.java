package UseCaseTests.DeveloperControllerTest;

import Controller.IUI;
import Controller.UserController.DeveloperController;
import CustomExceptions.ModelException;
import UseCaseTests.UseCasesUI.TestUI;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Tom on 11/03/16.
 */
public class updateBugReport extends DeveloperTestInitializer {
    @Test
    public void successfullyUpdatedBugReport() throws Exception {
        String[] simulatedUserInput = {
                "0",
                "Crash",
                "0",
                "UnderReview"
        };

        ArrayList<String> input = new ArrayList<String>(Arrays.asList(simulatedUserInput));
        IUI ui = new TestUI(input);

        DeveloperController developerController = new DeveloperController(ui, userService, projectService, bugReportService, userService.getUser("major"), developerAssignmentService, tagAssignmentService);
        developerController.callUseCase(7);
    }

    @Test(expected = ModelException.class)
    public void unSuccessfullyUpdatedBugReport_IllegalUser() throws Exception {
        String[] simulatedUserInput = {
                "0",
                "Crash",
                "0",
                "UnderReview"
        };

        ArrayList<String> input = new ArrayList<String>(Arrays.asList(simulatedUserInput));
        IUI ui = new TestUI(input);

        DeveloperController developerController = new DeveloperController(ui, userService, projectService, bugReportService, userService.getUser("test1"), developerAssignmentService, tagAssignmentService);
        developerController.callUseCase(7);
    }

    @Test(expected = ModelException.class)
    public void unSuccessfullyUpdatedBugReport_IllegalTagSwitch() throws Exception {
        String[] simulatedUserInput = {
                "0",
                "parse_ewd",
                "0",
                "UnderReview"
        };

        ArrayList<String> input = new ArrayList<String>(Arrays.asList(simulatedUserInput));
        IUI ui = new TestUI(input);

        DeveloperController developerController = new DeveloperController(ui, userService, projectService, bugReportService, userService.getUser("major"), developerAssignmentService, tagAssignmentService);
        developerController.callUseCase(7);
    }
}
