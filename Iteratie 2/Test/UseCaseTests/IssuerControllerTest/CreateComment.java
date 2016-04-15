package UseCaseTests.IssuerControllerTest;

import Controller.UserController.IssuerController;
import Controller.UserController.UserController;
import CustomExceptions.ReportErrorToUserException;
import UseCaseTests.UseCasesUI.TestUI;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Karina on 11.03.2016.
 */
public class CreateComment extends IssuerControllerInit{

    @Test
    public void successfullyCreatedComment_forBugReport() throws Exception{
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

        UserController issuerController = new IssuerController(ui, userService, projectService, bugReportService, tagAssignmentService, mailboxService, currentUser);
        issuerController.getUseCase(4).run();
    }

    @Test
    public void successfullyCreatedComment_forComment() throws Exception{
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

        UserController issuerController = new IssuerController(ui, userService, projectService, bugReportService, tagAssignmentService, mailboxService, currentUser);
        issuerController.getUseCase(4).run();
    }


    @Test(expected = IndexOutOfBoundsException.class)
    public void unsuccessfulCreatedComment_forBugReport_invalidChoice() throws Exception{
        String[] simulatedUserInput = {
                "0",
                "Crash",
                "1"
        };
        ArrayList<String> input = new ArrayList<>(Arrays.asList(simulatedUserInput));
        TestUI ui = new TestUI(input);

        UserController issuerController = new IssuerController(ui, userService, projectService, bugReportService, tagAssignmentService, mailboxService, currentUser);
        issuerController.getUseCase(4).run();
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void unsuccessfulCreatedComment_forComment_invalidChoice() throws Exception{
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

        UserController issuerController = new IssuerController(ui, userService, projectService, bugReportService, tagAssignmentService, mailboxService, currentUser);
        issuerController.getUseCase(4).run();
    }


    @Test(expected = ReportErrorToUserException.class)
    public void unsuccessfulCreatedComment_invalidChoice() throws Exception{
        String[] simulatedUserInput = {
                "0",
                "Crash",
                "0",
                "x"
        };
        ArrayList<String> input = new ArrayList<>(Arrays.asList(simulatedUserInput));
        TestUI ui = new TestUI(input);

        UserController issuerController = new IssuerController(ui, userService, projectService, bugReportService, tagAssignmentService, mailboxService, currentUser);
        issuerController.getUseCase(4).run();
    }


}
