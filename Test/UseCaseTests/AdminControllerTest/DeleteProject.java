package UseCaseTests.AdminControllerTest;

import Controller.UserController.AdminController;
import Controller.UserController.UserController;
import Model.Memento.Caretaker;
import UseCaseTests.UseCasesUI.TestUI;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

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

        UserController adminController = new AdminController(ui, userService, projectService, bugReportService, new Caretaker(projectService, mailboxService), currentUser);
        adminController.getUseCase(5).run();

        assert projectService.getAllProjects().stream().filter(x -> x.getName().equals("ProjectA")).collect(Collectors.toList()).size() == 0;
    }

    @Test
    public void unsuccessfullDeleteProject() throws Exception{
        int sizeFirst = projectService.getAllProjects().size();
        try {
            String[] simulatedUserInput = {
                    "6"
            };
            ArrayList<String> input = new ArrayList<>(Arrays.asList(simulatedUserInput));
            TestUI ui = new TestUI(input);

            UserController adminController = new AdminController(ui, userService, projectService, bugReportService, new Caretaker(projectService, mailboxService), currentUser);
            adminController.getUseCase(5).run();
        } catch (IndexOutOfBoundsException e) {
            assert e.getMessage().equals("Index: 6, Size: 2");
            assert sizeFirst == projectService.getAllProjects().size();
        }
    }

}
