package Controller.UserController.UseCases.DeveloperUseCases;

import Controller.Formatter;
import Controller.IUI;
import CustomExceptions.ReportErrorToUserException;
import Model.BugReport.BugReportService;
import Model.BugReport.DeveloperAssignmentService;
import Model.BugReport.TagAssignmentService;
import Model.Milestone.Milestone;
import Model.Project.Project;
import Model.Project.ProjectService;
import Model.Project.SubSystem;
import Model.User.User;
import Model.User.UserService;

import java.util.List;

/**
 * Class extending the use case class, representing a declare-achieved-milestone use case.
 */
public class DeclareAchievedMilestone extends DeveloperUseCase {

    public DeclareAchievedMilestone(IUI ui, UserService userService, ProjectService projectService, BugReportService bugReportService, TagAssignmentService tagAssignmentService, DeveloperAssignmentService developerAssignmentService, User currentUser) {
        super(ui, userService, projectService, bugReportService, tagAssignmentService, developerAssignmentService, currentUser);
        changeSystem = true;
    }

    /**
     * 2. The system shows a list of projects.
     * 3. The developer selects a project.
     * 4. The system shows a list of subsystems of the selected project.
     * 5. The developer selects a subsystem.
     * 6. The system shows the currently achieved milestones and asks for a new
     * one.
     * 7. The developer proposes a new achieved milestone.
     * 8. The system updates the achieved milestone of the selected component.
     * If necessary, the system rst recursively updates the achieved milestone
     * of all the subsystems that the component contains.
     *
     * Extensions:
     * 5a. The developer indicates he wants to change the achieved milestone of
     * the entire project.
     * 1. The use case continues with step 6.
     *
     * 8a. The new achieved milestone could not be assigned due to some con-
     * straint (see in 3.3.4).
     * 1. The system is restored and the use case has no eect.
     * 2. The use case ends here.
     *
     * @throws ReportErrorToUserException Something went wrong and has to be reported to user.
     * @throws IndexOutOfBoundsException Non valid index for selecting entered.
     */
    @Override
    public void run() throws ReportErrorToUserException, IndexOutOfBoundsException {

        // Step 2
        getUi().display("Select the project that you want to add an achieved milestone to: ");

        List<Project> projectList = getProjectService().getAllProjects();
        String formattedProjectList = Formatter.formatProjectList(projectList);
        getUi().display(formattedProjectList);

        // Step 3
        int projectIndex = getUi().readInt();
        Project project = projectList.get(projectIndex);

        // Step 4
        getUi().display("Select the subsystem that you want to declare an achieve milestone to: ");
        List<SubSystem> subSystemList = project.getAllSubSystems();
        String formattedSubSystemList = Formatter.formatSubSystemList(subSystemList);
        getUi().display(formattedSubSystemList);
        getUi().display((subSystemList.size()) + ": Declare a milestone for the entire project.");

        // Step 5

        int subSystemIndex = getUi().readInt();

        List<Milestone> milestoneList;

        SubSystem subSystem = null;

        if(subSystemIndex != subSystemList.size()){
            subSystem = subSystemList.get(subSystemIndex);
            milestoneList = subSystem.getAllMilestones();
        }

        // Step 5a
        else {
            milestoneList = project.getAllMilestones();
        }

        // Step 6
        getUi().display("These are the currently achieved milestones: ");
        String formattedMilestoneList = Formatter.formatMilestoneList(milestoneList);
        getUi().display(formattedMilestoneList);
        getUi().display("Please enter a new milestone: ");

        // Step 7
        String newMilestone = getUi().readString();
        Milestone newMilestoneObject = new Milestone(newMilestone);

        // Step 8
        // Step 8a is handled in the Subsystem & Project class, throws a
        // ReportErrorToUserException when the milestone cannot be assigned.
        if(subSystem != null )
            getProjectService().setNewSubSystemMilestone(subSystem, newMilestoneObject);
        else
            getProjectService().setNewProjectMilestone(project, newMilestoneObject);
    }
    
    @Override
	public String toString()
	{
		return "Declare Achieved Milestone";
	}
}
