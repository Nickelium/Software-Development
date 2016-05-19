package UseCaseTests.IssuerControllerTest;

import Controller.UserController.IssuerController;
import Controller.UserController.UserController;
import UseCaseTests.UseCasesUI.TestUI;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Karina on 14.04.2016.
 */
public class ShowNotifications extends IssuerControllerInit {

    @Test
    public void successfullyShownNotification() throws Exception {

        String[] simulatedUserInputRegister = {
                "1",
                "0",
                "1"

        };
        ArrayList<String> inputRegister = new ArrayList<>(Arrays.asList(simulatedUserInputRegister));
        TestUI uiRegister = new TestUI(inputRegister);

        UserController issuerControllerRegister = new IssuerController(uiRegister, userService, projectService, bugReportService, performanceMetricsService, tagAssignmentService, mailboxService, currentUser);
        issuerControllerRegister.getUseCase(IssuerUseCase.REGISTER_FOR_NOTIFICATION.value).run();

        String[] simulatedUserInputCreateBugReport = {
                "0",
                "0",
                "Bug Report Test Title",
                "Bug Report Test Description",
                "How to reproduce",
                ".",
                ".",
                "Error!!",
                ".",
                "1",
                "10",
                "0",
                "1",
                "-1"
        };
        ArrayList<String> inputCreateBugReport = new ArrayList<>(Arrays.asList(simulatedUserInputCreateBugReport));
        TestUI uiCreateBugReport = new TestUI(inputCreateBugReport);

        UserController issuerControllerCreateBugReport = new IssuerController(uiCreateBugReport, userService, projectService, bugReportService, performanceMetricsService, tagAssignmentService, mailboxService, currentUser);
        issuerControllerCreateBugReport.getUseCase(IssuerUseCase.CREATE_BUGREPORT.value).run();

        String[] simulatedUserInput = {
                "1"
        };
        ArrayList<String> input = new ArrayList<>(Arrays.asList(simulatedUserInput));
        TestUI ui = new TestUI(input);

        UserController issuerController = new IssuerController(ui, userService, projectService, bugReportService, performanceMetricsService, tagAssignmentService, mailboxService, currentUser);
        issuerController.getUseCase(IssuerUseCase.SHOW_NOTIFICATIONS.value).run();
    }

}
