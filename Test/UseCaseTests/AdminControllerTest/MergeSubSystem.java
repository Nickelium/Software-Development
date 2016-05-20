package UseCaseTests.AdminControllerTest;

import Controller.UserController.AdminController;
import Controller.UserController.UserController;
import CustomExceptions.ReportErrorToUserException;
import Model.Memento.Caretaker;
import UseCaseTests.UseCasesUI.TestUI;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class MergeSubSystem extends AdminControllerInit {

    @Test
    public void successfullySplit() throws Exception {

        assert projectService.getAllProjects().stream().noneMatch(x -> x.getName().equals("Merge"));
        
        int n = projectService.getAllSubSystems().size();
        
        String[] simulatedUserInput = {
                "0",
                "4",
                "0",
                "Merge",
                "Description of merge"
        };
        ArrayList<String> input = new ArrayList<>(Arrays.asList(simulatedUserInput));
        TestUI ui = new TestUI(input);

        UserController adminController = new AdminController(ui, userService, projectService, bugReportService, performanceMetricsService, new Caretaker(projectService, mailboxService), currentUser);
        adminController.getUseCase(AdminUseCase.MERGE_SUBSYSTEM.value).run();

        assert projectService.getAllSubSystems().stream().filter(x -> x.getName().equals("Merge")).collect(Collectors.toList()).size() == 1;
        assert projectService.getAllSubSystems().size() == n - 1;
    }

    @Test
    public void unsuccessfulSplit_invalidName() throws Exception {
    	
    	assert projectService.getAllProjects().stream().noneMatch(x -> x.getName().equals("Merge"));
        
        int n = projectService.getAllSubSystems().size();
        
        try {
            String[] simulatedUserInput = {
            		"0",
                    "4",
                    "0",
                    "",
                    "Description of merge"
            };
            ArrayList<String> input = new ArrayList<>(Arrays.asList(simulatedUserInput));
            TestUI ui = new TestUI(input);

            UserController adminController = new AdminController(ui, userService, projectService, bugReportService, performanceMetricsService, new Caretaker(projectService, mailboxService), currentUser);
            adminController.getUseCase(AdminUseCase.SPLIT_SUBSYSTEM.value).run();
        } catch (ReportErrorToUserException e) {
            assert (e.getMessage().equals("The given name is invalid."));
            assert projectService.getAllSubSystems().stream().filter(x -> x.getName().equals("Merge")).collect(Collectors.toList()).size() == 0;
            assert projectService.getAllSubSystems().size() == n;
        }
    }

    @Test
    public void unsuccessfulSplit_invalidDescription() throws Exception {
    	
    	assert projectService.getAllProjects().stream().noneMatch(x -> x.getName().equals("Merge"));
        
        int n = projectService.getAllSubSystems().size();
        
        try {
            String[] simulatedUserInput = {
            		"0",
                    "4",
                    "0",
                    "Merge",
                    ""
            };
            ArrayList<String> input = new ArrayList<>(Arrays.asList(simulatedUserInput));
            TestUI ui = new TestUI(input);

            UserController adminController = new AdminController(ui, userService, projectService, bugReportService, performanceMetricsService, new Caretaker(projectService, mailboxService), currentUser);
            adminController.getUseCase(AdminUseCase.SPLIT_SUBSYSTEM.value).run();
        } catch (ReportErrorToUserException e) {
            assert (e.getMessage().equals("The description name is invalid."));
            assert projectService.getAllSubSystems().stream().filter(x -> x.getName().equals("Merge")).collect(Collectors.toList()).size() == 0;
            assert projectService.getAllSubSystems().size() == n;
        }
    }

}
