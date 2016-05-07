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

import static org.junit.Assert.assertTrue;

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
        int initialSize = bugReportService.getAllBugReports(userService.getUser("major")).
                stream().filter(x -> x.getTitle().contains("Crash")).findFirst().get().getPatches().size();

        ArrayList<String> input = new ArrayList<String>(Arrays.asList(simulatedUserInput));
        IUI ui = new TestUI(input);
        DeveloperController developerController = new DeveloperController(ui, userService, projectService, bugReportService, performanceMetricsService, userService.getUser("maria"), developerAssignmentService, tagAssignmentService, mailboxService);
        developerController.getUseCase(DeveloperUseCase.PROPOSE_PATCH.value).run();

        assertTrue(bugReportService.getAllBugReports(userService.getUser("major")).stream().anyMatch(x -> x.getTitle().contains("Crash") && x.getPatches().size() == initialSize + 1));
    }

    @Test
    public void unsuccessfullyProposedPatch_UserNotAllowed() throws Exception {
        int initialSize = bugReportService.getAllBugReports(userService.getUser("major")).
                stream().filter(x -> x.getTitle().contains("Crash")).findFirst().get().getPatches().size();

        try {
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

            DeveloperController developerController = new DeveloperController(ui, userService, projectService, bugReportService, performanceMetricsService, userService.getUser("major"), developerAssignmentService, tagAssignmentService, mailboxService);
            developerController.getUseCase(DeveloperUseCase.PROPOSE_PATCH.value).run();
        } catch (ReportErrorToUserException e) {
            assert e.getMessage().equals("You are not allowed to add a patch.");
        }

        assertTrue(bugReportService.getAllBugReports(userService.getUser("major")).stream().anyMatch(x -> x.getTitle().contains("Crash") && x.getPatches().size() == initialSize));

    }

    @Test
    public void unsuccessfullyProposedPatch_NoTestsYet() throws Exception {
        int initialSize = bugReportService.getAllBugReports(userService.getUser("major")).
                stream().filter(x -> x.getTitle().contains("Crash")).findFirst().get().getPatches().size();

        try {
            String[] simulatedUserInput = {
                    "0",
                    "Crash",
                    "0",
                    "Patch test",
                    "."
            };
            ArrayList<String> input = new ArrayList<String>(Arrays.asList(simulatedUserInput));
            IUI ui = new TestUI(input);
            DeveloperController developerController = new DeveloperController(ui, userService, projectService, bugReportService, performanceMetricsService, userService.getUser("maria"), developerAssignmentService, tagAssignmentService, mailboxService);
            developerController.getUseCase(DeveloperUseCase.PROPOSE_PATCH.value).run();
        } catch (ReportErrorToUserException e) {
            assert e.getMessage().equals("No tests are submitted yet, so no patches can be added.");
        }

        assertTrue(bugReportService.getAllBugReports(userService.getUser("major")).stream().anyMatch(x -> x.getTitle().contains("Crash") && x.getPatches().size() == initialSize));
    }

    @Test
    public void unsuccessfullyProposedPatch_TagError() throws Exception {
        int initialSize = bugReportService.getAllBugReports(userService.getUser("major")).
                stream().filter(x -> x.getTitle().contains("parse_ewd")).findFirst().get().getPatches().size();
        try {
            String[] simulatedUserInput = {
                    "0",
                    "parse_ewd",
                    "0",
                    "Patch test",
                    "."
            };

            ArrayList<String> input = new ArrayList<String>(Arrays.asList(simulatedUserInput));
            IUI ui = new TestUI(input);

            DeveloperController developerController = new DeveloperController(ui, userService, projectService, bugReportService, performanceMetricsService, userService.getUser("major"), developerAssignmentService, tagAssignmentService, mailboxService);
            developerController.getUseCase(DeveloperUseCase.PROPOSE_PATCH.value).run();
        } catch (ReportErrorToUserException e) {
            assert e.getMessage().equals("No patches can be submitted because the bug report doesn't have the proper tag.");
        }

        assertTrue(bugReportService.getAllBugReports(userService.getUser("major")).stream().anyMatch(x -> x.getTitle().contains("parse_ewd") && x.getPatches().size() == initialSize));
    }

}
