package Controller.UserController.UseCases.DeveloperUseCases;

import Controller.Formatter;
import Controller.IUI;
import CustomExceptions.ReportErrorToUserException;
import Model.BugReport.BugReport;
import Model.BugReport.BugReportService;
import Model.BugReport.DeveloperAssignmentService;
import Model.BugReport.TagAssignmentService;
import Model.Project.ProjectService;
import Model.User.Developer;
import Model.User.User;
import Model.User.UserService;

import java.util.List;

/**
 * Created by Karina on 25.03.2016.
 */
public class AssignToBugReport extends DeveloperUseCase {

    public AssignToBugReport(IUI ui, UserService userService, ProjectService projectService, BugReportService bugReportService, TagAssignmentService tagAssignmentService, DeveloperAssignmentService developerAssignmentService, User currentUser) {
        super(ui, userService, projectService, bugReportService, tagAssignmentService, developerAssignmentService, currentUser);
        changeSystem = true;
    }

    /**
     *
     * Lets a Developer assign one or more developers to a bug report.
     *
     * 2. Include use case Select Bug Report.
     * 3. The system shows a list of developers that are involved in the project.
     * 4. The logged in developer selects one or more of the developers to assign
     *    to the selected bug report on top of those already assigned.
     * 5. The systems assigns the selected developers to the selected bug report.
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
        getUi().display("Please select the bug report that you want to assign a new developer to: ");
        BugReport bugReport = selectBugReport();
        if (!getDeveloperAssignmentService().canUserAssignDevelopers(getCurrentUser(), bugReport)) {
            throw new ReportErrorToUserException("You are not allowed to assign developers to this bugreport.");
        }

        // Step 3
        getUi().display("Please select the developer(s) that you want to assign to the chosen bug report. Type -1 to continue");
        List<Developer> developerList = bugReport.getAssignees();
        String parsedList = Formatter.formatUserList(developerList);
        getUi().display(parsedList);
        // Step 4
        int selectedValue = getUi().readInt();

        if (selectedValue == -1) {
            getUi().display("Assignment of developer canceled.");
        } else {
            //Step 5
            Developer developer = developerList.get(selectedValue);
            getDeveloperAssignmentService().assignDeveloperToBugReport(getCurrentUser(), developer, bugReport);
            getUi().display("The developer has successfully been assigned to the bugreport.");
        }
    }
    
    @Override
	public String toString()
	{
		return "Assign to Bugreport";
	}
}
