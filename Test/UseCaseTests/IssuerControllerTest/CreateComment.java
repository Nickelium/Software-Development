package UseCaseTests.IssuerControllerTest;

import Controller.UserController.IssuerController;
import Controller.UserController.UserController;
import CustomExceptions.ReportErrorToUserException;
import UseCaseTests.UseCasesUI.TestUI;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Created by Karina on 11.03.2016.
 */
public class CreateComment extends IssuerControllerInit {

    @Test
    public void successfullyCreatedComment_forBugReport() throws Exception {

        assert bugReportService.getAllBugReports(currentUser).stream().noneMatch(x -> x.getTitle().contains("Crash")
                && x.getAllComments().stream().anyMatch(y -> y.getText().contains("This is a test")));

        String[] simulatedUserInput = {
                "0",
                "Crash",
                "0",
                "b",
                "This",
                "Is",
                "A",
                "Test",
                "This is a test",
                ".",
        };
        ArrayList<String> input = new ArrayList<>(Arrays.asList(simulatedUserInput));
        TestUI ui = new TestUI(input);

        UserController issuerController = new IssuerController(ui, userService, projectService, bugReportService, performanceMetricsService, tagAssignmentService, mailboxService, currentUser);
        issuerController.getUseCase(IssuerUseCase.CREATE_COMMENT.value).run();

        assert bugReportService.getAllBugReports(currentUser).stream().filter(x -> x.getTitle().contains("Crash")
                && x.getAllComments().stream().anyMatch(y -> y.getText().contains("This is a test"))).collect(Collectors.toList()).size() == 1;
    }

    @Test
    public void successfullyCreatedComment_forComment() throws Exception {
        successfullyCreatedComment_forBugReport();

        String[] simulatedUserInput = {
                "0",
                "Crash",
                "0",
                "c",
                "0",
                "This",
                "Is",
                "A",
                "Test",
                "This is a test",
                ".",
        };
        ArrayList<String> input = new ArrayList<>(Arrays.asList(simulatedUserInput));
        TestUI ui = new TestUI(input);

        UserController issuerController = new IssuerController(ui, userService, projectService, bugReportService, performanceMetricsService, tagAssignmentService, mailboxService, currentUser);
        issuerController.getUseCase(IssuerUseCase.CREATE_COMMENT.value).run();

        assert bugReportService.getAllBugReports(currentUser).stream().filter(x -> x.getTitle().contains("Crash")
                && (x.getAllComments().stream().filter(y -> y.getText().contains("This is a test")).collect(Collectors.toList()).size()) == 2).collect(Collectors.toList()).size() == 1;
    }

    @Test
    public void unsuccessfulCreatedComment_forBugReport_invalidChoice() throws Exception {
        try {
            String[] simulatedUserInput = {
                    "0",
                    "Crash",
                    "1"
            };
            ArrayList<String> input = new ArrayList<>(Arrays.asList(simulatedUserInput));
            TestUI ui = new TestUI(input);

            UserController issuerController = new IssuerController(ui, userService, projectService, bugReportService, performanceMetricsService, tagAssignmentService, mailboxService, currentUser);
            issuerController.getUseCase(IssuerUseCase.CREATE_COMMENT.value).run();
        } catch (IndexOutOfBoundsException e) {
            assert e.getMessage().equals("Index: 1, Size: 1");
        }
    }

    @Test
    public void unsuccessfulCreatedComment_forComment_invalidChoice() throws Exception {
        try {
            successfullyCreatedComment_forBugReport();
            String[] simulatedUserInput = {
                    "0",
                    "Crash",
                    "0",
                    "c",
                    "2"
            };
            ArrayList<String> input = new ArrayList<>(Arrays.asList(simulatedUserInput));
            TestUI ui = new TestUI(input);

            UserController issuerController = new IssuerController(ui, userService, projectService, bugReportService, performanceMetricsService, tagAssignmentService, mailboxService, currentUser);
            issuerController.getUseCase(IssuerUseCase.CREATE_COMMENT.value).run();
        } catch (IndexOutOfBoundsException e) {
            assert e.getMessage().equals("Index: 2, Size: 1");
        }
    }

    @Test
    public void unsuccessfulCreatedComment_invalidChoice() throws Exception {
        try {
            String[] simulatedUserInput = {
                    "0",
                    "Crash",
                    "0",
                    "x"
            };
            ArrayList<String> input = new ArrayList<>(Arrays.asList(simulatedUserInput));
            TestUI ui = new TestUI(input);

            UserController issuerController = new IssuerController(ui, userService, projectService, bugReportService, performanceMetricsService, tagAssignmentService, mailboxService, currentUser);
            issuerController.getUseCase(IssuerUseCase.CREATE_COMMENT.value).run();
        } catch (ReportErrorToUserException e) {
            assert e.getMessage().equals("This is an invalid input");
        }
    }

}
