package UseCaseTests.IssuerControllerTest;

import Controller.UserController.IssuerController;
import Controller.UserController.UserController;
import CustomExceptions.ReportErrorToUserException;
import UseCaseTests.UseCasesUI.TestUI;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertTrue;

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


        assertTrue(userService.getUser("doc").getMailbox().getRegistrations().get(0).toString().contains("Registration for creation of new bug report in"));
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

        assertTrue(userService.getUser("doc").getMailbox().getRegistrations().get(0).toString().contains("Registration for change of tag in"));

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

        assertTrue(userService.getUser("doc").getMailbox().getRegistrations().get(0).toString().contains("Registration for changed of tag to"));

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

        assertTrue(userService.getUser("doc").getMailbox().getRegistrations().get(0).toString().contains("Registration for creation of new comment in"));

    }

    @Test
    public void unsuccessfullyRegisteredForProject_UnvalidSpecificTag() throws Exception {
        try {
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
        } catch (ReportErrorToUserException e) {
            assert e.getMessage().equals("The tag you entered does not exist.");
        }
    }

    @Test
    public void unsuccessfullyRegisteredForProject_UnvalidChoice() throws Exception {
        try {
            String[] simulatedUserInput = {
                    "1",
                    "0",
                    "5"
            };
            ArrayList<String> input = new ArrayList<>(Arrays.asList(simulatedUserInput));
            TestUI ui = new TestUI(input);

            UserController issuerController = new IssuerController(ui, userService, projectService, bugReportService, tagAssignmentService, mailboxService, currentUser);
            issuerController.getUseCase(7).run();
        } catch (IndexOutOfBoundsException e) {
            assert e.getMessage().equals("Invalid index input");
        }
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

        assertTrue(userService.getUser("doc").getMailbox().getRegistrations().get(0).toString().contains("Registration for creation of new bug report in"));

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

        assertTrue(userService.getUser("doc").getMailbox().getRegistrations().get(0).toString().contains("Registration for change of tag in"));

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

        assertTrue(userService.getUser("doc").getMailbox().getRegistrations().get(0).toString().contains("Registration for changed of tag to"));

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

        assertTrue(userService.getUser("doc").getMailbox().getRegistrations().get(0).toString().contains("Registration for creation of new comment in"));

    }

    @Test
    public void unsuccessfullyRegisteredForSubSystem_UnvalidSpecificTag() throws Exception {
        try {
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
        } catch (ReportErrorToUserException e) {
            assert e.getMessage().equals("The tag you entered does not exist.");
        }
    }

    @Test
    public void unsuccessfullyRegisteredForSubSystem_UnvalidChoice() throws Exception {
        try {
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
        } catch (IndexOutOfBoundsException e) {
            assert e.getMessage().equals("Invalid index input");
        }
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

    @Test
    public void unsuccessfullyRegisteredForBugReport_UnvalidSpecificTag() throws Exception {
        try {
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
        } catch (ReportErrorToUserException e) {
            assert e.getMessage().equals("The tag you entered does not exist.");
        }
    }

    @Test
    public void unsuccessfullyRegisteredForBugReport_UnvalidChoice() throws Exception {
        try {
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
        } catch (IndexOutOfBoundsException e) {
            assert e.getMessage().equals("Invalid index input");
        }
    }

    //endregion

}
