package UseCaseTests.IssuerControllerTest;

import Controller.UserController.IssuerController;
import Controller.UserController.UserController;
import CustomExceptions.ReportErrorToUserException;
import UseCaseTests.UseCasesUI.TestUI;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Karina on 14.04.2016.
 */
public class RegisterForNotification extends IssuerControllerInit {

    //region registered for project

    @Test
    public void successfullyRegisteredForProject_CreationOfNewBugReport() throws Exception {
        String[] simulatedUserInput = {
                "1",
                "0",
                "1"

        };
        ArrayList<String> input = new ArrayList<>(Arrays.asList(simulatedUserInput));
        TestUI ui = new TestUI(input);

        UserController issuerController = new IssuerController(ui, userService, projectService, bugReportService, tagAssignmentService, mailboxService, currentUser);
        issuerController.getUseCase(7).run();
    }

    @Test
    public void successfullyRegisteredForProject_BugReportNewTag() throws Exception {
        String[] simulatedUserInput = {
                "1",
                "0",
                "2"

        };
        ArrayList<String> input = new ArrayList<>(Arrays.asList(simulatedUserInput));
        TestUI ui = new TestUI(input);

        UserController issuerController = new IssuerController(ui, userService, projectService, bugReportService, tagAssignmentService, mailboxService, currentUser);
        issuerController.getUseCase(7).run();
    }

    @Test
    public void successfullyRegisteredForProject_BugReportSpecificTag() throws Exception {
        String[] simulatedUserInput = {
                "1",
                "0",
                "3",
                "UnderReview"
        };
        ArrayList<String> input = new ArrayList<>(Arrays.asList(simulatedUserInput));
        TestUI ui = new TestUI(input);

        UserController issuerController = new IssuerController(ui, userService, projectService, bugReportService, tagAssignmentService, mailboxService, currentUser);
        issuerController.getUseCase(7).run();
    }

    @Test
    public void successfullyRegisteredForProject_NewComment() throws Exception {
        String[] simulatedUserInput = {
                "1",
                "0",
                "4"
        };
        ArrayList<String> input = new ArrayList<>(Arrays.asList(simulatedUserInput));
        TestUI ui = new TestUI(input);

        UserController issuerController = new IssuerController(ui, userService, projectService, bugReportService, tagAssignmentService, mailboxService, currentUser);
        issuerController.getUseCase(7).run();
    }

