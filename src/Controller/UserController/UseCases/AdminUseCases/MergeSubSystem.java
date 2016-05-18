package Controller.UserController.UseCases.AdminUseCases;

import Controller.Formatter;
import Controller.IUI;
import Controller.UserController.UseCases.UseCase;
import CustomExceptions.ReportErrorToUserException;
import Model.BugReport.BugReport;
import Model.BugReport.BugReportService;
import Model.Milestone.Milestone;
import Model.Project.Project;
import Model.Project.ProjectService;
import Model.Project.SubSystem;
import Model.User.User;
import Model.User.UserService;

import java.util.ArrayList;
import java.util.List;


public class MergeSubSystem extends UseCase {

    public MergeSubSystem(IUI ui, UserService userService, ProjectService projectService, BugReportService bugReportService, User currentUser) {
        super(ui, userService, projectService, bugReportService, currentUser);
        changeSystem = true;
    }

    /**
     *
     * Lets an administrator merge subsystems.
     *
	 * 2. The system shows a list of projects.
	 * 3. The administrator selects a project.
	 * 4. The system shows a list of subsystems of the selected project.
	 * 5. The administrator selects a first subsystem.
	 * 6. The system shows a list of subsystems compatible with the first one.
	 *	 	A compatible subsystem is a child, a parent or a sibling of the first
	 * 		selected subsystem in the tree of subsystems of the selected project.
	 * 7. The administrator selects a second subsystem.
	 * 8. The system asks for a name and description for the new subsystem.
	 * 9. The administrator enters a name and description.
	 * 10. The system creates a new subsystem with the lowest milestone of the
	 * 		two original subsystems. The bug reports and subsystems that are part
	 *		of the original two subsystems are migrated to the new subsystem. The
	 * 		two original subsystems are removed.
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
        
        // Step 6
        getUi().display("List of all compatible subsystems of the subsystem:");
        List<SubSystem> related = getProjectService().getRelated(project, subSystem);
        String parsedRelatedList = Formatter.formatSubSystemList(related);
        getUi().display(parsedRelatedList);
        
        // Step 7
        int indexRelated = getUi().readInt();
        SubSystem relatedSubSystem = related.get(indexRelated);
        
        // Step 8 + 9
        getUi().display("Please enter the new subsystem informations.");
        getUi().display("Name:");
        String name = getUi().readString();
        if(!subSystem.isValidName(name)) throw new ReportErrorToUserException("The given name is invalid.");
        
        getUi().display("Description:");
        String description = getUi().readString();
        if(!subSystem.isValidDescription(description)) throw new ReportErrorToUserException("The description is invalid.");
      
        // Step 10
       
    	getProjectService().merge(project, subSystem, relatedSubSystem, name, description);
        
        getUi().display("Merge completed !\n");
        
    }
    
    @Override
	public String toString()
	{
		return "Merge Subsystem";
	}
}
