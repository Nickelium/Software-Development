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

import java.util.ArrayList;
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
     * Lets an administrator split a subsystem.
     *
	 * 2. The system shows a list of projects.
	 * 3. The administrator selects a project.
	 * 4. The system shows a list of subsystems of the selected project.
	 * 5. The administrator selects a subsystem.
	 * 6. The system asks for a name and description for both new subsystems.
	 * 7. The administrator enters both names and descriptions.
	 * 8. For each bug report and subsystem that is part of the original subsystem, the system asks to which new subsystem to migrate it to.
	 * 9. The administrator answers for each bug report and subsystem.
	 * 10. The system creates two new subsystems with the same milestone as
	 * the original subsystem. The original subsystem is removed.
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
        if(!subSystem.isValidName(name1)) throw new ReportErrorToUserException("The given name is invalid.");
        
        getUi().display("Description:");
        String description1 = getUi().readString();
        if(!subSystem.isValidDescription(description1)) throw new ReportErrorToUserException("The description is invalid.");
      
        getUi().display("Please enter the subsystem 2 information.");
        getUi().display("Name:");
        String name2 = getUi().readString();
        if(!subSystem.isValidName(name2)) throw new ReportErrorToUserException("The given name is invalid.");

        
        getUi().display("Description:");
        String description2 = getUi().readString();
        if(!subSystem.isValidDescription(description2)) throw new ReportErrorToUserException("The description is invalid.");

       
        // Step 8 + 9
        List<SubSystem> toAddSubSystem1 = new ArrayList<>();
        List<SubSystem> toAddSubSystem2 = new ArrayList<>();
        
        for(SubSystem sub : subSystem.getSubSystems())
        {
        	getUi().display("Please indicate to which subsystem this belongs to (1/2):");
        	getUi().display(sub.toString());
        	int index = getUi().readInt();
        	
        	// Observers OK updates automatically in add
        	switch(index)
        	{
	        	case 1:
	        		toAddSubSystem1.add(sub);
	        		break;
	        	case 2 :
	        		toAddSubSystem2.add(sub);
	        		break;
	        	default : 
	        		throw new ReportErrorToUserException("This is an invalid input");
        	}
        }
        
        List<BugReport> toAddBugReport1 = new ArrayList<>();
        List<BugReport> toAddBugReport2 = new ArrayList<>();
        
        for(BugReport bug : subSystem.getBugReports())
        {
        	getUi().display("Please indicate to which subsystem this belongs to (1/2):");
        	getUi().display(bug.toString());
        	int index = getUi().readInt();
        	
        	switch(index)
        	{
	        	case 1:
	        		toAddBugReport1.add(bug);
	        		break;
	        	case 2 :
	        		toAddBugReport2.add(bug);
	        		break;
	        	default : 
	        		throw new ReportErrorToUserException("This is an invalid input");
        	}
        }
        
        // Step 10
        // Finalize + Creation
        
        
        SubSystem parent = getProjectService().getParentSubSystem(subSystem);
        SubSystem subSystem1, subSystem2;
        if(parent != null)
        {
        	subSystem1 = getProjectService().createSubsystem(name1, description1, parent);
        	subSystem2 = getProjectService().createSubsystem(name2, description2, parent);
        }
        else
        {
        	subSystem1 = getProjectService().createSubsystem(name1, description1, project);
        	subSystem2 = getProjectService().createSubsystem(name2, description2, project);
        }
        
        for(SubSystem sub : toAddSubSystem1)
        	subSystem1.addSubSystem(sub);
        for(BugReport bug : toAddBugReport1)
        	subSystem1.addBugReport(bug);
        
        for(SubSystem sub : toAddSubSystem2)
        	subSystem2.addSubSystem(sub);
        for(BugReport bug : toAddBugReport2)
        	subSystem2.addBugReport(bug);
        
        getProjectService().setNewSubSystemMilestone(subSystem1, subSystem.getCurrentSubsystemMilestones());
        getProjectService().setNewSubSystemMilestone(subSystem2, subSystem.getCurrentSubsystemMilestones());
        
        getProjectService().removeSubSystem(project, subSystem);
     
        getUi().display("The subsystem has been successfully splitted.\n");
    }
    
    @Override
	public String toString()
	{
		return "Split Subsystem";
	}
}
