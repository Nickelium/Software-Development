package UseCaseTests.UserControllerTest;

import Controller.UserController.AdminController;
import Controller.UserController.UserController;
import Model.Memento.Caretaker;
import UseCaseTests.AdminControllerTest.AdminControllerInit;
import UseCaseTests.UseCasesUI.TestUI;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Karina on 11.03.2016.
 */
public class ShowProject extends AdminControllerInit{

    @Test
    public void successfullyShowedProject() throws Exception{
        String[] simulatedUserInput = {
                "0"
        };
        ArrayList<String> input = new ArrayList<>(Arrays.asList(simulatedUserInput));
        TestUI ui = new TestUI(input);

        UserController adminController = new AdminController(ui, userService, projectService, bugReportService, performanceMetricsService, new Caretaker(projectService, mailboxService), currentUser);
        adminController.getUseCase(0).run();
    }

    @Test
    public void unsuccessfulShowProject_invalidInput() throws Exception{
        try {
            String[] simulatedUserInput = {
                    "5"
            };
            ArrayList<String> input = new ArrayList<>(Arrays.asList(simulatedUserInput));
            TestUI ui = new TestUI(input);

            UserController adminController = new AdminController(ui, userService, projectService, bugReportService, performanceMetricsService, new Caretaker(projectService, mailboxService), currentUser);
            adminController.getUseCase(0).run();
        } catch (IndexOutOfBoundsException e) {
            assert e.getMessage().equals("Index: 5, Size: 2");
        }
    }

}
