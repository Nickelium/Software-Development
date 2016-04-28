package Controller.UserController.UseCases.AdminUseCases;

import Controller.Formatter;
import Controller.IUI;
import Controller.UserController.UseCases.UseCase;
import CustomExceptions.ReportErrorToUserException;
import Model.BugReport.BugReport;
import Model.BugReport.BugReportService;
import Model.Project.Project;
import Model.Project.ProjectService;
import Model.Project.SubSystem;
import Model.User.User;
import Model.User.UserService;

import java.util.List;

/**
 * Created by Karina on 24.03.2016.
 */
public class SplitSubSystem extends UseCase {

    public SplitSubSystem(IUI ui, UserService userService, ProjectService projectService, BugReportService bugReportService, User currentUser) {
        super(ui, userService, projectService, bugReportService, currentUser);
        changeSystem = true;
    }

    /**
     *
     * Lets an administrator create a subsystem.
     *
     * 2. The system shows a list of all projects.
     * 3. The user selects a project.
     * 4. The system shows a detailed overview of the selected project and all
     * its subsystems.
     *
     * @throws ReportErrorToUserException
     *          in case that the method encounters invalid input
     * @throws IndexOutOfBoundsException
     *		   thrown when a user puts an incorrect option index.
     *
     */
    @Override
    public void run() throws ReportErrorToUserException, IndexOutOfBoundsException {

        // Step 2
        getUi().display("List of all projects:");
        List<Project> projectList = getProjectService().getAllProjects();
        String parsedProjectList = Formatter.formatProjectList(projectList);
        getUi().display(parsedProjectList);

        // Step 3
        int indexP = getUi().readInt();
        Project project = projectList.get(indexP);
        
        // Step 4
        getUi().display("List of all subsystems of this project:");
        List<SubSystem> subSystemList = project.getAllSubSystems();
        String parsedSubSystemList = Formatter.formatSubSystemList(subSystemList);
        getUi().display(parsedSubSystemList);

        // Step 5
        int indexS = getUi().readInt();
        SubSystem subSystem = subSystemList.get(indexS);
        
        // Step 6 + 7
        getUi().display("Please enter the subsystem 1 information.");
        getUi().display("Name:");
        String name1 = getUi().readString();
        getUi().display("Description:");
        String description1 = getUi().readString();
        
        SubSystem subsystem1 = getProjectService().createSubsystem(name1, description1, project);
        
        getUi().display("Please enter the subsystem 2 information.");
        getUi().display("Name:");
        String name2 = getUi().readString();
        getUi().display("Description:");
        String description2 = getUi().readString();
        
        SubSystem subsystem2 = getProjectService().createSubsystem(name1, description1, project);

        // Step 8 + 9
        for(SubSystem sub : subSystem.getSubSystems())
        {
        	getUi().display("Please indicate to which subsystem this belongs to :");
        	getUi().display(sub.toString());
        	int index = getUi().readInt();
        	
        	// Observers OK updates automatically in add
        	switch(index)
        	{
	        	case 1:
	        		subsystem1.addSubSystem(sub);
	        		break;
	        	case 2 :
	        		subsystem2.addSubSystem(sub);
	        		break;
	        		default : throw new ReportErrorToUserException("This is an invalid input");
        	}
        }
        
        for(BugReport bug : subSystem.getBugReports())
        {
        	getUi().display("Please indicate to which subsystem this belongs to :");
        	getUi().display(bug.toString());
        	int index = getUi().readInt();
        	
        	switch(index)
        	{
	        	case 1:
	        		subsystem1.addBugReport(bug);
	        		break;
	        	case 2 :
	        		subsystem2.addBugReport(bug);
	        		break;
	        		default : throw new ReportErrorToUserException("This is an invalid input");
        	}
        }
        
        // Step 10
        //Todo remove subsystem
     

        getUi().display("The subsystem has been successfully splitted.\n");
    }
    
    @Override
	public String toString()
	{
		return "Create Subsystem";
	}
}
