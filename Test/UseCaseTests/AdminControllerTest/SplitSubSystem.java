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

public class SplitSubSystem extends AdminControllerInit {

    @Test
    public void successfullySplit1() throws Exception {

        assert projectService.getAllProjects().stream().noneMatch(x -> x.getName().equals("Split1"));
        assert projectService.getAllProjects().stream().noneMatch(x -> x.getName().equals("Split2"));
        
        int n = projectService.getAllSubSystems().size();
        
        String[] simulatedUserInput = {
                "0",
                "4",
                "Split1",
                "Description of split1",
                "Split2",
                "Description of split2"
        };
        ArrayList<String> input = new ArrayList<>(Arrays.asList(simulatedUserInput));
        TestUI ui = new TestUI(input);

        UserController adminController = new AdminController(ui, userService, projectService, bugReportService, performanceMetricsService, new Caretaker(projectService, mailboxService), currentUser);
        adminController.getUseCase(AdminUseCase.SPLIT_SUBSYSTEM.value).run();

        assert projectService.getAllSubSystems().stream().filter(x -> x.getName().equals("Split1")).collect(Collectors.toList()).size() == 1;
        assert projectService.getAllSubSystems().stream().filter(x -> x.getName().equals("Split2")).collect(Collectors.toList()).size() == 1;
        assert projectService.getAllSubSystems().size() == n + 1;
    }
    
    @Test
    public void successfullySplit2() throws Exception {

        assert projectService.getAllProjects().stream().noneMatch(x -> x.getName().equals("Split1"));
        assert projectService.getAllProjects().stream().noneMatch(x -> x.getName().equals("Split2"));
        
        int n = projectService.getAllSubSystems().size();
        
        String[] simulatedUserInput = {
                "0",
                "2",
                "Split1",
                "Description of split1",
                "Split2",
                "Description of split2",
                "1",
                "2"
        };
        ArrayList<String> input = new ArrayList<>(Arrays.asList(simulatedUserInput));
        TestUI ui = new TestUI(input);

        UserController adminController = new AdminController(ui, userService, projectService, bugReportService, performanceMetricsService, new Caretaker(projectService, mailboxService), currentUser);
        adminController.getUseCase(AdminUseCase.SPLIT_SUBSYSTEM.value).run();

        assert projectService.getAllSubSystems().stream().filter(x -> x.getName().equals("Split1")).collect(Collectors.toList()).size() == 1;
        assert projectService.getAllSubSystems().stream().filter(x -> x.getName().equals("Split2")).collect(Collectors.toList()).size() == 1;
        assert projectService.getAllSubSystems().size() == n + 1;
    }

    @Test
    public void unsuccessfulSplit_invalidName1() throws Exception {
        try {
            String[] simulatedUserInput = {
            		"0",
                    "4",
                    "",
                    "Description of split1",
                    "Split2",
                    "Description of split2"
            };
            ArrayList<String> input = new ArrayList<>(Arrays.asList(simulatedUserInput));
            TestUI ui = new TestUI(input);

            UserController adminController = new AdminController(ui, userService, projectService, bugReportService, performanceMetricsService, new Caretaker(projectService, mailboxService), currentUser);
            adminController.getUseCase(AdminUseCase.SPLIT_SUBSYSTEM.value).run();
        } catch (ReportErrorToUserException e) {
            assert (e.getMessage().equals("The given name is invalid."));
            assert projectService.getAllSubSystems().stream().filter(x -> x.getName().equals("Split1")).collect(Collectors.toList()).size() == 0;
            assert projectService.getAllSubSystems().stream().filter(x -> x.getName().equals("Split2")).collect(Collectors.toList()).size() == 0;
        }
    }

    @Test
    public void unsuccessfulSplit_invalidDescription1() throws Exception {
    	try {
            String[] simulatedUserInput = {
            		"0",
                    "4",
                    "Split1",
                    "",
                    "Split2",
                    "Description of split2"
            };
            ArrayList<String> input = new ArrayList<>(Arrays.asList(simulatedUserInput));
            TestUI ui = new TestUI(input);

            UserController adminController = new AdminController(ui, userService, projectService, bugReportService, performanceMetricsService, new Caretaker(projectService, mailboxService), currentUser);
            adminController.getUseCase(AdminUseCase.SPLIT_SUBSYSTEM.value).run();
        } catch (ReportErrorToUserException e) {
            assert (e.getMessage().equals("The description is invalid."));
            assert projectService.getAllSubSystems().stream().filter(x -> x.getName().equals("Split1")).collect(Collectors.toList()).size() == 0;
            assert projectService.getAllSubSystems().stream().filter(x -> x.getName().equals("Split2")).collect(Collectors.toList()).size() == 0;
        }

    }

    @Test
    public void unsuccessfulSplit_invalidName2() throws Exception
    {
    	try 
    	{
            String[] simulatedUserInput =
            {
            		"0",
                    "4",
                    "Split1",
                    "Description of split1",
                    "",
                    "Description of split2"
            };
            ArrayList<String> input = new ArrayList<>(Arrays.asList(simulatedUserInput));
            TestUI ui = new TestUI(input);

            UserController adminController = new AdminController(ui, userService, projectService, bugReportService, performanceMetricsService, new Caretaker(projectService, mailboxService), currentUser);
            adminController.getUseCase(AdminUseCase.SPLIT_SUBSYSTEM.value).run();
    	} 
    	catch (ReportErrorToUserException e)
    	{
            assert (e.getMessage().equals("The given name is invalid."));
            assert projectService.getAllSubSystems().stream().filter(x -> x.getName().equals("Split1")).collect(Collectors.toList()).size() == 0;
            assert projectService.getAllSubSystems().stream().filter(x -> x.getName().equals("Split2")).collect(Collectors.toList()).size() == 0;
        }
    }
    @Test
    public void unsuccessfulSplit_invalidDescription2() throws Exception
    {
    	try 
    	{
            String[] simulatedUserInput =
            {
            		"0",
                    "4",
                    "Split1",
                    "Description of split1",
                    "Split2",
                    ""
            };
            ArrayList<String> input = new ArrayList<>(Arrays.asList(simulatedUserInput));
            TestUI ui = new TestUI(input);

            UserController adminController = new AdminController(ui, userService, projectService, bugReportService, performanceMetricsService, new Caretaker(projectService, mailboxService), currentUser);
            adminController.getUseCase(AdminUseCase.SPLIT_SUBSYSTEM.value).run();
    	} 
    	catch (ReportErrorToUserException e)
    	{
            assert (e.getMessage().equals("The description is invalid."));
            assert projectService.getAllSubSystems().stream().filter(x -> x.getName().equals("Split1")).collect(Collectors.toList()).size() == 0;
            assert projectService.getAllSubSystems().stream().filter(x -> x.getName().equals("Split2")).collect(Collectors.toList()).size() == 0;
        }
    }

}
