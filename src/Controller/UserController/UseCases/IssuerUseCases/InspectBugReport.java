package Controller.UserController.UseCases.IssuerUseCases;

import Controller.Formatter;
import Controller.IUI;
import CustomExceptions.ModelException;
import Model.BugReport.BugReport;
import Model.BugReport.BugReportService;
import Model.Project.ProjectService;
import Model.User.User;
import Model.User.UserService;

/**
 * Created by Karina on 24.03.2016.
 */
public class InspectBugReport extends IssuerUseCase {

    public InspectBugReport(IUI ui, UserService userService, ProjectService projectService, BugReportService bugReportService, User currentUser) {
        super(ui, userService, projectService, bugReportService, null, currentUser);
    }

    /**
     *
     * Lets an Issuer inspect a bug report.
     *
     * 2. Include use case Select Bug Report.
     * 3. The system shows a detailed overview of the selected bug report and
     * all its comments
     *
     * @throws ModelException
     *          in case that the method encounters invalid input
     * @throws IndexOutOfBoundsException
     *		   thrown when a user puts an incorrect option index.
     */
    @Override
    public void run() throws ModelException, IndexOutOfBoundsException {

        // Step 2
        BugReport bugReport = selectBugReport();

        // Step 3
        String bugReportDetails = Formatter.formatDetailedBugReport(bugReport);
        getUi().display(bugReportDetails);
    }
}
