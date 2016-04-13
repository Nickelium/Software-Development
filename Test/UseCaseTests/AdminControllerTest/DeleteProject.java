package UseCaseTests.AdminControllerTest;

import Controller.UserController.AdminController;
import Controller.UserController.UserController;
import Model.Memento.Caretaker;
import UseCaseTests.UseCasesUI.TestUI;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Karina on 10.03.2016.
 */
public class DeleteProject extends AdminControllerInit{

    @Test
    public void successfullyDeletedProject() throws Exception{
        String[] simulatedUserInput = {
                "0"
        };
        ArrayList<String> input = new ArrayList<>(Arrays.asList(simulatedUserInput));
        TestUI ui = new TestUI(input);

        UserController adminController = new AdminController(ui, userService, projectService, bugReportService, new Caretaker(projectService, mailboxService), loginController.getCurrentUser());
        adminController.getUseCase(5).run();
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void unsuccessfullDeleteProject() throws Exception{
        String[] simulatedUserInput = {
                "6"
        };
        ArrayList<String> input = new ArrayList<>(Arrays.asList(simulatedUserInput));
        TestUI ui = new TestUI(input);

        UserController adminController = new AdminController(ui, userService, projectService, bugReportService, new Caretaker(projectService, mailboxService), loginController.getCurrentUser());
        adminController.getUseCase(5).run();
    }
}
