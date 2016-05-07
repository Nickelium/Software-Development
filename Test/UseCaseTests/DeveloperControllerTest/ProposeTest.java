package UseCaseTests.DeveloperControllerTest;

import Controller.IUI;
import Controller.UserController.DeveloperController;
import CustomExceptions.ReportErrorToUserException;
import UseCaseTests.UseCasesUI.TestUI;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertTrue;

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
        int initialSize = bugReportService.getAllBugReports(userService.getUser("major")).
                stream().filter(x -> x.getTitle().contains("Crash")).findFirst().get().getTests().size();
        System.out.print(initialSize);

        ArrayList<String> input = new ArrayList<String>(Arrays.asList(simulatedUserInput));
        IUI ui = new TestUI(input);
        DeveloperController developerController = new DeveloperController(ui, userService, projectService, bugReportService, performanceMetricsService, userService.getUser("maria"), developerAssignmentService, tagAssignmentService, mailboxService);
        developerController.getUseCase(DeveloperUseCase.PROPOSE_TEST.value).run();

        assertTrue(bugReportService.getAllBugReports(userService.getUser("major")).stream().anyMatch(x -> x.getTitle().contains("Crash") && x.getTests().size() == initialSize + 1));
    }

    @Test
    public void unsuccessfullyProposedTest_UserNotAllowed() throws Exception {
        int initialSize = bugReportService.getAllBugReports(userService.getUser("major")).
                stream().filter(x -> x.getTitle().contains("Crash")).findFirst().get().getTests().size();

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

            DeveloperController developerController = new DeveloperController(ui, userService, projectService, bugReportService, performanceMetricsService, userService.getUser("major"), developerAssignmentService, tagAssignmentService, mailboxService);
            developerController.getUseCase(DeveloperUseCase.PROPOSE_TEST.value).run();
        } catch (ReportErrorToUserException e) {
            assert e.getMessage().equals("You are not allowed to add a test.");
        }

        assertTrue(bugReportService.getAllBugReports(userService.getUser("major")).stream().anyMatch(x -> x.getTitle().contains("Crash") && x.getTests().size() == initialSize));

    }

    @Test
    public void unsuccessfullyProposedTest_TagError() throws Exception {
        int initialSize = bugReportService.getAllBugReports(userService.getUser("major")).
                stream().filter(x -> x.getTitle().contains("parse_ewd")).findFirst().get().getTests().size();

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

            DeveloperController developerController = new DeveloperController(ui, userService, projectService, bugReportService, performanceMetricsService, userService.getUser("major"), developerAssignmentService, tagAssignmentService, mailboxService);
            developerController.getUseCase(DeveloperUseCase.PROPOSE_TEST.value).run();
        } catch (ReportErrorToUserException e) {
            assert e.getMessage().equals("The Bug Report doesn't has the tag Assigned, so no test can be added!");
        }

        assertTrue(bugReportService.getAllBugReports(userService.getUser("major")).stream().anyMatch(x -> x.getTitle().contains("parse_ewd") && x.getTests().size() == initialSize));
    }

}
