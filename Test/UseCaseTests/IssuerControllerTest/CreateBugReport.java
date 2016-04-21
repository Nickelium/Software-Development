package UseCaseTests.IssuerControllerTest;

import Controller.UserController.IssuerController;
import Controller.UserController.UserController;
import UseCaseTests.UseCasesUI.TestUI;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Karina on 10.03.2016.
 */
public class CreateBugReport extends IssuerControllerInit {

    @Test
    public void successfullyCreatedPublicBugReport() throws Exception {
        String[] simulatedUserInput = {
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
                "0",
                "1",
                "-1"
        };
        ArrayList<String> input = new ArrayList<>(Arrays.asList(simulatedUserInput));
        TestUI ui = new TestUI(input);

        UserController issuerController = new IssuerController(ui, userService, projectService, bugReportService, tagAssignmentService, mailboxService, currentUser);
        issuerController.getUseCase(2).run();

    }

    @Test
    public void successfullyCreatedPrivateBugReport() throws Exception {
        String[] simulatedUserInput = {
                "0",
                "0",
                "Bug Report Test Title",
                "Bug Report Test Description",
                "How to reproduce",
                ".",
                ".",
                "Error!!",
                ".",
                "0",
                "0",
                "-1"
        };
        ArrayList<String> input = new ArrayList<>(Arrays.asList(simulatedUserInput));
        TestUI ui = new TestUI(input);

        UserController issuerController = new IssuerController(ui, userService, projectService, bugReportService, tagAssignmentService, mailboxService, currentUser);
        issuerController.getUseCase(2).run();
    }

    @Test
    public void successfullyCreatedPrivateBugReport_NoOptionalValues() throws Exception {
        String[] simulatedUserInput = {
                "0",
                "0",
                "Bug Report Test Title",
                "Bug Report Test Description",
                ".",
                ".",
                ".",
                "0",
                "0",
                "-1"
        };
        ArrayList<String> input = new ArrayList<>(Arrays.asList(simulatedUserInput));
        TestUI ui = new TestUI(input);

        UserController issuerController = new IssuerController(ui, userService, projectService, bugReportService, tagAssignmentService, mailboxService, currentUser);
        issuerController.getUseCase(2).run();
    }

    @Test
    public void unsuccessfulCreateBugReport_invalidProjectChoice() throws Exception {
        try {
            String[] simulatedUserInput = {
                    "10"
            };
            ArrayList<String> input = new ArrayList<>(Arrays.asList(simulatedUserInput));
            TestUI ui = new TestUI(input);

            UserController issuerController = new IssuerController(ui, userService, projectService, bugReportService, tagAssignmentService, mailboxService, currentUser);
            issuerController.getUseCase(2).run();
        } catch (IndexOutOfBoundsException e) {
            assert e.getMessage().equals("Index: 10, Size: 2");
        }

    }

    @Test
    public void unsuccessfulCreateBugReport_invalidBugReportChoice() throws Exception {
        try {
            String[] simulatedUserInput = {
                    "1",
                    "10"
            };
            ArrayList<String> input = new ArrayList<>(Arrays.asList(simulatedUserInput));
            TestUI ui = new TestUI(input);

            UserController issuerController = new IssuerController(ui, userService, projectService, bugReportService, tagAssignmentService, mailboxService, currentUser);
            issuerController.getUseCase(2).run();
        } catch (IndexOutOfBoundsException e) {
            assert e.getMessage().equals("Index: 10, Size: 3");
        }
    }

    @Test
    public void unsuccessfulCreateBugReport_invalidDependencyChoice() throws Exception {
        try {
            String[] simulatedUserInput = {
                    "1",
                    "1",
                    "Bug Report Test Title",
                    "Bug Report Test Description",
                    "10"
            };
            ArrayList<String> input = new ArrayList<>(Arrays.asList(simulatedUserInput));
            TestUI ui = new TestUI(input);

            UserController issuerController = new IssuerController(ui, userService, projectService, bugReportService, tagAssignmentService, mailboxService, currentUser);
            issuerController.getUseCase(2).run();
        } catch (IndexOutOfBoundsException e) {
            assert e.getMessage().equals("Index: 0, Size: 0");
        }
    }

}
