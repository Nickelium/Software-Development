package UseCaseTests.DeveloperControllerTest;

import Controller.IUI;
import Controller.UserController.DeveloperController;
import CustomExceptions.ReportErrorToUserException;
import Model.Project.Project;
import Model.Roles.Programmer;
import Model.User.Developer;
import UseCaseTests.UseCasesUI.TestUI;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

        ArrayList<String> input = new ArrayList<String>(Arrays.asList(simulatedUserInput));
        IUI ui = new TestUI(input);

        DeveloperController developerController = new DeveloperController(ui, userService, projectService, bugReportService, userService.getUser("major"), developerAssignmentService, tagAssignmentService, mailboxService);
        developerController.getUseCase(10).run();
    }

    @Test(expected = ReportErrorToUserException.class)
    public void unsuccessfullyAssignedDeveloperToBugReport() throws Exception {
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

    }

}