    @Test(expected = ReportErrorToUserException.class)
    public void unsuccessfullyRegisteredForProject_UnvalidSpecificTag() throws Exception {
        String[] simulatedUserInput = {
                "1",
                "0",
                "3",
                "Test"
        };
        ArrayList<String> input = new ArrayList<>(Arrays.asList(simulatedUserInput));
        TestUI ui = new TestUI(input);

        UserController issuerController = new IssuerController(ui, userService, projectService, bugReportService, tagAssignmentService, mailboxService, currentUser);
        issuerController.getUseCase(7).run();
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void unsuccessfullyRegisteredForProject_UnvalidChoice() throws Exception {
        String[] simulatedUserInput = {
                "1",
                "0",
                "5"
        };
        ArrayList<String> input = new ArrayList<>(Arrays.asList(simulatedUserInput));
        TestUI ui = new TestUI(input);

        UserController issuerController = new IssuerController(ui, userService, projectService, bugReportService, tagAssignmentService, mailboxService, currentUser);
        issuerController.getUseCase(7).run();
    }

    //endregion

    //region registered for subsystem

    @Test
    public void successfullyRegisteredForSubSystem_CreationOfNewBugReport() throws Exception {
        String[] simulatedUserInput = {
                "2",
                "0",
                "0",
                "1"

        };
        ArrayList<String> input = new ArrayList<>(Arrays.asList(simulatedUserInput));
        TestUI ui = new TestUI(input);

        UserController issuerController = new IssuerController(ui, userService, projectService, bugReportService, tagAssignmentService, mailboxService, currentUser);
        issuerController.getUseCase(7).run();
    }

    @Test
    public void successfullyRegisteredForSubSystem_BugReportNewTag() throws Exception {
        String[] simulatedUserInput = {
                "2",
                "0",
                "0",
                "2"

        };
        ArrayList<String> input = new ArrayList<>(Arrays.asList(simulatedUserInput));
        TestUI ui = new TestUI(input);

        UserController issuerController = new IssuerController(ui, userService, projectService, bugReportService, tagAssignmentService, mailboxService, currentUser);
        issuerController.getUseCase(7).run();
    }

    @Test
    public void successfullyRegisteredForSubSystem_BugReportSpecificTag() throws Exception {
        String[] simulatedUserInput = {
                "2",
                "0",
                "0",
                "3",
                "UnderReview"
        };
        ArrayList<String> input = new ArrayList<>(Arrays.asList(simulatedUserInput));
        TestUI ui = new TestUI(input);

        UserController issuerController = new IssuerController(ui, userService, projectService, bugReportService, tagAssignmentService, mailboxService, currentUser);
        issuerController.getUseCase(7).run();
    }

    @Test
    public void successfullyRegisteredForSubSystem_NewComment() throws Exception {
        String[] simulatedUserInput = {
                "2",
                "0",
                "0",
                "4"
        };
        ArrayList<String> input = new ArrayList<>(Arrays.asList(simulatedUserInput));
        TestUI ui = new TestUI(input);

        UserController issuerController = new IssuerController(ui, userService, projectService, bugReportService, tagAssignmentService, mailboxService, currentUser);
        issuerController.getUseCase(7).run();
    }

    @Test(expected = ReportErrorToUserException.class)
    public void unsuccessfullyRegisteredForSubSystem_UnvalidSpecificTag() throws Exception {
        String[] simulatedUserInput = {
                "2",
                "0",
                "0",
                "3",
                "Test"
        };
        ArrayList<String> input = new ArrayList<>(Arrays.asList(simulatedUserInput));
        TestUI ui = new TestUI(input);

        UserController issuerController = new IssuerController(ui, userService, projectService, bugReportService, tagAssignmentService, mailboxService, currentUser);
        issuerController.getUseCase(7).run();
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void unsuccessfullyRegisteredForSubSystem_UnvalidChoice() throws Exception {
        String[] simulatedUserInput = {
                "2",
                "0",
                "0",
                "5"
        };
        ArrayList<String> input = new ArrayList<>(Arrays.asList(simulatedUserInput));
        TestUI ui = new TestUI(input);

        UserController issuerController = new IssuerController(ui, userService, projectService, bugReportService, tagAssignmentService, mailboxService, currentUser);
        issuerController.getUseCase(7).run();
    }

    //endregion

    //region registered for bugreport

    @Test
    public void successfullyRegisteredForBugReport_BugReportNewTag() throws Exception {
        String[] simulatedUserInput = {
                "3",
                "0",
                "Crash",
                "0",
                "1"

        };
        ArrayList<String> input = new ArrayList<>(Arrays.asList(simulatedUserInput));
        TestUI ui = new TestUI(input);

        UserController issuerController = new IssuerController(ui, userService, projectService, bugReportService, tagAssignmentService, mailboxService, currentUser);
        issuerController.getUseCase(7).run();
    }

    @Test
    public void successfullyRegisteredForBugReport_BugReportSpecificTag() throws Exception {
        String[] simulatedUserInput = {
                "3",
                "0",
                "Crash",
                "0",
                "2",
                "UnderReview"
        };
        ArrayList<String> input = new ArrayList<>(Arrays.asList(simulatedUserInput));
        TestUI ui = new TestUI(input);

        UserController issuerController = new IssuerController(ui, userService, projectService, bugReportService, tagAssignmentService, mailboxService, currentUser);
        issuerController.getUseCase(7).run();
    }

    @Test
    public void successfullyRegisteredForBugReport_NewComment() throws Exception {
        String[] simulatedUserInput = {
                "3",
                "0",
                "Crash",
                "0",
                "3"
        };
        ArrayList<String> input = new ArrayList<>(Arrays.asList(simulatedUserInput));
        TestUI ui = new TestUI(input);

        UserController issuerController = new IssuerController(ui, userService, projectService, bugReportService, tagAssignmentService, mailboxService, currentUser);
        issuerController.getUseCase(7).run();
    }

    @Test(expected = ReportErrorToUserException.class)
    public void unsuccessfullyRegisteredForBugReport_UnvalidSpecificTag() throws Exception {
        String[] simulatedUserInput = {
                "3",
                "0",
                "Crash",
                "0",
                "2",
                "Test"
        };
        ArrayList<String> input = new ArrayList<>(Arrays.asList(simulatedUserInput));
        TestUI ui = new TestUI(input);

        UserController issuerController = new IssuerController(ui, userService, projectService, bugReportService, tagAssignmentService, mailboxService, currentUser);
        issuerController.getUseCase(7).run();
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void unsuccessfullyRegisteredForBugReport_UnvalidChoice() throws Exception {
        String[] simulatedUserInput = {
                "3",
                "0",
                "Crash",
                "0",
                "5"
        };
        ArrayList<String> input = new ArrayList<>(Arrays.asList(simulatedUserInput));
        TestUI ui = new TestUI(input);

        UserController issuerController = new IssuerController(ui, userService, projectService, bugReportService, tagAssignmentService, mailboxService, currentUser);
        issuerController.getUseCase(7).run();
    }

    //endregion

}
