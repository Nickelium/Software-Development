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
public class CreateBugReport extends IssuerControllerInit{

    @Test
    public void successfullyCreatedBugReport() throws Exception{
        String[] simulatedUserInput = {
                "0",
                "0",
                "Bug Report Test Title",
                "Bug Report Test Description",
                "0",
                "1",
                "-1"
        };
        ArrayList<String> input = new ArrayList<>(Arrays.asList(simulatedUserInput));
        TestUI ui = new TestUI(input);

        UserController issuerController = new IssuerController(ui, userService, projectService, bugReportService, tagAssignmentService, mailboxService, loginController.getCurrentUser());
        issuerController.getUseCase(2).run();
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void unsuccessfulCreateBugReport_invalidProjectChoice() throws Exception{
        String[] simulatedUserInput = {
                "10"
        };
        ArrayList<String> input = new ArrayList<>(Arrays.asList(simulatedUserInput));
        TestUI ui = new TestUI(input);

        UserController issuerController = new IssuerController(ui, userService, projectService, bugReportService, tagAssignmentService, mailboxService, loginController.getCurrentUser());
        issuerController.getUseCase(2).run();

    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void unsuccessfulCreateBugReport_invalidBugReportChoice() throws Exception{
        String[] simulatedUserInput = {
                "1",
                "10"
        };
        ArrayList<String> input = new ArrayList<>(Arrays.asList(simulatedUserInput));
        TestUI ui = new TestUI(input);

        UserController issuerController = new IssuerController(ui, userService, projectService, bugReportService, tagAssignmentService, mailboxService, loginController.getCurrentUser());
        issuerController.getUseCase(2).run();
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void unsuccessfulCreateBugReport_invalidDependencyChoice() throws Exception{
        String[] simulatedUserInput = {
                "1",
                "1",
                "Bug Report Test Title",
                "Bug Report Test Description",
                "10"
        };
        ArrayList<String> input = new ArrayList<>(Arrays.asList(simulatedUserInput));
        TestUI ui = new TestUI(input);

        UserController issuerController = new IssuerController(ui, userService, projectService, bugReportService, tagAssignmentService, mailboxService, loginController.getCurrentUser());
        issuerController.getUseCase(2).run();
    }

}
