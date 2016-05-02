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

/**
 * Created by Karina on 24.03.2016.
 */
public class MergeSubSystem extends UseCase {

    public MergeSubSystem(IUI ui, UserService userService, ProjectService projectService, BugReportService bugReportService, User currentUser) {
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
        SubSystem reference = subSystem.getHeight() > relatedSubSystem.getHeight() ? subSystem : relatedSubSystem;
        SubSystem parentReferenceSubSystem = getProjectService().getParentSubSystem(reference);
        
        if(parentReferenceSubSystem != null)
        {
        	getProjectService().createSubsystem(name, description, parentReferenceSubSystem);
        	//for(SubSystem sub : subSystemSubs)
        	//	relatedSubSystem.addSubSystem(sub);
        	//for(BugReport bug : subSystemBugs)
        	//	relatedSubSystem.addBugReport(bug);
        }
        else
        {
        	getProjectService().createSubsystem(name, description, project);

        }
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        // Step 10
        if(relatedSubSystem.isParent(subSystem))
        {
        	//related
        	List<SubSystem> subSystemSubs = subSystem.getSubSystems();
        	List<BugReport> subSystemBugs = subSystem.getBugReports();
        	Milestone subSystemMilestone = subSystem.getLatestAchievedMilestone();
        	getProjectService().removeSubSystem(project, subSystem);
        	
        	getProjectService().setSubSystemName(relatedSubSystem, name);
        	getProjectService().setSubSystemDescription(relatedSubSystem, description);
        	
        	for(SubSystem sub : subSystemSubs)
        		relatedSubSystem.addSubSystem(sub);
        	for(BugReport bug : subSystemBugs)
        		relatedSubSystem.addBugReport(bug);
        	
        	// Latestachieved or all milestones ?
        	Milestone lowerMilestone = 
        			subSystem.getLatestAchievedMilestone().compareTo(subSystemMilestone)
        			 < 0 ? subSystem.getLatestAchievedMilestone() : subSystemMilestone;
        	getProjectService().setNewSubSystemMilestone(relatedSubSystem, lowerMilestone);
        }
        else
        {
        	//subsystem
        	List<SubSystem> relatedSubs = relatedSubSystem.getSubSystems();
        	List<BugReport> relatedBugs = relatedSubSystem.getBugReports();
        	Milestone relatedMilestone = relatedSubSystem.getLatestAchievedMilestone();
        	getProjectService().removeSubSystem(project, relatedSubSystem);
        	
        	getProjectService().setSubSystemName(subSystem, name);
        	getProjectService().setSubSystemDescription(subSystem, description);
        	
        	for(SubSystem sub : relatedSubs)
        		subSystem.addSubSystem(sub);
        	for(BugReport bug : relatedBugs)
        		subSystem.addBugReport(bug);
        	
        	// Latestachieved or all milestones ?
        	Milestone lowerMilestone = 
        			subSystem.getLatestAchievedMilestone().compareTo(relatedMilestone)
        			 < 0 ? subSystem.getLatestAchievedMilestone() :relatedMilestone;
        	getProjectService().setNewSubSystemMilestone(subSystem, lowerMilestone);
        	
        }
        
        getUi().display("Merge completed !");
        
    }
    
    @Override
	public String toString()
	{
		return "Merge Subsystem";
	}
}
