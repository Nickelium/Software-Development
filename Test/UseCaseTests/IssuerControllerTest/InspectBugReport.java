package UseCaseTests.IssuerControllerTest;

import Controller.UserController.AdminController;
import Controller.UserController.IssuerController;
import Controller.UserController.UserController;
import CustomExceptions.ModelException;
import UseCaseTests.UseCasesUI.TestUI;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Karina on 11.03.2016.
 */
public class InspectBugReport extends IssuerControllerInit{

    @Test
    public void successfullyInspectedByString() throws Exception{
        String[] simulatedUserInput = {
                "0",
                "Crash",
                "0"

        };
        ArrayList<String> input = new ArrayList<>(Arrays.asList(simulatedUserInput));
        TestUI ui = new TestUI(input);

        UserController issuerController = new IssuerController(ui,userService,projectService,bugReportService,loginController.getCurrentUser());
        issuerController.callUseCase(3);
    }

    @Test
    public void successfullyInspectedByFiledUser() throws Exception{
        String[] simulatedUserInput = {
                "1",
                "doc",
                "0"
        };
        ArrayList<String> input = new ArrayList<>(Arrays.asList(simulatedUserInput));
        TestUI ui = new TestUI(input);

        UserController issuerController = new IssuerController(ui,userService,projectService,bugReportService,loginController.getCurrentUser());
        issuerController.callUseCase(3);
    }

    @Test
    public void successfullyInspectedByAssignedUser() throws Exception{
        String[] simulatedUserInput = {
                "2",
                "major",
                "0"
        };
        ArrayList<String> input = new ArrayList<>(Arrays.asList(simulatedUserInput));
        TestUI ui = new TestUI(input);

        UserController issuerController = new IssuerController(ui,userService,projectService,bugReportService,loginController.getCurrentUser());
        issuerController.callUseCase(3);
    }


    @Test(expected = ModelException.class)
    public void unsuccessfulInspectionByString() throws Exception{
        String[] simulatedUserInput = {
                "0",
                "Test test",
                "0"
        };
        ArrayList<String> input = new ArrayList<>(Arrays.asList(simulatedUserInput));
        TestUI ui = new TestUI(input);

        UserController issuerController = new IssuerController(ui,userService,projectService,bugReportService,loginController.getCurrentUser());
        issuerController.callUseCase(3);
    }

    @Test(expected = ModelException.class)
    public void unsuccessfulInspectionByFiledUser() throws Exception{
        String[] simulatedUserInput = {
                "1",
                "major",
                "0"
        };
        ArrayList<String> input = new ArrayList<>(Arrays.asList(simulatedUserInput));
        TestUI ui = new TestUI(input);

        UserController issuerController = new IssuerController(ui,userService,projectService,bugReportService,loginController.getCurrentUser());
        issuerController.callUseCase(3);
    }

    @Test(expected = ModelException.class)
    public void unsuccessfulInspectionByAssignedUser() throws Exception{
        String[] simulatedUserInput = {
                "2",
                "doc",
                "0"
        };
        ArrayList<String> input = new ArrayList<>(Arrays.asList(simulatedUserInput));
        TestUI ui = new TestUI(input);

        UserController issuerController = new IssuerController(ui,userService,projectService,bugReportService,loginController.getCurrentUser());
        issuerController.callUseCase(3);
    }


    @Test(expected = ModelException.class)
    public void unsuccessfulInspection_invalidInput() throws Exception{
        String[] simulatedUserInput = {
                "3"
        };
        ArrayList<String> input = new ArrayList<>(Arrays.asList(simulatedUserInput));
        TestUI ui = new TestUI(input);

        UserController issuerController = new IssuerController(ui,userService,projectService,bugReportService,loginController.getCurrentUser());
        issuerController.callUseCase(3);
    }

}