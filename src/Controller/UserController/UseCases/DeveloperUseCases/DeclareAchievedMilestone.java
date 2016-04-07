package Controller.UserController.UseCases.DeveloperUseCases;

import Controller.IUI;
import CustomExceptions.ReportErrorToUserException;
import Model.BugReport.BugReportService;
import Model.BugReport.DeveloperAssignmentService;
import Model.BugReport.TagAssignmentService;
import Model.Project.ProjectService;
import Model.User.User;
import Model.User.UserService;

/**
 * Created by Laurens on 7/04/2016.
 */
public class DeclareAchievedMilestone extends DeveloperUseCase {

    public DeclareAchievedMilestone(IUI ui, UserService userService, ProjectService projectService, BugReportService bugReportService, TagAssignmentService tagAssignmentService, DeveloperAssignmentService developerAssignmentService, User currentUser) {
        super(ui, userService, projectService, bugReportService, tagAssignmentService, developerAssignmentService, currentUser);
    }

    @Override
    public void run() throws ReportErrorToUserException, IndexOutOfBoundsException {

        // Step 2

    }
}
