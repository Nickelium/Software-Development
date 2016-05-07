package UseCaseTests.IssuerControllerTest;

import Controller.IUI;
import Controller.UserController.DeveloperController;
import CustomExceptions.ReportErrorToUserException;
import Model.BugReport.SearchMethod.SearchOnTitle;
import Model.BugReport.TagTypes.Assigned;
import Model.BugReport.TagTypes.Resolved;
import Model.BugReport.TagTypes.UnderReview;
import Model.User.User;
import UseCaseTests.UseCasesUI.TestUI;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

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

        assert bugReportService.getAllBugReports(currentUser).stream().noneMatch(x -> x.getTitle().contains("Crash") && x.getTag().equals(new Resolved(x.getPatches().get(0))));

        String[] simulatedUserInput = {
                "0",
                "Crash",
                "0",
                "Resolved",
                "0"
        };

        ArrayList<String> input = new ArrayList<String>(Arrays.asList(simulatedUserInput));
        IUI ui = new TestUI(input);

        DeveloperController developerController = new DeveloperController(ui, userService, projectService, bugReportService, performanceMetricsService, userService.getUser("major"), developerAssignmentService, tagAssignmentService, mailboxService);
        developerController.getUseCase(IssuerUseCase.UPDATE_BUGREPORT.value).run();

        assert bugReportService.getAllBugReports(currentUser).stream().filter(x -> x.getTitle().contains("Crash") && x.getTag() instanceof Resolved).collect(Collectors.toList()).size() == 1;
    }

    @Test
    public void unSuccessfullyUpdatedBugReport_IllegalUser() throws Exception {
        try {
            setTagToUnderReview();
            String[] simulatedUserInput = {
                    "0",
                    "Crash",
                    "0",
                    "Assigned"
            };

            ArrayList<String> input = new ArrayList<String>(Arrays.asList(simulatedUserInput));
            IUI ui = new TestUI(input);

            DeveloperController developerController = new DeveloperController(ui, userService, projectService, bugReportService, performanceMetricsService, userService.getUser("maria"), developerAssignmentService, tagAssignmentService, mailboxService);
            developerController.getUseCase(IssuerUseCase.UPDATE_BUGREPORT.value).run();
        } catch (ReportErrorToUserException e) {
            assert e.getMessage().equals("Cannot preform tag change! No valid permission.");
            assert bugReportService.getAllBugReports(currentUser).stream().noneMatch(x -> x.getTitle().contains("Crash") && x.getTag() instanceof Assigned);
        }
    }

    @Test
    public void unSuccessfullyUpdatedBugReport_IllegalTagSwitch() throws Exception {
        try {
            String[] simulatedUserInput = {
                    "0",
                    "Crash",
                    "0",
                    "UnderReview"
            };

            ArrayList<String> input = new ArrayList<String>(Arrays.asList(simulatedUserInput));
            IUI ui = new TestUI(input);

            DeveloperController developerController = new DeveloperController(ui, userService, projectService, bugReportService, performanceMetricsService, userService.getUser("major"), developerAssignmentService, tagAssignmentService, mailboxService);
            developerController.getUseCase(IssuerUseCase.UPDATE_BUGREPORT.value).run();
        } catch (ReportErrorToUserException e) {
            assert e.getMessage().equals("Cannot preform tag change! No valid permission.");
            assert bugReportService.getAllBugReports(currentUser).stream().noneMatch(x -> x.getTitle().contains("Crash") && x.getTag() instanceof UnderReview);
        }
    }

}
