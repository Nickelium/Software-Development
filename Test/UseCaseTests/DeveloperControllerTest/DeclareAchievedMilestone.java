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
public class DeclareAchievedMilestone extends DeveloperControllerInit {
    @Test
    public void subsystem_valid() throws ReportErrorToUserException {
        String[] simulatedUserInput = {
                "1",
                "0",
                "M1.4"
        };

        ArrayList<String> input = new ArrayList<String>(Arrays.asList(simulatedUserInput));
        IUI ui = new TestUI(input);

        DeveloperController developerController = new DeveloperController(ui, userService, projectService, bugReportService, userService.getUser("major"), developerAssignmentService, tagAssignmentService, mailboxService);
        developerController.getUseCase(11).run();
    }

    @Test
    public void subsystem_Invalid_TooSmall() throws ReportErrorToUserException {
        try {
            String[] simulatedUserInput = {
                    "1",
                    "0",
                    "M1.0"
            };

            ArrayList<String> input = new ArrayList<String>(Arrays.asList(simulatedUserInput));
            IUI ui = new TestUI(input);

            DeveloperController developerController = new DeveloperController(ui, userService, projectService, bugReportService, userService.getUser("major"), developerAssignmentService, tagAssignmentService, mailboxService);
            developerController.getUseCase(11).run();
        } catch (ReportErrorToUserException e) {
            assert e.getMessage().equals("The new milestone is smaller than the current one");
        }
    }

    @Test
    public void subsystem_Invalid_BiggerThanSubsystem() throws ReportErrorToUserException {
        try {
            String[] simulatedUserInput = {
                    "1",
                    "1",
                    "M1.3"
            };

            ArrayList<String> input = new ArrayList<String>(Arrays.asList(simulatedUserInput));
            IUI ui = new TestUI(input);

            DeveloperController developerController = new DeveloperController(ui, userService, projectService, bugReportService, userService.getUser("major"), developerAssignmentService, tagAssignmentService, mailboxService);
            developerController.getUseCase(11).run();
        } catch (ReportErrorToUserException e) {
            assert e.getMessage().equals("The new milestone exceeds milestone of subsystem!");
        }
    }

    @Test
    // TODO: this test can only be valid if the milestone of the subsystems is increased first.
    public void entireProject_valid() throws ReportErrorToUserException {
        String[] simulatedUserInput = {
                "1",
                "3",
                "M1.2"
        };

        ArrayList<String> input = new ArrayList<String>(Arrays.asList(simulatedUserInput));
        IUI ui = new TestUI(input);

        DeveloperController developerController = new DeveloperController(ui, userService, projectService, bugReportService, userService.getUser("major"), developerAssignmentService, tagAssignmentService, mailboxService);
        developerController.getUseCase(11).run();
    }
}
