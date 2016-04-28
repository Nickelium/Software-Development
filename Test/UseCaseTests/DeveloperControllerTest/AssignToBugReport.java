package UseCaseTests.DeveloperControllerTest;

import Controller.IUI;
import Controller.UserController.DeveloperController;
import CustomExceptions.ReportErrorToUserException;
import Model.Project.Project;
import Model.Roles.Programmer;
import Model.User.Developer;
import Model.User.User;
import UseCaseTests.UseCasesUI.TestUI;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


/**
 * Created by Tom on 10/03/16.
 */
public class AssignToBugReport extends DeveloperControllerInit {
    @Before
    public void extraInitialization() throws Exception {
        super.initialization();
        Developer tempUser = userService.createDeveloper("test", "", "Testing", "test3");
        List<Project> projects = projectService.getAllProjects().stream().filter(x -> x.getName().equals("ProjectA")).collect(Collectors.toList());
        if (projects.isEmpty()) throw new Exception("No projectA found!");
        projectService.assignRole(projects.get(0), new Programmer(tempUser), userService.getUser("major"));
    }

    @Test
    public void successfullyAssignedDeveloperToBugReport() throws Exception {
        String[] simulatedUserInput = {
                "0",
                "Crash",
                "0",
                "2",
        };
        //Check not containing user before
        User user = userService.getUser("test3");
        assertFalse(bugReportService.getAllBugReports(userService.getUser("major")).stream()
                .anyMatch(x -> x.getTitle().contains("Crash") && x.getAssignees().contains(user)));

        ArrayList<String> input = new ArrayList<String>(Arrays.asList(simulatedUserInput));
        IUI ui = new TestUI(input);

        DeveloperController developerController = new DeveloperController(ui, userService, projectService, bugReportService, userService.getUser("major"), developerAssignmentService, tagAssignmentService, mailboxService);
        developerController.getUseCase(10).run();

        //Check containing user after
        assertTrue(bugReportService.getAllBugReports(userService.getUser("major")).stream()
                .anyMatch(x -> x.getTitle().contains("Crash") && x.getAssignees().contains(user)));
    }

    @Test
    public void unsuccessfullyAssignedDeveloperToBugReport() throws Exception {
        try {
            String[] simulatedUserInput = {
                    "0",
                    "Crash",
                    "0",
                    "0"
            };

            ArrayList<String> input = new ArrayList<String>(Arrays.asList(simulatedUserInput));
            IUI ui = new TestUI(input);

            DeveloperController developerController = new DeveloperController(ui, userService, projectService, bugReportService, userService.getUser("test1"), developerAssignmentService, tagAssignmentService, mailboxService);
            developerController.getUseCase(10).run();
        } catch (ReportErrorToUserException e) {
            assert e.getMessage().equals("Cannot assign developer to bug report!");
        }

        User user = userService.getUser("major");
        assertFalse(!bugReportService.getAllBugReports(userService.getUser("major")).stream()
                .anyMatch(x -> x.getTitle().contains("Crash") && x.getAssignees().contains(user)));
    }

}
