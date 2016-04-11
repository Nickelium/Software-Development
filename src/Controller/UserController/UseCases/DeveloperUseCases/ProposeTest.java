package Controller.UserController.UseCases.DeveloperUseCases;

import Controller.IUI;
import CustomExceptions.ReportErrorToUserException;
import Model.BugReport.BugReport;
import Model.BugReport.BugReportService;
import Model.BugReport.DeveloperAssignmentService;
import Model.BugReport.TagAssignmentService;
import Model.Project.ProjectService;
import Model.User.User;
import Model.User.UserService;

/**
 * Created by Tom on 11/04/16.
 */
public class ProposeTest extends DeveloperUseCase {

    public ProposeTest(IUI ui, UserService userService, ProjectService projectService, BugReportService bugReportService, TagAssignmentService tagAssignmentService, DeveloperAssignmentService developerAssignmentService, User currentUser) {
        super(ui, userService, projectService, bugReportService, tagAssignmentService, developerAssignmentService, currentUser);
    }

    @Override
    public void run() throws ReportErrorToUserException, IndexOutOfBoundsException {
        BugReport bugReport = selectBugReport();

        if (!this.getBugReportService().canAddTest(this.getCurrentUser(), bugReport)) {
            throw new ReportErrorToUserException("You are not allowed to add a test.");
        }

        getUi().display("Please enter the test code. Stop inserting text with '.' on new line.");
        String test = getUi().readMultiline();

        this.getBugReportService().createTest(test, getCurrentUser(), bugReport);

        getUi().display("Successfully added new test.");
    }
}
