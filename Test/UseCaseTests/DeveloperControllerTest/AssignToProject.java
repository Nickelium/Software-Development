package UseCaseTests.DeveloperControllerTest;

import Controller.IUI;
import Controller.UserController.DeveloperController;
import CustomExceptions.ReportErrorToUserException;
import Model.User.User;
import UseCaseTests.UseCasesUI.TestUI;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
        //Check not containing user before
        User user = userService.getUser("test1");
        assertFalse(projectService.getAllProjects().stream().anyMatch(x -> x.getName().equals("ProjectA") && x.getAllInvolvedDevelopers().contains(user)));

        ArrayList<String> input = new ArrayList<String>(Arrays.asList(simulatedUserInput));
        IUI ui = new TestUI(input);

        DeveloperController developerController = new DeveloperController(ui, userService, projectService, bugReportService, performanceMetricsService, userService.getUser("major"), developerAssignmentService, tagAssignmentService, mailboxService);
        developerController.getUseCase(9).run();

        //Check containing user after
        assertTrue(projectService.getAllProjects().stream().anyMatch(x -> x.getName().equals("ProjectA") && x.getAllInvolvedDevelopers().contains(user)));
    }

    @Test
    public void unsuccessfullyAssignedDeveloperToProject() throws Exception {
        try {
            String[] simulatedUserInput = {};

            ArrayList<String> input = new ArrayList<String>(Arrays.asList(simulatedUserInput));
            IUI ui = new TestUI(input);

            DeveloperController developerController = new DeveloperController(ui, userService, projectService, bugReportService, performanceMetricsService, userService.getUser("test1"), developerAssignmentService, tagAssignmentService, mailboxService);
            developerController.getUseCase(9).run();
        } catch (ReportErrorToUserException e) {
            assert e.getMessage().equals("You are not assigned as lead developer in any project. You are not allowed to assign a new developer to any project.");
        }
    }

}
