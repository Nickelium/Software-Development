package Controller.UserController.UseCases.IssuerUseCases;

import Controller.Formatter;
import Controller.IUI;
import CustomExceptions.ReportErrorToUserException;
import Model.BugReport.BugReport;
import Model.BugReport.BugReportService;
import Model.Project.ProjectService;
import Model.User.User;
import Model.User.UserService;

/**
 * Class extending the issuer use case class, representing a inspect-bug-report use case.
 */
public class InspectBugReport extends IssuerUseCase {

    public InspectBugReport(IUI ui, UserService userService, ProjectService projectService, BugReportService bugReportService, User currentUser) {
        super(ui, userService, projectService, bugReportService, null, currentUser);
        changeSystem = false;
    }

    /**
     *
     * Lets an Issuer inspect a bug report.
     *
     * 2. Include use case Select Bug Report.
     * 3. The system shows a detailed overview of the selected bug report and
     * all its comments
     *
     * @throws ReportErrorToUserException
     *          in case that the method encounters invalid input
     * @throws IndexOutOfBoundsException
     *		   thrown when a user puts an incorrect option index.
     */
    @Override
    public void run() throws ReportErrorToUserException, IndexOutOfBoundsException {

        // Step 2
        BugReport bugReport = selectBugReport();

        // Step 3
        String bugReportDetails = Formatter.formatDetailedBugReport(bugReport);
        getUi().display(bugReportDetails);
    }
    
    @Override
	public String toString()
	{
		return "Inspect Bugreport";
	}
}
